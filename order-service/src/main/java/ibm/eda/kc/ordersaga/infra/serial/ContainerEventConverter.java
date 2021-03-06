package ibm.eda.kc.ordersaga.infra.serial;

import ibm.eda.kc.ordersaga.infra.events.container.ContainerReservedEventPayload;
import ibm.eda.kc.ordersaga.infra.events.container.ContainerSagaEvent;
import io.smallrye.reactive.messaging.MessageConverter;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.UUID;

@ApplicationScoped
public class ContainerEventConverter implements MessageConverter {

    @Override
    public boolean canConvert(Message<?> in, Type target) {
        return in.getMetadata(IncomingKafkaRecordMetadata.class).isPresent()
                && target.equals(ContainerSagaEvent.class);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Message<?> convert(Message<?> in, Type target)
    {
        IncomingKafkaRecordMetadata metadata = in.getMetadata(IncomingKafkaRecordMetadata.class)
                .orElseThrow(() -> new IllegalStateException("No Kafka metadata"));

        UUID messageId = UUID.fromString(getHeaderAsString(metadata.getHeaders(), "id"));
        UUID sagaId = UUID.fromString((String) metadata.getKey());

        return in.withPayload(new ContainerSagaEvent(sagaId, messageId,
                ((ContainerReservedEventPayload) in.getPayload()).status, metadata.getHeaders()));
    }

    private String getHeaderAsString(Headers headers, String name) {
        Header header = headers.lastHeader(name);
        if (header == null) {
            throw new IllegalArgumentException("Expected record header '" + name + "' not present");
        }

        return new String(header.value(), Charset.forName("UTF-8"));
    }
}
