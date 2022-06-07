package ibm.eda.kc.ordersaga.infra.events.voyage;

import ibm.eda.kc.ordersaga.saga.SagaStepStatus;

public enum VoyageReservationStatus
{
    RESERVED, CANCELLED, FAILED;

    public SagaStepStatus toStepStatus()
    {
        switch (this)
        {
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

//    public SagaStepStatus toStepStatus()
//    {
//        return this == CANCELLED ? SagaStepStatus.COMPENSATED : this == REJECTED ? SagaStepStatus.FAILED : SagaStepStatus.SUCCEEDED;
//
//    }

}
