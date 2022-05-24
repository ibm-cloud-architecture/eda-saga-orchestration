package ibm.eda.kc.ordersaga.infra.events.container;

import ibm.eda.kc.ordersaga.infra.api.SagaStepStatus;

public enum ContainerReservationStatus
{
    RESERVED, REJECTED, CANCELLED;

    public SagaStepStatus toStepStatus()
    {
        return this == CANCELLED ? SagaStepStatus.COMPENSATED : this == REJECTED ? SagaStepStatus.FAILED : SagaStepStatus.SUCCEEDED;
    }
}
