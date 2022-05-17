package ibm.eda.kc.orderms.infra.events.order;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderCreatedEvent extends OrderVariablePayload {
    public String destinationCity;
	public String pickupCity;
	public String creationDate;

    public OrderCreatedEvent(){}
}
