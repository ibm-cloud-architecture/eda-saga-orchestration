package ibm.eda.kc.orderms.infra.events.order;

import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import ibm.eda.kc.orderms.domain.ShippingOrder;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;

@ApplicationScoped
public class OrderEventProducer {
    
    @Channel("orders")
	public Emitter<OrderEvent> eventProducer;

    public void sendOrderCreationFrom(ShippingOrder order) {

        OrderEvent oe = new OrderEvent();
        oe.customerID = order.customerID;
        oe.orderID = order.orderID;
        oe.productID = order.productID;
        oe.quantity = order.quantity;
        oe.type = OrderEvent.ORDER_CREATED_TYPE;
        OrderCreatedEvent oce = new OrderCreatedEvent();
        oce.pickupCity = order.getPickupAddress().getCity();
        oce.destinationCity = order.getDestinationAddress().getCity();
		oe.payload = oce;
        sendOrder(oe.orderID,oe);
    }


    public void sendOrder(String key, OrderEvent orderEvent){
		eventProducer.send(Message.of(orderEvent).addMetadata(OutgoingKafkaRecordMetadata.<String>builder()
			.withKey(key).build())
			.withAck( () -> {
				
				return CompletableFuture.completedFuture(null);
			})
			.withNack( throwable -> {
				return CompletableFuture.completedFuture(null);
			}));
	}

}
