package ibm.eda.kc.saga.voyage.infra.events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ibm.eda.kc.saga.voyage.domain.VoyageStatus;
import io.debezium.outbox.quarkus.ExportedEvent;

import java.time.Instant;
import java.util.UUID;

public class VoyageSagaEvent implements ExportedEvent<String, JsonNode>
{
    private static ObjectMapper mapper = new ObjectMapper();

    private final UUID sagaId;
    private final JsonNode payload;
    private final Instant timestamp;
    private String type;

    private VoyageSagaEvent(UUID sagaId, JsonNode payload) {
        this.sagaId = sagaId;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public static VoyageSagaEvent of(UUID sagaId, VoyageStatus status) {
        ObjectNode asJson = mapper.createObjectNode()
                .put("status", status.name());

        return new VoyageSagaEvent(sagaId, asJson);
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(sagaId);
    }

    @Override
    public String getAggregateType() {
        return "voyage-reservation";
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
