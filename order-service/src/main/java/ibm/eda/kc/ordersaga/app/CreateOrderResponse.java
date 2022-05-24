package ibm.eda.kc.ordersaga.app;

import ibm.eda.kc.ordersaga.domain.ShippingOrder;
import ibm.eda.kc.ordersaga.domain.ShippingOrderStatus;

public class CreateOrderResponse
{
    public long orderId;
    public ShippingOrderStatus status;

    public static CreateOrderResponse fromShippingOrder(ShippingOrder order)
    {
        CreateOrderResponse response = new CreateOrderResponse();
        response.orderId = order.id;
        response.status = order.status;

        return response;
    }
}
