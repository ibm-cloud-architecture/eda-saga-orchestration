package container.infra.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import container.domain.ContainerStatus;
import io.debezium.outbox.quarkus.ExportedEvent;

import java.time.Instant;
import java.util.UUID;

public class ContainerEvent implements ExportedEvent<String, JsonNode>
{
    private static ObjectMapper mapper = new ObjectMapper();

    private final UUID sagaId;
    private final JsonNode payload;
    private final Instant timestamp;
    private String type;

    private ContainerEvent(UUID sagaId, JsonNode payload) {
        this.sagaId = sagaId;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public static ContainerEvent of(UUID sagaId, ContainerStatus status) {
        ObjectNode asJson = mapper.createObjectNode()
                .put("status", status.name());

        return new ContainerEvent(sagaId, asJson);
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(sagaId);
    }

    @Override
    public String getAggregateType() {
        return "container";
    }

    @Override
    public JsonNode getPayload() {
        return payload;
    }

    @Override
    public String getType() {
        return "ContainerUpdated";
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

}
