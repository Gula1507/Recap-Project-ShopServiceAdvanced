import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public ShopService() {

    }

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new ProductIdException("Product mit der Id: " + productId + " konnte nicht bestellt werden!");

            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }

    public Optional<List<Order>> getOrdersByStatus(OrderStatus requiredOrderStatus) {
        List<Order> filteredOrders = orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(requiredOrderStatus))
                .toList();
        return filteredOrders.isEmpty() ? Optional.empty() : Optional.of(filteredOrders);
    }

    public void updateOrder(String orderId, OrderStatus newOderStatus) {
        Order existingOrder = orderRepo.getOrderById(orderId);
        if (existingOrder == null) {
            throw new OrderIdException("There is no orderId: " + orderId);
        } else {
            Order updatedOrder = existingOrder.withOrderStatus(newOderStatus);
            orderRepo.removeOrder(orderId);
            orderRepo.addOrder(updatedOrder);
        }

    }

}
