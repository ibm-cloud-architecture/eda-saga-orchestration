package ibm.eda.kc.saga.voyage.infra.events;

import ibm.eda.kc.saga.voyage.domain.Voyage;
import ibm.eda.kc.saga.voyage.infra.api.VoyageEventHandler;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.contrib.kafka.TracingKafkaUtils;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


@ApplicationScoped
public class VoyageKafkaEventConsumer
{
    private static final Logger logger = LoggerFactory.getLogger(VoyageKafkaEventConsumer.class);

    @Inject
    VoyageEventHandler voyageEventHandler;

    @Inject
    Tracer tracer;

    /**
     * Read message on "voyage" channel
     * @param message
     * @return
     * @throws IOException
     */
    @Incoming("voyage")
    public CompletionStage<Void> onMessage(KafkaRecord<String, Voyage> message) throws IOException {
        return CompletableFuture.runAsync(() -> {
            try (final Scope span = tracer.scopeManager().activate(getOrdersSpanBuilder(message.getHeaders()).start()))
            {
                logger.info("Voyage request message with key = {} arrived at topic={}", message.getKey(),message.getTopic());

                String eventId = getHeaderAsString(message, "id");

                voyageEventHandler.onReservationEvent(
                        UUID.fromString(eventId),
                        UUID.fromString(message.getKey()),
                        message.getPayload()
                );
            }
        }).thenRun(() -> message.ack());
    }

    private Tracer.SpanBuilder getOrdersSpanBuilder(Headers headers) {
        return tracer.buildSpan("orders").asChildOf(TracingKafkaUtils.extractSpanContext(headers, tracer));
    }

    private String getHeaderAsString(KafkaRecord<?, ?> record, String name) {
        Header header = record.getHeaders().lastHeader(name);
        if (header == null) {
            throw new IllegalArgumentException("Expected record header '" + name + "' not present");
        }

        return new String(header.value(), StandardCharsets.UTF_8);
    }
}
