package ibm.eda.kc.saga.order.voyage.domain;


import ibm.eda.kc.ordersaga.infra.api.SagaStepStatus;

public enum VoyageStatus
{
    RESERVED, REJECTED, CANCELLED;

    public SagaStepStatus toStepStatus()
    {
        return this == CANCELLED ? SagaStepStatus.COMPENSATED : this == REJECTED ? SagaStepStatus.FAILED : SagaStepStatus.SUCCEEDED;
    }
}
