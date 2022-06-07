package ibm.eda.kc.saga.voyage.infra.events.order;

public enum SagaStepStatus
{
    STARTED, FAILED, SUCCEEDED, COMPENSATING, COMPENSATED;
}
