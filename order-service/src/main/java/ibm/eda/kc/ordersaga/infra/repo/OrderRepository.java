package ibm.eda.kc.ordersaga.infra.repo;

import java.util.List;

import ibm.eda.kc.ordersaga.domain.ShippingOrder;

public interface OrderRepository {
    public List<ShippingOrder> getAll();
    public void addOrder(ShippingOrder entity);
    public void updateOrder(ShippingOrder entity);
    public ShippingOrder findById(String key);
}
