package ibm.eda.kc.saga.container.infra.api;

import ibm.eda.kc.saga.container.domain.Container;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ContainerDeserializer extends ObjectMapperDeserializer<Container>
{

    public ContainerDeserializer() {
        super(Container.class);
    }
}
