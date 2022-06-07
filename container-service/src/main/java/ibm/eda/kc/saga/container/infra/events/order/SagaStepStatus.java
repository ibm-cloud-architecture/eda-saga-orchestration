package ibm.eda.kc.saga.container.infra.events.order;

public enum SagaStepStatus
{
    STARTED, FAILED, SUCCEEDED, COMPENSATING, COMPENSATED;
}
