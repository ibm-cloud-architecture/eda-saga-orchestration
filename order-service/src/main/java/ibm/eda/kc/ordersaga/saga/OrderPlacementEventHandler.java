package ibm.eda.kc.ordersaga.saga;

import ibm.eda.kc.ordersaga.infra.api.SagaManager;
import ibm.eda.kc.ordersaga.infra.events.container.ContainerReservationEvent;
import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageReservationEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class OrderPlacementEventHandler
{
    @Inject
    private SagaManager sagaManager;

    @Transactional
    public void onVoyageReservationEvent(VoyageReservationEvent event) {
        OrderPlacementSaga saga = sagaManager.find(OrderPlacementSaga.class, event.sagaId);

        if (saga == null) {
            return;
        }

        saga.onVoyageEvent(event);
    }

    @Transactional
    public void onContainerReservationEvent(ContainerReservationEvent event) {
        OrderPlacementSaga saga = sagaManager.find(OrderPlacementSaga.class, event.sagaId);

        if (saga == null) {
            return;
        }

        saga.onContainerEvent(event);
    }
}
