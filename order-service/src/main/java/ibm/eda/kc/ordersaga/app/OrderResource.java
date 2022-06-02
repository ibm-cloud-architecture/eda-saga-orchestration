package ibm.eda.kc.ordersaga.app;

import ibm.eda.kc.ordersaga.domain.ShippingOrder;
import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageSagaEvent;
import ibm.eda.kc.ordersaga.saga.SagaManager;
import ibm.eda.kc.ordersaga.infra.events.container.ContainerSagaEvent;
import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageReservationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
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
    private static final Logger LOG = LoggerFactory.getLogger(OrderResource.class);
    //Annotation qualifier = synchronously ? new SynchronousQualifier() : new AsynchronousQualifier();
    @Inject
    @Any
    Instance<SagaManager> sagaManager;

    @Inject
    @Any
    Instance<OrderPlacementEventHandler> eventHandler;

    @POST
    @Transactional
    public CreateOrderResponse placeOrder(CreateOrderRequest req)
    {
        LOG.info("REST invoke to create order for customer ID {}", req.customerID);
        ShippingOrder order = req.toShippingOrder();
        //persist incoming order
        order.persist();

        //begin order saga flow
        LOG.info("Saga beginning...");
        //create a new record in sagastate table
        sagaManager.get().begin(OrderPlacementSaga.class, OrderPlacementSaga.payloadFor(order));

        LOG.info("Order ID {} placed for customer ID {}", req.customerID, order.orderId);
        return CreateOrderResponse.fromShippingOrder(order);
    }

    @POST
    @Path("/container")
    @Transactional
    public void onContainerEvent(@HeaderParam("saga-id") UUID sagaId, @HeaderParam("message-id") UUID messageId,
                                 ContainerSagaEvent event)
    {
        LOG.info("REST invoke to reserve Container for saga ID {} and message ID {}", sagaId, messageId);
        eventHandler.get().onContainerEvent(new ContainerSagaEvent(sagaId, messageId, event.status, null));
        LOG.info("REST invoke to reserve Container for saga ID {} and message ID {} completed", sagaId, messageId);
    }

    @POST
    @Path("/voyage")
    @Transactional
    public void onVoyageEvent(@HeaderParam("saga-id") UUID sagaId, @HeaderParam("message-id") UUID messageId,
                              VoyageSagaEvent event)
    //public void onVoyageEvent(@HeaderParam("saga-id") UUID sagaId, @HeaderParam("message-id") UUID messageId)
    {
        LOG.info("REST invoke to reserve Voyage for saga ID {} and message ID {}", sagaId, messageId);
        eventHandler.get().onVoyageEvent(new VoyageSagaEvent(sagaId, messageId, VoyageReservationStatus.DEFAULT, null));
        LOG.info("REST invoke to reserve Voyage for saga ID {} and message ID {} completed", sagaId, messageId);
    }
}
