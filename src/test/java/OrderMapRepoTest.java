import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product));
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = new Product("1", "Apfel");
        expected.add(new Order("1", List.of(product1)));

        assertEquals(actual, expected);
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product));
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1");

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1));

        assertEquals(actual, expected);
    }

    @Test
    void addOrder_whenAddNewOrder_returnSameOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), OrderStatus.PROCESSING
        );
        //WHEN
        Order actual = repo.addOrder(newOrder);
        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1), OrderStatus.PROCESSING
        );
        assertEquals(actual.id(), expected.id());
        assertEquals(actual.orderStatus(), expected.orderStatus());
        assertEquals(actual.products(), expected.products());
    }

    @Test
    void addOrder_whenAddNewOrder_returnMax1NanoSecOrderTimeDifference() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), OrderStatus.PROCESSING,
                ZonedDateTime.now()
        );
        //WHEN
        Order actual = repo.addOrder(newOrder);
        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1), OrderStatus.PROCESSING,
                ZonedDateTime.now());
        ZonedDateTime actualOrderDate = actual.orderDate();
        ZonedDateTime expectedOrderDate = expected.orderDate();
        long nanoDifference = java.time.Duration.between(actualOrderDate,
                expectedOrderDate).toNanos();
        System.out.println(nanoDifference);
        assertTrue(nanoDifference < 5);

    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertNull(repo.getOrderById("1"));
    }
}
