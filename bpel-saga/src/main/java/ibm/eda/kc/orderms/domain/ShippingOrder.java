package ibm.eda.kc.orderms.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Represents the order entity
 */
public class ShippingOrder {

    public static final String PENDING_STATUS = "pending";
    public static final String CANCELLED_STATUS = "cancelled";
    public static final String ASSIGNED_STATUS = "assigned";
    public static final String REJECTED_STATUS = "rejected";
    public static final String SPOILT_STATUS = "spoilt";
    
    public String orderID;
    public String productID;
    public String customerID;
    public int quantity;

    public Address pickupAddress;
    public String pickupDate;

    public Address destinationAddress;
    public String expectedDeliveryDate;
    public String creationDate;
    public String updateDate;
    public String status;
	public String voyageID;
	public String containerID;

    public ShippingOrder() {
    }

    public ShippingOrder(String orderID, String productID, String customerID, int quantity, Address pickupAddress,
            String pickupDate, Address destinationAddress, String expectedDeliveryDate, String status) {
        super();
        this.orderID = orderID;
        this.productID = productID;
        this.customerID = customerID;
        this.quantity = quantity;
        this.pickupAddress = pickupAddress;
        this.pickupDate = pickupDate;
        this.destinationAddress = destinationAddress;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.status = status;
    }

   

  

    public void setAssignStatus() {
    	if (this.voyageID != null && this.containerID != null) {
    		this.status = ShippingOrder.ASSIGNED_STATUS;
    	}
    }
   

    // Implement what can be updated in an order from the customer update order command.
    // For now, we are updating an existing order with whatever comes from the update order command.
    public void update(ShippingOrder oco) {
        this.containerID = oco.getContainerID();
        this.voyageID = oco.getVoyageID();
	}

    public void spoilOrder(){
        this.status = ShippingOrder.SPOILT_STATUS;
    }

    public void rejectOrder(){
        this.status = ShippingOrder.REJECTED_STATUS;
    }

    public void cancelOrder(){
        this.status = ShippingOrder.CANCELLED_STATUS;
    }
    
    public String getOrderID() {
        return orderID;
    }

    public String getProductID() {
        return productID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Address getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(Address pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

	public void setOrderID(String oid) {
		this.orderID = 	oid;
	}

	public void setQuantity(int value) {
		this.quantity = value;
	}

	public String getVoyageID() {
		return voyageID;
	}

	public String getContainerID() {
		return containerID;
	}
}
