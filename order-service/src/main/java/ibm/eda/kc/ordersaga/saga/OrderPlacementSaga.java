package ibm.eda.kc.ordersaga.saga;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ibm.eda.kc.ordersaga.domain.ShippingOrder;
import ibm.eda.kc.ordersaga.domain.ShippingOrderStatus;
import ibm.eda.kc.ordersaga.infra.api.*;
import ibm.eda.kc.ordersaga.infra.events.container.ContainerReservationEvent;
import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageReservationEvent;
import io.debezium.outbox.quarkus.ExportedEvent;

import javax.enterprise.event.Event;

import static ibm.eda.kc.ordersaga.saga.OrderPlacementSaga.CONTAINER_RESERVATION;
import static ibm.eda.kc.ordersaga.saga.OrderPlacementSaga.VOYAGE_RESERVATION;


@Saga(type="order-placement", stepIds = {VOYAGE_RESERVATION, CONTAINER_RESERVATION})
public class OrderPlacementSaga extends SagaBase
{
    private static final String REQUEST = "REQUEST";
    private static final String CANCEL = "CANCEL";
    protected static final String VOYAGE_RESERVATION = "voyage-reservation";
    protected static final String CONTAINER_RESERVATION = "container-reservation";

    public static JsonNode payloadFor(ShippingOrder shippingOrder) {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode payload = objectMapper.createObjectNode();

        payload.put("order-id", shippingOrder.id);
        payload.put("customer-id", shippingOrder.customerID);
        payload.put("container-id", shippingOrder.containerID);
        payload.put("product-id", shippingOrder.productID);
        payload.put("voyage-id", shippingOrder.voyageID);
        payload.put("creation-date", shippingOrder.creationDate);
        payload.put("destination-address", shippingOrder.destinationAddress);
        payload.put("pickup-address", shippingOrder.pickupAddress);
        payload.put("expected-delivery-date", shippingOrder.expectedDeliveryDate);
        payload.put("pickup-date", shippingOrder.pickupDate);
        payload.put("update-date", shippingOrder.updateDate);
        payload.put("quantity", shippingOrder.quantity);
        payload.put("type", REQUEST);

        return payload;
    }

    public OrderPlacementSaga(Event<ExportedEvent<?, ?>> event, SagaState state)
    {
        super(event, state);
    }

    @Override
    public SagaStepMessage getStepMessage(String id)
    {
        if (id.equals(VOYAGE_RESERVATION)) {
            return new SagaStepMessage(VOYAGE_RESERVATION, REQUEST, getPayload());
        }
        else {
            return new SagaStepMessage(CONTAINER_RESERVATION, REQUEST, getPayload());
        }
    }

    @Override
    public SagaStepMessage getCompensatingStepMessage(String id) {
        ObjectNode payload = getPayload().deepCopy();
        payload.put("type", CANCEL);

        return new SagaStepMessage(CONTAINER_RESERVATION, CANCEL, payload);
    }

    public void onVoyageEvent(VoyageReservationEvent event)
    {
        if (alreadyProcessed(event.messageId))
        {
            return;
        }

        onStepEvent(VOYAGE_RESERVATION, event.status.toStepStatus());
        updateOrderStatus();

        processed(event.messageId);
    }

    public void onContainerEvent(ContainerReservationEvent event)
    {
        if (alreadyProcessed(event.messageId))
        {
            return;
        }

        onStepEvent(CONTAINER_RESERVATION, event.status.toStepStatus());
        updateOrderStatus();

        processed(event.messageId);
    }

    private void updateOrderStatus()
    {
        if (getStatus() == SagaStatus.COMPLETED)
        {
            ShippingOrder order = ShippingOrder.findById(getOrderId());
            order.status = ShippingOrderStatus.PROCESSING;
        }
        else if (getStatus() == SagaStatus.ABORTED) {
            ShippingOrder order = ShippingOrder.findById(getOrderId());
            order.status = ShippingOrderStatus.CANCELLED;
        }
    }

    private long getOrderId() {
        return getPayload().get("order-id").asLong();
    }
}
