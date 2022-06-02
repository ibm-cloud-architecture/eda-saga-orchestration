package ibm.eda.kc.ordersaga.infra.events.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ibm.eda.kc.ordersaga.domain.ShippingOrder;
import io.debezium.outbox.quarkus.ExportedEvent;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.Instant;

@RegisterForReflection
public class OrderCreatedEvent implements ExportedEvent<String, JsonNode>
{

    private static ObjectMapper mapper = new ObjectMapper();
    private long id;
    private final JsonNode order;
    private final Instant timestamp;

    private OrderCreatedEvent(long id, JsonNode order)
    {
        this.id = id;
        this.order = order;
        this.timestamp = Instant.now();
    }

    public static OrderCreatedEvent of(ShippingOrder order) {
        ObjectNode asJson = mapper.createObjectNode()
                .put("id", order.id)
                .put("customerId", order.getCustomerID())
                .put("orderDate", order.getCreationDate());

        ArrayNode items = asJson.putArray("lineItems");
        return new OrderCreatedEvent(order.id, asJson);
    }

    @Override
    public String getAggregateId() {
        return null;
    }

    @Override
    public String getAggregateType() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public Instant getTimestamp() {
        return null;
    }

    @Override
    public JsonNode getPayload() {
        return null;
    }
}
