package ibm.eda.kc.saga.container.domain;

import ibm.eda.kc.ordersaga.infra.api.SagaStepStatus;

public enum ContainerStatus
{
    RESERVED, REJECTED, CANCELLED;

    public SagaStepStatus toStepStatus()
    {
        return this == CANCELLED ? SagaStepStatus.COMPENSATED : this == REJECTED ? SagaStepStatus.FAILED : SagaStepStatus.SUCCEEDED;
    }
}
