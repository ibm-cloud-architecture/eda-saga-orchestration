package ibm.eda.kc.ordersaga.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ibm.eda.kc.ordersaga.domain.ShippingOrder;
import ibm.eda.kc.ordersaga.domain.ShippingOrderStatus;
import ibm.eda.kc.ordersaga.infra.events.container.ContainerSagaEvent;
import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageSagaEvent;
import ibm.eda.kc.ordersaga.saga.*;
import io.debezium.outbox.quarkus.ExportedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Event;

import static ibm.eda.kc.ordersaga.app.OrderPlacementSaga.CONTAINER_RESERVATION;
import static ibm.eda.kc.ordersaga.app.OrderPlacementSaga.VOYAGE_RESERVATION;

//saga steps in order of execution
@Saga(type="order-placement", stepIds = {VOYAGE_RESERVATION, CONTAINER_RESERVATION})
public class OrderPlacementSaga extends SagaBase
{
    private static final Logger logger = LoggerFactory.getLogger(OrderPlacementSaga.class);
    private static final String RESERVE = "RESERVE";
    private static final String CANCEL = "CANCEL";
    protected static final String VOYAGE_RESERVATION = "voyage-reservation";
    protected static final String CONTAINER_RESERVATION = "container-reservation";

    public static JsonNode payloadFor(ShippingOrder shippingOrder)
    {
        logger.info("In payloadFor()...building REQUEST JsonNode");
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
        payload.put("type", RESERVE);

        return payload;
    }

    public OrderPlacementSaga(Event<ExportedEvent<?, ?>> event, SagaState state)
    {
        super(event, state);
    }

    /**
     *
     * @param id
     * @return Returns outbox message to be emitted for the given step
     */
    @Override
    public SagaStepMessage getStepMessage(String id)
    {
        logger.info("Retrieving saga step {}", id);
        if (id.equals(VOYAGE_RESERVATION))
        {
            return new SagaStepMessage(VOYAGE_RESERVATION, RESERVE, getPayload());
        }
        else
        {
            return new SagaStepMessage(CONTAINER_RESERVATION, RESERVE, getPayload());
        }
    }

    /**
     *
     * @param id
     * @return Outbox message to be emitted for compensating given step
     */
    @Override
    public SagaStepMessage getCompensatingStepMessage(String id)
    {
        logger.info("In getCompensatingStepMessage for ID = {}", id);
        ObjectNode payload = getPayload().deepCopy();
        payload.put("type", CANCEL);

        return new SagaStepMessage(CONTAINER_RESERVATION, CANCEL, payload);
    }

    /**
     * Event handler for "voyage" reply messages.
     *
     * @param event
     */
    public void onVoyageEvent(VoyageSagaEvent event)
    {
        if (alreadyProcessed(event.messageId))
        {
            logger.info("Event {} already processed", event.messageId);
            return;
        }
        else
        {
            logger.info("processing VoyageSagaEvent {}", event.messageId);
        }

        // Callback to update Shipping Order status and saga status.
        // Depending on status this may initiate rollback by applying all compensating messages
        onStepEvent(VOYAGE_RESERVATION, event.status.toStepStatus());
        updateOrderStatus();

        processed(event.messageId);
    }

    /**
     * Event handler for "container" reply messages.
     * @param event
     */
    public void onContainerEvent(ContainerSagaEvent event)
    {
        if (alreadyProcessed(event.messageId))
        {
            logger.info("Message ID {} already processed", event.messageId);
            return;
        }
        else
        {
            logger.info("processing ContainerSagaEvent {}", event.messageId);
        }

        // Callback to update Shipping Order status and saga status.
        // Depending on status this may initiate rollback by applying all compensating messages
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
            logger.info("Saga COMPLETED...updating status to PROCESSING for order {}", order.orderId);
        }
        else if (getStatus() == SagaStatus.ABORTED)
        {
            logger.info("saga is in ABORTED state");
            ShippingOrder order = ShippingOrder.findById(getOrderId());
            order.status = ShippingOrderStatus.CANCELLED;
            logger.info("updating status to CANCELLED for order {}", order.orderId);
        }
    }

    private long getOrderId() {
        return getPayload().get("order-id").asLong();
    }
}
