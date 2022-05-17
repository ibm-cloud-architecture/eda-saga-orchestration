package ibm.eda.kc.orderms.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import ibm.eda.kc.orderms.infra.events.order.OrderEvent;
import ibm.eda.kc.orderms.infra.events.order.OrderEventProducer;
import ibm.eda.kc.orderms.infra.repo.OrderRepository;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;


@ApplicationScoped
public class OrderServiceSaga {

    @Inject
	public OrderRepository repository;

	@Inject
	public OrderEventProducer producer;

    public List<ShippingOrder> getAllOrders() {
		return repository.getAll();
	}


    public ShippingOrder createOrder(ShippingOrder order) {
        if (order.orderID == null) {
            order.orderID = UUID.randomUUID().toString();
        }
        if (order.creationDate == null) {
			order.creationDate = LocalDate.now().toString();
		}
		order.updateDate= order.creationDate;
        repository.addOrder(order);
		producer.sendOrderCreationFrom(order);
        
        return order;
    }
}
