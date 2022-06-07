package ibm.eda.kc.ordersaga.app;

import ibm.eda.kc.ordersaga.domain.ShippingOrder;
import ibm.eda.kc.ordersaga.domain.ShippingOrderStatus;

public class CreateOrderRequest
{
    public long orderId;
    public String productID;
    public String customerID;
    public int quantity;
    public String pickupAddress;
    public String pickupDate;
    public String destinationAddress;
    public String expectedDeliveryDate;
    public String creationDate;
    public String updateDate;
    public String voyageID;
    public String containerID;

    public ShippingOrder toShippingOrder()
    {
        ShippingOrder order = new ShippingOrder();
        order.orderId = orderId;
        order.productID = productID;
        order.customerID = customerID;
        order.quantity = quantity;
        order.pickupAddress = pickupAddress;
        order.pickupDate = pickupDate;
        order.destinationAddress = destinationAddress;
        order.expectedDeliveryDate = expectedDeliveryDate;
        order.creationDate = creationDate;
        order.updateDate = updateDate;
        order.voyageID = voyageID;
        order.containerID = containerID;
        order.status = ShippingOrderStatus.CREATED;

        return order;
    }

}
