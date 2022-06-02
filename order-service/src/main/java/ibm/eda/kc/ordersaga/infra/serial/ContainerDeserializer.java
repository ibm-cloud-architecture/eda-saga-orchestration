package ibm.eda.kc.ordersaga.infra.serial;

import ibm.eda.kc.ordersaga.infra.events.container.ContainerReservedEventPayload;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

import java.awt.event.ContainerEvent;

public class ContainerDeserializer extends ObjectMapperDeserializer<ContainerReservedEventPayload>
{

    public ContainerDeserializer() {
        super(ContainerReservedEventPayload.class);
    }
}
