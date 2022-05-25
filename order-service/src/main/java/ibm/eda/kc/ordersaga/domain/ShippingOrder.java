package ibm.eda.kc.ordersaga.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.graalvm.nativeimage.c.struct.UniqueLocationIdentity;

import javax.persistence.*;
import java.util.Map;

/**
 * Represents the order entity
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShippingOrder extends PanacheEntity
{

//    @Id
    public Long orderId;
    public String productID;
    public String customerID;
    public int quantity;

    //public Map<String, Object> pickupAddress;
    public String pickupAddress;
    public String pickupDate;


    //public Map<String, Object> destinationAddress;
    public String destinationAddress;
    public String expectedDeliveryDate;
    public String creationDate;
    public String updateDate;
	public String voyageID;
	public String containerID;

    @Enumerated(EnumType.STRING)
    public ShippingOrderStatus status;

}
