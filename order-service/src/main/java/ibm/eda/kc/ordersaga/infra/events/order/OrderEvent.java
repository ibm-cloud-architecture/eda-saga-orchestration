package ibm.eda.kc.ordersaga.infra.events.order;

import java.util.Date;
import java.util.UUID;

import ibm.eda.kc.ordersaga.infra.events.EventBase;

/**
 * Order event for the state change of a shipping order
 * 
 * Events are data element, so limit inheritance and polymorphism to the minimum
 * 
 * @author jeromeboyer
 *
 */
public class OrderEvent extends EventBase
{
    public UUID sagaId;
    public UUID messageId;
    public static final String ORDER_CREATED_TYPE = "OrderCreated";
    public static final String ORDER_UPDATED_TYPE = "OrderUpdated";
    public static final String ORDER_REJECTED_TYPE = "OrderRejected";
    public static final String ORDER_CANCELLED_TYPE = "OrderCancelled";
    public String orderID;
    public String productID;
    public String customerID;
    public int quantity;
    public String status;
	
    public OrderVariablePayload  payload;

    public OrderEvent(long timestampMillis, 
                String type, 
                String version, 
                OrderVariablePayload payload) {
        super(timestampMillis, type, version);
        this.payload = payload;
    }

    public OrderEvent( OrderVariablePayload payload) {
        this.payload = payload;
        this.type = ORDER_CREATED_TYPE;
        this.timestampMillis = new Date().getTime();
        this.version = DEFAULT_VERSION;
    }

    public OrderEvent(){
        this.timestampMillis = new Date().getTime();
        this.version = DEFAULT_VERSION;
    }

}
