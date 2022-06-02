package ibm.eda.kc.saga.voyage.infra.events;

import ibm.eda.kc.saga.voyage.domain.Voyage;
import ibm.eda.kc.saga.voyage.domain.VoyageReservationEvent;
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
public class KafkaEventConsumer
{
    private static final Logger LOG = LoggerFactory.getLogger(KafkaEventConsumer.class);

    @Inject
    VoyageEventHandler voyageEventHandler;

    @Inject
    Tracer tracer;

    @Incoming("voyage-request")
    public CompletionStage<Void> onMessage(KafkaRecord<String, Voyage> message) throws IOException {
        return CompletableFuture.runAsync(() -> {
            try (final Scope span = tracer.scopeManager().activate(getOrdersSpanBuilder(message.getHeaders()).start()))
            {
                LOG.info("Voyage request message with key = {} arrived payload={}", message.getKey(),message.getPayload());

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
