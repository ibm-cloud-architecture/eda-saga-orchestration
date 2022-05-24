package ibm.eda.kc.ordersaga.infra.events;

public interface EventEmitter {

    public void emit(EventBase event) throws Exception;
    public void safeClose();

}
