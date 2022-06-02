package ibm.eda.kc.ordersaga.saga;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.UUID;

@TypeDef(name = "jsonb", typeClass = JsonNodeBinaryType.class)
@Entity
public class SagaState
{
    @Id
    private UUID id;

    @Version
    private int version;

    private String type;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode payload;

    private String currentStep;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode stepStatus;

    @Enumerated(EnumType.STRING)
    private SagaStatus sagaStatus;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JsonNode getPayload() {
        return payload;
    }

    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public JsonNode getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(JsonNode stepStatus) {
        this.stepStatus = stepStatus;
    }

    public SagaStatus getSagaStatus() {
        return sagaStatus;
    }

    public void setSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
    }
}
