package ibm.eda.kc.ordersaga.infra.serial;

import ibm.eda.kc.ordersaga.infra.events.container.ContainerEventPayload;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ContainerDeserializer extends ObjectMapperDeserializer<ContainerEventPayload>
{

    public ContainerDeserializer() {
        super(ContainerEventPayload.class);
    }
}
