package ibm.eda.kc.saga.voyage.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents the VoyageReservationEvent entity
 */
@Getter
@Setter
@Entity
public class Voyage extends PanacheEntity
{
    @JsonProperty("order-id")
    public Long orderId;
    @JsonProperty("product-id")
    public String productID;
    @JsonProperty("customer-id")
    public String customerID;
    @JsonProperty("quantity")
    public int quantity;
    @JsonProperty("pickup-address")
    public String pickupAddress;
    @JsonProperty("pickup-date")
    public String pickupDate;
    @JsonProperty("destination-address")
    public String destinationAddress;
    @JsonProperty("expected-delivery")
    public String expectedDeliveryDate;
    @JsonProperty("creation-date")
    public String creationDate;
    @JsonProperty("update-date")
    public String updateDate;
    @JsonProperty("voyage-id")
    public String voyageID;
    @JsonProperty("container-id")
    public String containerID;

    @JsonProperty("ship-id")
    public String shipID;

    @JsonProperty("origin-port")
    public String originPort;

    @JsonProperty("destination-port")
    public String destinationPort;

    @JsonProperty("planned-departure-date")
    public String plannedDepartureDate;

    @JsonProperty("free-space")
    public int freeSpaceThisLeg;

    @JsonProperty("status")
    public String status;

    @Enumerated(EnumType.STRING)
    public VoyageRequestType type;

    public Voyage()
    {
    }

//    public VoyageReservationEvent(String orderId, String shipID, String originPort, String destinationPort, String plannedDepartureDate,
//                                  int freeSpaceThisLeg, VoyageRequestType type)
//    {
//        super();
//        this.orderId = orderId;
//        this.shipID = shipID;
//        this.originPort = originPort;
//        this.destinationPort = destinationPort;
//        this.plannedDepartureDate = plannedDepartureDate;
//        this.freeSpaceThisLeg = freeSpaceThisLeg;
//        this.type = type;
//    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }


}
