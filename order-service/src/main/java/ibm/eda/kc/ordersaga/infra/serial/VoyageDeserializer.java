package ibm.eda.kc.ordersaga.infra.serial;

import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageEventPayload;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class VoyageDeserializer extends ObjectMapperDeserializer<VoyageEventPayload>
{

    public VoyageDeserializer() {
        super(VoyageEventPayload.class);
    }
}
