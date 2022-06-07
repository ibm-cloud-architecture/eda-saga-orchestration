package ibm.eda.kc.saga.container.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents the Container entity
 */
@Entity
public class Container extends PanacheEntity
{

    public static final String PENDING_STATUS = "pending";
    public static final String CANCELLED_STATUS = "cancelled";
    public static final String RESERVED_STATUS = "assigned";
    public static final String REJECTED_STATUS = "rejected";

    @JsonProperty("order-id")
    public Long orderId;

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
    public ContainerRequestType type;

    public Container()
    {
    }

    public Container(Long orderId, String shipID, String originPort, String destinationPort, String plannedDepartureDate,
                     int freeSpaceThisLeg)
    {
        super();
        this.orderId = orderId;
        this.shipID = shipID;
        this.originPort = originPort;
        this.destinationPort = destinationPort;
        this.plannedDepartureDate = plannedDepartureDate;
        this.freeSpaceThisLeg = freeSpaceThisLeg;
    }
   

    // Implement what can be updated in a container from the customer update order command.
    // For now, we are updating an existing order with whatever comes from the update order command.
    public void update(Container container)
    {
        this.orderId = container.getOrderId();
	}

    public void cancelVoyage(){
        this.status = Container.CANCELLED_STATUS;
    }

    public Long getOrderId() {
		return orderId;
	}
    public void setOrderID(Long orderId) {
		this.orderId = orderId;
	}

    public String getShipID() {
        return shipID;
    }

    public void setShipID(String sid) {
        this.shipID = sid;
    }

    public String getOriginPort() {
        return originPort;
    }

    public void setOriginPort(String origin) {
        this.originPort = origin;
    }

    public String getDestinatonPort() {
        return destinationPort;
    }

    public void setDestinatonPort(String dest) {
        this.destinationPort = dest;
    }

    public String getPlannedDepartureDate() {
        return plannedDepartureDate;
    }

    public void setPlannedDepartureDate(String depart) {
        this.plannedDepartureDate = depart;
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

	


	public Long getVoyageID() {
		return orderId;
	}
}
