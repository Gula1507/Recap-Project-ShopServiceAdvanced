import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapRepo implements OrderRepo{
    private Map<String, Order> orders = new HashMap<>();

    @Override
    public List<Order> getOrders() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public Order getOrderById(String id) {
        return orders.get(id);
    }

    @Override
    public Order addOrder(Order newOrder) {
        Order newOrderWithActualisedDate = newOrder.withOrderDate(ZonedDateTime.now());
        orders.put(newOrderWithActualisedDate.id(), newOrderWithActualisedDate);
        return newOrderWithActualisedDate;
    }

    @Override
    public void removeOrder(String id) {
        orders.remove(id);
    }
}
