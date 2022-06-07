
package ibm.eda.kc.saga.container.infra.api;

import ibm.eda.kc.saga.container.domain.Container;
import ibm.eda.kc.saga.container.domain.ContainerRequestType;
import ibm.eda.kc.saga.container.domain.ContainerStatus;

import ibm.eda.kc.saga.container.infra.events.ContainerSagaEvent;
import io.debezium.outbox.quarkus.ExportedEvent;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
@Traced
public class ContainerEventHandler
{

    private static final Logger logger = LoggerFactory.getLogger(ContainerEventHandler.class);

    @Inject
    MessageLog log;

    @Inject
    Event<ExportedEvent<?, ?>> outBoxevent;

    @Inject
    EntityManager entityManager;

    @Transactional
    public void onReservationEvent(UUID eventId, UUID sagaId, Container event)
    {
        if (log.alreadyProcessed(eventId))
        {
            logger.info("Event with UUID {} was already retrieved, ignoring it", eventId);
            return;
        }
        else
        {
            logger.info("Handling Container event {}", event.orderId);
        }
        ContainerStatus status;

        if (event.type == ContainerRequestType.RESERVE)
        {
            if (event.freeSpaceThisLeg == 666)
            {
                status = ContainerStatus.REJECTED;
            }
            else
            {
                status = ContainerStatus.RESERVED;
            }
        }
        else
        {
            status = ContainerStatus.CANCELLED;
        }

        createContainer(event, status);

        event.persist();
        logger.info("Persisted event {}",event.orderId);

        this.outBoxevent.fire(ContainerSagaEvent.of(sagaId, status));

        log.processed(eventId);
        logger.info("MessageLog has process {}", eventId);
    }

    public void createContainer(Container event, ContainerStatus status)
    {
        event.setDestinatonPort("San Francisco");
        event.setOriginPort("Mombasa, Kenya");
        event.setPlannedDepartureDate("2022-07-15");
        event.setStatus(status.name());
    }

//    @Transactional
//    public void createTestData(@Observes StartupEvent se)
//    {
//        entityManager.createNativeQuery("INSERT INTO voyage.voyage (id, status) VALUES (123, 'pending')")
//            .executeUpdate();
//    }
 }
