package ibm.eda.kc.saga.order.voyage.infra.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ibm.eda.kc.saga.order.voyage.domain.VoyageStatus;
import io.debezium.outbox.quarkus.ExportedEvent;

import java.time.Instant;
import java.util.UUID;

public class VoyageEvent implements ExportedEvent<String, JsonNode>
{
    private static ObjectMapper mapper = new ObjectMapper();

    private final UUID sagaId;
    private final JsonNode payload;
    private final Instant timestamp;
    private String type;

    private VoyageEvent(UUID sagaId, JsonNode payload) {
        this.sagaId = sagaId;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public static VoyageEvent of(UUID sagaId, VoyageStatus status) {
        ObjectNode asJson = mapper.createObjectNode()
                .put("status", status.name());

        return new VoyageEvent(sagaId, asJson);
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(sagaId);
    }

    @Override
    public String getAggregateType() {
        return "voyage";
    }

    @Override
    public JsonNode getPayload() {
        return payload;
    }

    @Override
    public String getType() {
        return "VoyageUpdated";
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

}
