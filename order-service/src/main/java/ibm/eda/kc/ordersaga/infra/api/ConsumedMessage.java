package ibm.eda.kc.ordersaga.infra.api;

import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

public class ConsumedMessage
{
    @Id
    private UUID eventId;

    private Instant timeOfReceiving;

    ConsumedMessage()
    {
    }

    public ConsumedMessage(UUID eventId, Instant timeOfReceiving)
    {
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
