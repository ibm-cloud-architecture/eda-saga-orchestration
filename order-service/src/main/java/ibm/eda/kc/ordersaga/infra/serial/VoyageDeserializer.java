package ibm.eda.kc.ordersaga.infra.serial;

//import ibm.eda.kc.saga.voyage.domain.Voyage;

import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageReservedEventPayload;
import ibm.eda.kc.ordersaga.infra.events.voyage.VoyageSagaEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class VoyageDeserializer extends ObjectMapperDeserializer<VoyageReservedEventPayload>
{

    public VoyageDeserializer() {
        super(VoyageReservedEventPayload.class);
    }
}
