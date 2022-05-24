package ibm.eda.kc.ordersaga.infra.events.voyage;

import org.apache.kafka.common.header.Headers;

import java.util.UUID;

public class VoyageReservationEvent
{
    public UUID sagaId;
    public UUID messageId;
    public VoyageReservationStatus status;
    public Headers headers;

    public VoyageReservationEvent(UUID sagaId, UUID messageId, VoyageReservationStatus status, Headers headers)
    {
        this.sagaId = sagaId;
        this.messageId = messageId;
        this.status = status;
        this.headers = headers;
    }

    @Override
    public String toString()
    {
        return "CreditApprovalEvent [sagaId=" + sagaId + ", messageId=" + messageId + ", status=" + status + "]";
    }
}
