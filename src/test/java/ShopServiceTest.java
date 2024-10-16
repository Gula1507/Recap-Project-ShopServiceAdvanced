import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")));
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectException() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");
        //THEN
        assertThrows(ProductIdException.class, () -> shopService.addOrder(productsIds));
    }


    @Nested
    class GetOrdersAndUpdateOrderTests {
        private ShopService shopService;
        private OrderRepo orderRepo;

        @BeforeEach
        void setUp() {
            Product product1 = new Product("1", "pencil");
            Product product2 = new Product("2", "book");
            Product product3 = new Product("3", "couch");

            ProductRepo productRepo = new ProductRepo(new ArrayList<>(List.of(product1, product2, product3)));
            Order order1 = new Order("111", new ArrayList<>(List.of(product1, product2)), OrderStatus.PROCESSING);
            Order order2 = new Order("211", new ArrayList<>(List.of(product3)), OrderStatus.IN_DELIVERY);
            List<Order> orders = new ArrayList<>(List.of(order1, order2));
            orderRepo = new OrderListRepo(orders);
            shopService = new ShopService(productRepo, orderRepo);
        }

        @Test
        void getOrdersByStatus_whenNoOrdersWithStatus_returnOptionalEmpty() {

            //WHEN
            Optional<List<Order>> filteredOrders = shopService.getOrdersByStatus(OrderStatus.COMPLETED);
            Optional<List<Order>> emptyList = Optional.empty();

            //THEN
            assertEquals(emptyList, filteredOrders);

        }

        @Test
        void getOrdersByStatus_whenOrdersWithDeliveryStatus_returnOptionalOrderList() {

            //GIVEN
            Product product3 = new Product("3", "couch");
            Order order2 = new Order("211", new ArrayList<>(List.of(product3)), OrderStatus.IN_DELIVERY);
            List<Order> ordersInDeliveryList = new ArrayList<>(List.of(order2));
            Optional<List<Order>> ordersInDelivery = Optional.of(ordersInDeliveryList);

            //WHEN
            Optional<List<Order>> filteredOrders = shopService.getOrdersByStatus(OrderStatus.IN_DELIVERY);

            //THEN
            assertEquals(ordersInDelivery, filteredOrders);

        }

        @Test
        void updateOrder_updatesStatusToProcessing_ifOrderIdFound() {
            //GIVEN
            Product product3 = new Product("3", "couch");
            Order orderToFind = new Order("211", new ArrayList<>(List.of(product3)), OrderStatus.IN_DELIVERY);

            //WHEN
            shopService.updateOrder("211", OrderStatus.PROCESSING);
            Order expected = orderToFind.withOrderStatus(OrderStatus.PROCESSING);
            Order actual = orderRepo.getOrderById("211");

            //THEN
            assertEquals(expected, actual);

        }

        @Test
        void updateOrder_ThrowOrderIdException_ifOrderIdNotFound() {

            //WHEN & THEN
            assertThrows(OrderIdException.class, () -> shopService.updateOrder("011", OrderStatus.PROCESSING));

        }
    }
}
