package ibm.eda.kc.orderms.infra.events.order;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use=Id.DEDUCTION)
@JsonSubTypes({@Type(OrderCreatedEvent.class)})
public abstract class OrderVariablePayload {

}
