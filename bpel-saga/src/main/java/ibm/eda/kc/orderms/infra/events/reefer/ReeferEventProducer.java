package ibm.eda.kc.orderms.infra.events.reefer;

import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;

@ApplicationScoped
public class ReeferEventProducer {
    
    @Channel("reefers")
	public Emitter<ReeferEvent> reeferEventProducer;


    public void sendEvent(String key, ReeferEvent reeferEvent){
		reeferEventProducer.send(Message.of(reeferEvent).addMetadata(OutgoingKafkaRecordMetadata.<String>builder()
			.withKey(key).build())
			.withAck( () -> {
				
				return CompletableFuture.completedFuture(null);
			})
			.withNack( throwable -> {
				return CompletableFuture.completedFuture(null);
			}));
	}

}
