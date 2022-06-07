/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package ibm.eda.kc.saga.voyage.infra.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Entity
public class ConsumedMessage
{
    private static final Logger logger = LoggerFactory.getLogger(ConsumedMessage.class);

    @Id
    private UUID eventId;

    private Instant timeOfReceiving;

    ConsumedMessage()
    {
    }

    public ConsumedMessage(UUID eventId, Instant timeOfReceiving) {
        logger.info("consumed event {} at {}", eventId, timeOfReceiving);
        this.eventId = eventId;
        this.timeOfReceiving = timeOfReceiving;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public Instant getTimeOfReceiving() {
        return timeOfReceiving;
    }

    public void setTimeOfReceiving(Instant timeOfReceiving) {
        this.timeOfReceiving = timeOfReceiving;
    }
}
