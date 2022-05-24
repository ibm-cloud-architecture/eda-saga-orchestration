package ibm.eda.kc.ordersaga.app;

import ibm.eda.kc.ordersaga.domain.ShippingOrder;
import ibm.eda.kc.ordersaga.infra.api.SagaManager;
import ibm.eda.kc.ordersaga.infra.events.container.ContainerEventPayload;
import ibm.eda.kc.ordersaga.infra.events.container.ContainerReservationEvent;
import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageEventPayload;
import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageReservationEvent;
import ibm.eda.kc.ordersaga.saga.OrderPlacementEventHandler;
import ibm.eda.kc.ordersaga.saga.OrderPlacementSaga;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class OrderResource
{
    @Inject
    private SagaManager sagaManager;

    @Inject
    private OrderPlacementEventHandler eventHandler;

    @POST
    @Transactional
    public CreateOrderResponse placeOrder(CreateOrderRequest req) {
        ShippingOrder order = req.toShippingOrder();
        order.persist();

        //begin saga
        sagaManager.begin(OrderPlacementSaga.class, OrderPlacementSaga.payloadFor(order));

        return CreateOrderResponse.fromShippingOrder(order);
    }

    @POST
    @Path("/container")
    @Transactional
    public void onContainerEvent(@HeaderParam("saga-id") UUID sagaId, @HeaderParam("message-id") UUID messageId, ContainerEventPayload event) {
        eventHandler.onContainerReservationEvent(new ContainerReservationEvent(sagaId, messageId, event.status, null));
    }

    @POST
    @Path("/voyage")
    @Transactional
    public void onVoyageEvent(@HeaderParam("saga-id") UUID sagaId, @HeaderParam("message-id") UUID messageId, VoyageEventPayload event) {
        eventHandler.onVoyageReservationEvent(new VoyageReservationEvent(sagaId, messageId, event.status, null));
    }
}
