package ibm.eda.kc.ordersaga.infra.events.voyage;

import ibm.eda.kc.ordersaga.saga.SagaStepStatus;

public enum VoyageReservationStatus
{
    RESERVED, REJECTED, CANCELLED, DEFAULT;

    public SagaStepStatus toStepStatus()
    {
        return this == CANCELLED ? SagaStepStatus.COMPENSATED : this == REJECTED ? SagaStepStatus.FAILED : SagaStepStatus.SUCCEEDED;

    }

}
