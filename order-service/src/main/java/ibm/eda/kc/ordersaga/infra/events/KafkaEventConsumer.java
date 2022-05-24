package ibm.eda.kc.ordersaga.infra.events;

import ibm.eda.kc.ordersaga.infra.events.container.ContainerReservationEvent;
import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageReservationEvent;
import ibm.eda.kc.ordersaga.saga.OrderPlacementEventHandler;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.contrib.kafka.TracingKafkaUtils;
import org.apache.kafka.common.header.Headers;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class KafkaEventConsumer
{
    private static final int RETRIES = 5;

    @Inject
    private OrderPlacementEventHandler eventHandler;

    //@Inject
    Tracer tracer;

    @Incoming("voyage-response")
    public CompletionStage<Void> onVoyageMessage(VoyageReservationEvent event) throws IOException {
        return CompletableFuture.runAsync(() -> {
            try (final Scope span = tracer.scopeManager().activate(getOrdersSpanBuilder(event.headers).start())) {
                retrying(() -> eventHandler.onVoyageReservationEvent(event));
            }
        });
    }

    @Incoming("container-response")
    public CompletionStage<Void> onContainerMessage(ContainerReservationEvent event) throws IOException {
        return CompletableFuture.runAsync(() -> {
            try (final Scope span = tracer.scopeManager().activate(getOrdersSpanBuilder(event.headers).start()))
            {
                retrying(() -> eventHandler.onContainerReservationEvent((event)));
            }
        });
    }

    private Tracer.SpanBuilder getOrdersSpanBuilder(Headers headers) {
        return tracer.buildSpan("orders").asChildOf(TracingKafkaUtils.extractSpanContext(headers, tracer));
    }

    private void retrying(Runnable runnable) {
        int tries = 0;

        while (tries < RETRIES) {
            try {
                tries++;
                runnable.run();
                return;
            }
            catch(OptimisticLockException ole) {
                if (tries == RETRIES) {
                    throw ole;
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
