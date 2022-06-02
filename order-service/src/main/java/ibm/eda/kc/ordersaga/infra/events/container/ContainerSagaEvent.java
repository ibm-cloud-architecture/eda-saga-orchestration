package ibm.eda.kc.ordersaga.infra.events.container;


import org.apache.kafka.common.header.Headers;

import java.util.UUID;

public class ContainerSagaEvent
{
    public UUID sagaId;
    public UUID messageId;
    public ContainerReservationStatus status;
    public Headers headers;

    public ContainerSagaEvent(UUID sagaId, UUID messageId, ContainerReservationStatus status, Headers headers)
    {
        this.sagaId = sagaId;
        this.messageId = messageId;
        this.status = status;
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "VoyageSagaEvent [sagaId=" + sagaId + ", messageId=" + messageId + ", status=" + status + "]";
    }

}
