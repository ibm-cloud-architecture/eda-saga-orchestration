package ibm.eda.kc.saga.voyage.domain;


import ibm.eda.kc.ordersaga.infra.api.SagaStepStatus;

public enum VoyageStatus
{
    RESERVED, CANCELLED, UNKNOWN;
    public SagaStepStatus toStepStatus()
{

    switch (this)
    {
        case CANCELLED:
            return SagaStepStatus.COMPENSATED;
        case RESERVED:
            return SagaStepStatus.SUCCEEDED;
        case UNKNOWN:
            return SagaStepStatus.FAILED;
        default:
            throw new IllegalArgumentException("Unexpected state: " + this);
    }
}


//    public SagaStepStatus toStepStatus()
//    {
//        return this == CANCELLED ? SagaStepStatus.COMPENSATED : this == REJECTED ? SagaStepStatus.FAILED : SagaStepStatus.SUCCEEDED;
//    }
}
