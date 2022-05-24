package ibm.eda.kc.saga.order.voyage.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents the Voyage entity
 */
public class Voyage extends PanacheEntity
{

    public static final String PENDING_STATUS = "pending";
    public static final String CANCELLED_STATUS = "cancelled";
    public static final String RESERVED_STATUS = "assigned";
    public static final String REJECTED_STATUS = "rejected";

    @JsonProperty("voyage-id")
    public String voyageID;

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

    public Voyage(String voyageID, String shipID, String originPort, String destinationPort, String plannedDepartureDate,
                  int freeSpaceThisLeg)
    {
        super();
        this.voyageID = voyageID;
        this.shipID = shipID;
        this.originPort = originPort;
        this.destinationPort = destinationPort;
        this.plannedDepartureDate = plannedDepartureDate;
        this.freeSpaceThisLeg = freeSpaceThisLeg;
    }
   

    // Implement what can be updated in a voyage from the customer update order command.
    // For now, we are updating an existing order with whatever comes from the update order command.
    public void update(Voyage voyage) 
    {
        this.voyageID = voyage.getVoyageID();
	}

    public void cancelVoyage(){
        this.status = Voyage.CANCELLED_STATUS;
    }

    public void getVoyageID(String vid) {
		this.voyageID = vid;
	}
    public void setOrderID(String vid) {
		this.voyageID = vid;
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


    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

	


	public String getVoyageID() {
		return voyageID;
	}
}
