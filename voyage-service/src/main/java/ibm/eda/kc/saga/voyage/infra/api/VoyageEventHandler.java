/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package ibm.eda.kc.saga.voyage.infra.api;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ibm.eda.kc.saga.voyage.domain.Voyage;
import ibm.eda.kc.saga.voyage.domain.VoyageRequestType;
import ibm.eda.kc.saga.voyage.infra.events.VoyageSagaEvent;
import ibm.eda.kc.saga.voyage.domain.VoyageStatus;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.debezium.outbox.quarkus.ExportedEvent;

@ApplicationScoped
@Traced
public class VoyageEventHandler
{

    private static final Logger logger = LoggerFactory.getLogger(VoyageEventHandler.class);

    @Inject
    MessageLog log;

    @Inject
    Event<ExportedEvent<?, ?>> outboxEvent;

    Voyage currentVoyage;

    @Inject
    EntityManager entityManager;

    @Transactional
    public void onReservationEvent(UUID eventId, UUID sagaId, Voyage event)
    {
        this.currentVoyage = event;
        if (log.alreadyProcessed(eventId)) {
            logger.info("Event with UUID {} was already retrieved, ignoring it", eventId);
            return;
        }
        VoyageStatus status = VoyageStatus.UNKNOWN;

        if (event.type == VoyageRequestType.RESERVE)
        {
            if (event.freeSpaceThisLeg == 666)
            {
                status = VoyageStatus.CANCELLED;
            }
            else
            {
                status = VoyageStatus.RESERVED;
            }
        }
        else
        {
            status = VoyageStatus.CANCELLED;
        }

        logger.info("status={}", status);

        createVoyage(event, status);

        event.persist();
        logger.info("persisted event ID {}", eventId);

        this.outboxEvent.fire(VoyageSagaEvent.of(sagaId, status));
        logger.info("fired outbox event for sagaID={} status={}", sagaId, status);

        log.processed(eventId);
    }

    public void createVoyage(Voyage event, VoyageStatus status)
    {
        event.setDestinationPort("blah");
        event.setFreeSpaceThisLeg(200);
        event.setPlannedDepartureDate("2022-07-07");
        event.setShipID("Maersk-1");
        event.setStatus(status.name());

    }

    @Transactional
    public void createTestData(@Observes StartupEvent se)
    {
        logger.info("in createTestData");
//        String dPort = this.currentVoyage.getDestinatonPort();
//        Long id = this.currentVoyage.getOrderId();
//        entityManager.createNativeQuery("INSERT INTO voyage.voyage (id, destinationPort, freeSpaceThisLeg) VALUES ("+id+","+dPort+", '100')")
//            .executeUpdate();
    }
 }
