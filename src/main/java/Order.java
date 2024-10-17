import lombok.With;

import java.time.ZonedDateTime;
import java.util.List;


@With
public record Order(
        String id,
        List<Product> products,
        OrderStatus orderStatus,
        ZonedDateTime orderDate
) {
    public Order(String id, List<Product> products) {
        this(id, products, OrderStatus.PROCESSING, ZonedDateTime.now());
    }

    public Order(String id, List<Product> products, OrderStatus orderStatus) {
        this(id, products, OrderStatus.PROCESSING, ZonedDateTime.now());
    }
}
