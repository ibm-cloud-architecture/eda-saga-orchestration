import ibm.eda.kc.orderms.infra.events.order.OrderCreatedEvent;
import ibm.eda.kc.orderms.infra.events.order.OrderEvent;
import ibm.eda.kc.orderms.infra.events.order.OrderVariablePayload;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class TestOrderCreatedEvent
{
    @Test
    public void orderCreatedEvent()
    {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent("Mombasa", "Liverpool", "05172022");
        OrderEvent orderEvent = new OrderEvent(222L,OrderEvent.ORDER_CREATED_TYPE, "0.1", orderCreatedEvent);
        System.out.println("OrderCreatedEvent: " + orderCreatedEvent.toString());
    }
}
