package ibm.eda.kc.orderms.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Represents the Voyage entity
 */
public class Voyage 
{

    public static final String PENDING_STATUS = "pending";
    public static final String CANCELLED_STATUS = "cancelled";
    public static final String RESERVED_STATUS = "assigned";
    public static final String REJECTED_STATUS = "rejected";
    
    public String voyageID;
    public String shipID;
    public String originPort;
    public String destinationPort;
    public String plannedDepartureDate;
    public int freeSpaceThisLeg;
    public String status;

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
