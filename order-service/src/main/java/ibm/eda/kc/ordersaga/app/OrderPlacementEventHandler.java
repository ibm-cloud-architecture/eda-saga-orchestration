package ibm.eda.kc.ordersaga.app;

import ibm.eda.kc.ordersaga.infra.events.container.ContainerSagaEvent;
import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageSagaEvent;
import ibm.eda.kc.ordersaga.saga.SagaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class OrderPlacementEventHandler
{
    private static final Logger logger = LoggerFactory.getLogger(OrderPlacementEventHandler.class);
    @Inject
    Instance<SagaManager> sagaManager;

    @Transactional
    public void onVoyageEvent(VoyageSagaEvent event)
    {
        logger.info("onVoyageEvent...saga event:\n {}", event.toString());
        OrderPlacementSaga saga = sagaManager.get().find(OrderPlacementSaga.class, event.sagaId);

        if (saga == null)
        {
            logger.info("***saga ID {} not found!",event.sagaId);
            return;
        }

        saga.onVoyageEvent(event);
    }

    @Transactional
    public void onContainerEvent(ContainerSagaEvent event)
    {
        logger.info("onContainerEvent: {}", event.toString());
        OrderPlacementSaga saga = sagaManager.get().find(OrderPlacementSaga.class, event.sagaId);

        if (saga == null)
        {
            logger.info("***saga ID {} not found!",event.sagaId);
            return;
        }

        saga.onContainerEvent(event);
    }
}
