package ibm.eda.kc.saga.voyage.infra.api;

import ibm.eda.kc.saga.voyage.domain.Voyage;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class VoyageDeserializer extends ObjectMapperDeserializer<Voyage>
{

    public VoyageDeserializer() {
        super(Voyage.class);
    }
}
