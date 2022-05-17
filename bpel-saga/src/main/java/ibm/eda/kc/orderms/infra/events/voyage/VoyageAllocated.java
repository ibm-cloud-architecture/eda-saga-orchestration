package ibm.eda.kc.orderms.infra.events.reefer;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Generate a VoyageAllocated event
 */
@RegisterForReflection
public class VoyageAllocated extends VoyageVariablePayload {
	public String voyageID;

	public VoyageAllocated() {}

	public VoyageAllocated(String vid) {
		this.voyageID = vid;
	} 
}
