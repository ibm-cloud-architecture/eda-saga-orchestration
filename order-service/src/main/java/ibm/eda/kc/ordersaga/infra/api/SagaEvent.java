package ibm.eda.kc.ordersaga.infra.api;

import com.fasterxml.jackson.databind.JsonNode;
import io.debezium.outbox.quarkus.ExportedEvent;

import java.time.Instant;
import java.util.UUID;

public class SagaEvent implements ExportedEvent<String, JsonNode>
{
    private final UUID sagaId;
    private final String aggregateType;
    private final String eventType;
    private final JsonNode payload;
    private final Instant timestamp;

    public SagaEvent(UUID sagaId, String aggregateType, String eventType, JsonNode payload) {
        this.sagaId = sagaId;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(sagaId);
    }

    @Override
    public String getAggregateType() {
        return aggregateType;
    }

    @Override
    public JsonNode getPayload() {
        return payload;
    }

    @Override
    public String getType() {
        return eventType;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }
}
