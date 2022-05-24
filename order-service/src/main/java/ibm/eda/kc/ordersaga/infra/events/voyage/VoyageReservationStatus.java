package ibm.eda.kc.ordersaga.infra.events.voyage;

import ibm.eda.kc.ordersaga.infra.api.SagaStepStatus;

public enum VoyageReservationStatus
{
    REQUESTED, CANCELLED, FAILED, COMPLETED;

    public SagaStepStatus toStepStatus() {
        switch(this) {
            case CANCELLED:
                return SagaStepStatus.COMPENSATED;
            case COMPLETED:
            case REQUESTED:
                return SagaStepStatus.SUCCEEDED;
            case FAILED:
                return SagaStepStatus.FAILED;
            default:
                throw new IllegalArgumentException("Unexpected state: " + this);
        }
    }

}
