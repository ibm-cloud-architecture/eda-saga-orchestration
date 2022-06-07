package ibm.eda.kc.ordersaga.infra.events;

import ibm.eda.kc.ordersaga.infra.events.container.ContainerSagaEvent;
import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageSagaEvent;
import ibm.eda.kc.ordersaga.app.OrderPlacementEventHandler;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.contrib.kafka.TracingKafkaUtils;
import org.apache.kafka.common.header.Headers;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderKafkaEventConsumer
{
    private static final Logger LOG = LoggerFactory.getLogger(OrderKafkaEventConsumer.class);
    private static final int RETRIES = 5;

    @Inject
    Instance<OrderPlacementEventHandler> eventHandler;

    @Inject
    Tracer tracer;

    @Incoming("voyage-response")
    public CompletionStage<Void> onVoyageMessage(VoyageSagaEvent event) throws IOException
    {
        LOG.info("Incoming voyageresponse event with ID = {} arrived", event.messageId);
        return CompletableFuture.runAsync(() -> {
            try (final Scope span = tracer.scopeManager().activate(getOrdersSpanBuilder(event.headers).start()))
            {
                retrying(() -> eventHandler.get().onVoyageEvent(event));
            }
        });
    }

    @Incoming("container-response")
    public CompletionStage<Void> onContainerMessage(ContainerSagaEvent event) throws IOException
    {
        LOG.info("Incoming containerresponse event with ID = {} arrived", event.messageId);
        return CompletableFuture.runAsync(() -> {
            try (final Scope span = tracer.scopeManager().activate(getOrdersSpanBuilder(event.headers).start()))
            {
                retrying(() -> eventHandler.get().onContainerEvent((event)));
            }
        });
    }

    private Tracer.SpanBuilder getOrdersSpanBuilder(Headers headers)
    {
        return tracer.buildSpan("orders").asChildOf(TracingKafkaUtils.extractSpanContext(headers, tracer));
    }

    private void retrying(Runnable runnable)
    {
        int tries = 0;

        while (tries < RETRIES)
        {
            try
            {
                tries++;
                runnable.run();
                return;
            }
            catch(OptimisticLockException ole)
            {
                if (tries == RETRIES) {
                    throw ole;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
