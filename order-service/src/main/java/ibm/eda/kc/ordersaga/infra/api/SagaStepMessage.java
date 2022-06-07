package ibm.eda.kc.ordersaga.infra.api;

import com.fasterxml.jackson.databind.JsonNode;

public class SagaStepMessage
{
    public String type;
    public String eventType;
    public JsonNode payload;

    public SagaStepMessage(String type, String eventType, JsonNode payload) {
        this.type = type;
        this.eventType = eventType;
        this.payload = payload;
    }
}
