package ibm.eda.kc.orderms.infra.repo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.ObjectMapper;

import ibm.eda.kc.orderms.domain.ShippingOrder;

@ApplicationScoped
public class OrderRepositoryMem implements OrderRepository {
    private static ConcurrentHashMap<String,ShippingOrder> repo = new ConcurrentHashMap<String,ShippingOrder>();

    private static ObjectMapper mapper = new ObjectMapper();
    

    public OrderRepositoryMem() {
        super();
        InputStream is = getClass().getClassLoader().getResourceAsStream("orders.json");
        if (is == null) 
            throw new IllegalAccessError("file not found for order json");
        try {
            List<ShippingOrder> currentDefinitions = mapper.readValue(is, mapper.getTypeFactory().constructCollectionType(List.class, ShippingOrder.class));
            currentDefinitions.stream().forEach( (t) -> repo.put(t.getOrderID(),t));
        } catch (IOException e) {
            e.printStackTrace();
        }
        repo.values().stream().forEach(v -> System.out.println(v.orderID));
    }

    public List<ShippingOrder> getAll(){
        return new ArrayList<ShippingOrder>(repo.values());
    }

    public void addOrder(ShippingOrder entity) {
        repo.put(entity.getOrderID(), entity);
    }

    public void updateOrder(ShippingOrder entity) {
        repo.put(entity.getOrderID(), entity);
    }

    @Override
    public ShippingOrder findById(String key) {
        return repo.get(key);
    }
}
