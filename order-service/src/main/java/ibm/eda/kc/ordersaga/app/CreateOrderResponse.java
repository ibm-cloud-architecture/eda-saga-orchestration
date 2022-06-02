package ibm.eda.kc.ordersaga.app;

import ibm.eda.kc.ordersaga.domain.ShippingOrder;
import ibm.eda.kc.ordersaga.domain.ShippingOrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateOrderResponse
{
    private static final Logger logger = LoggerFactory.getLogger(OrderResource.class);
    public long orderId;
    public ShippingOrderStatus status;

    public static CreateOrderResponse fromShippingOrder(ShippingOrder order)
    {
        logger.info("...in fromShippingOrder()");
        CreateOrderResponse response = new CreateOrderResponse();
        response.orderId = order.id;
        response.status = order.status;
        logger.info("Created order response for Order ID {} and status {}", order.id, order.status);

        return response;
    }
}
