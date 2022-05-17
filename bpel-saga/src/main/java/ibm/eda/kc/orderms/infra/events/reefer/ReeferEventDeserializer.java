package ibm.eda.kc.orderms.infra.events.reefer;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ReeferEventDeserializer extends ObjectMapperDeserializer<ReeferEvent> {
    public ReeferEventDeserializer(){
        // pass the class to the parent.
        super(ReeferEvent.class);
    }
    
}
