package ibm.eda.kc.orderms.infra.events.reefer;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Generate an event that could have aggregated IDs
 */
@RegisterForReflection
public class ReeferAllocated extends ReeferVariablePayload {
	public String orderID;
	public boolean aggregate = false;
	public String reeferIDs = "";

	public ReeferAllocated() {}

	public ReeferAllocated(String cid,String oid) {
		this.orderID = oid;
		this.reeferIDs = cid;
	} 
}
