import java.util.List;

public record Order(
        String id,
        List<Product> products,
        OrderStatus OrderStatus
) {
    public Order(String id, List<Product> products) {
        this(id, products, null);
    }
}
