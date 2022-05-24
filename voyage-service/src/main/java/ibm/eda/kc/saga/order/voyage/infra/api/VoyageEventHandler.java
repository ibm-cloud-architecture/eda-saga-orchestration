/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package ibm.eda.kc.saga.order.voyage.infra.api;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ibm.eda.kc.saga.order.voyage.domain.Voyage;
import ibm.eda.kc.saga.order.voyage.domain.VoyageRequestType;
import ibm.eda.kc.saga.order.voyage.infra.events.VoyageEvent;
import ibm.eda.kc.saga.order.voyage.domain.VoyageStatus;
import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.debezium.outbox.quarkus.ExportedEvent;

@ApplicationScoped
@Traced
public class VoyageEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoyageEventHandler.class);

    @Inject
    MessageLog log;

    @Inject
    Event<ExportedEvent<?, ?>> event;

    @Inject
    EntityManager entityManager;

    @Transactional
    public void onReservationEvent(UUID eventId, UUID sagaId, Voyage event)
    {
        if (log.alreadyProcessed(eventId)) {
            LOGGER.info("Event with UUID {} was already retrieved, ignoring it", eventId);
            return;
        }
        VoyageStatus status;

        if (event.type == VoyageRequestType.RESERVE) {
            if (event.freeSpaceThisLeg == 666) {
                status = VoyageStatus.REJECTED;
            }
            else {
                status = VoyageStatus.RESERVED;
            }
        }
        else {
            status = VoyageStatus.CANCELLED;
        }

        event.persist();

        this.event.fire(VoyageEvent.of(sagaId, status));

        log.processed(eventId);
    }

//    @Transactional
//    public void createTestData(@Observes StartupEvent se) {
//        entityManager.createNativeQuery("INSERT INTO voyage.voyage (id, status) VALUES (123, 'pending')")
//            .executeUpdate();
//    }
 }
