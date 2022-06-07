package ibm.eda.kc.ordersaga.infra.events.container;

import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageReservationStatus;
import org.apache.kafka.common.header.Headers;

import java.util.UUID;

public class ContainerReservationEvent
{
    public UUID sagaId;
    public UUID messageId;
    public ContainerReservationStatus status;
    public Headers headers;

    public ContainerReservationEvent(UUID sagaId, UUID messageId, ContainerReservationStatus status, Headers headers)
    {
        this.sagaId = sagaId;
        this.messageId = messageId;
        this.status = status;
        this.headers = headers;
    }

    @Override
    public String toString()
    {
        return "ContainerReservationEvent [sagaId=" + sagaId + ", messageId=" + messageId + ", status=" + status + "]";
    }
}
