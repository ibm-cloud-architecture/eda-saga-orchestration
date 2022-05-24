
package container.infra.api;

import container.domain.Container;
import container.domain.ContainerRequestType;
import container.domain.ContainerStatus;
import container.infra.events.ContainerEvent;
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
public class ContainerEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerEventHandler.class);

    @Inject
    MessageLog log;

    @Inject
    Event<ExportedEvent<?, ?>> event;

    @Inject
    EntityManager entityManager;

    @Transactional
    public void onReservationEvent(UUID eventId, UUID sagaId, Container event)
    {
        if (log.alreadyProcessed(eventId)) {
            LOGGER.info("Event with UUID {} was already retrieved, ignoring it", eventId);
            return;
        }
        ContainerStatus status;

        if (event.type == ContainerRequestType.RESERVE) {
            if (event.freeSpaceThisLeg == 666) {
                status = ContainerStatus.REJECTED;
            }
            else {
                status = ContainerStatus.RESERVED;
            }
        }
        else {
            status = ContainerStatus.CANCELLED;
        }

        event.persist();

        this.event.fire(ContainerEvent.of(sagaId, status));

        log.processed(eventId);
    }

//    @Transactional
//    public void createTestData(@Observes StartupEvent se) {
//        entityManager.createNativeQuery("INSERT INTO voyage.voyage (id, status) VALUES (123, 'pending')")
//            .executeUpdate();
//    }
 }
