import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class OrderListRepo implements OrderRepo {
    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public OrderListRepo(List<Order> orders) {
        this.orders = orders;
    }

    public OrderListRepo() {

    }

    public Order getOrderById(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public Order addOrder(Order newOrder) {
        Order newOrderWithActualisedDate = newOrder.withOrderDate(ZonedDateTime.now());
        orders.add(newOrderWithActualisedDate);
        return newOrderWithActualisedDate;
    }

    public void removeOrder(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                orders.remove(order);
                return;
            }
        }
    }
}
