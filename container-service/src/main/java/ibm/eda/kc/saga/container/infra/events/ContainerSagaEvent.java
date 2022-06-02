package ibm.eda.kc.saga.container.infra.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ibm.eda.kc.saga.container.domain.ContainerStatus;
import io.debezium.outbox.quarkus.ExportedEvent;

import java.time.Instant;
import java.util.UUID;

public class ContainerSagaEvent implements ExportedEvent<String, JsonNode>
{
    private static ObjectMapper mapper = new ObjectMapper();

    public final UUID sagaId;
    public final JsonNode payload;
    public final Instant timestamp;
    public String type;

    private ContainerSagaEvent(UUID sagaId, JsonNode payload) {
        this.sagaId = sagaId;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public static ContainerSagaEvent of(UUID sagaId, ContainerStatus status) {
        ObjectNode asJson = mapper.createObjectNode()
                .put("status", status.name());

        return new ContainerSagaEvent(sagaId, asJson);
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
