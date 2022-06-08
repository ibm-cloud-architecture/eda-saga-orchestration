package ibm.eda.kc.ordersaga.infra.events.container;

import ibm.eda.kc.ordersaga.saga.SagaStepStatus;

public enum ContainerReservationStatus
{
    RESERVED, CANCELLED, FAILED;

    public SagaStepStatus toStepStatus()
    {
        switch(this) {
            case CANCELLED:
                return SagaStepStatus.COMPENSATED;
            case RESERVED:
                return SagaStepStatus.SUCCEEDED;
            case FAILED:
                return SagaStepStatus.FAILED;
            default:
                throw new IllegalArgumentException("Unexpected state: " + this);
        }
    }
}
