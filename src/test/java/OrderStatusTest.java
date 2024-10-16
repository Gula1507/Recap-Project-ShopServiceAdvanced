import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void values() {
        assertEquals(3, OrderStatus.values().length);
    }

    @Test
    void valueOf() {
        assertEquals(OrderStatus.PROCESSING, OrderStatus.values()[0]);
        assertEquals(OrderStatus.IN_DELIVERY, OrderStatus.values()[1]);
        assertEquals(OrderStatus.COMPLETED, OrderStatus.values()[2]);
    }
}