package bg.sofia.uni.fmi.mjt.order.analyzer;

import bg.sofia.uni.fmi.mjt.order.domain.Category;
import bg.sofia.uni.fmi.mjt.order.domain.Order;
import bg.sofia.uni.fmi.mjt.order.domain.PaymentMethod;
import bg.sofia.uni.fmi.mjt.order.domain.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderAnalyzerImplTest {

    private static final List<Order> DATA_SET = List.of(
        new Order("ORD0011", LocalDate.parse("17-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.BOOKS,15,2,30, "David Lee", "Boston", PaymentMethod.AMAZON_PAY, Status.PENDING),
        new Order("ORD0012", LocalDate.parse("13-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Jeans", Category.CLOTHING,40,4,160, "Michael Brown","Dallas", PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0013", LocalDate.parse("01-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Laptop", Category.ELECTRONICS,800,2,1600, "Daniel Harris", "San Francisco", PaymentMethod.GIFT_CARD,Status.PENDING),
        new Order("ORD0014", LocalDate.parse("04-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Washing Machine", Category.HOME_APPLIANCES,600,3,1800, "Michael Brown", "Miami",PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0015", LocalDate.parse("20-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartwatch", Category.ELECTRONICS,150,4,600,"John Doe", "Seattle",PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0016", LocalDate.parse("26-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Refrigerator", Category.HOME_APPLIANCES,1200,1,1200,"John Doe", "Boston", PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0017", LocalDate.parse("01-04-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"T-Shirt", Category.CLOTHING,20,1,20, "Emma Clark", "New York", PaymentMethod.AMAZON_PAY,Status.COMPLETED),
        new Order("ORD0018", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartphone", Category.ELECTRONICS,500,2,1000,"Michael Brown","Los Angeles", PaymentMethod.AMAZON_PAY,Status.COMPLETED)
    );

    private OrderAnalyzerImpl orderAnalyzer;

    @BeforeEach
    void setUp() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET);
    }

    @Test
    void testOrdersByCustomerNullCustomer() {
        assertThrows(IllegalArgumentException.class, () -> orderAnalyzer.ordersByCustomer(null), "IllegalArgumentException expected");
    }

    @Test
    void testOrdersByCustomerBlankCustomer() {
        assertThrows(IllegalArgumentException.class, () -> orderAnalyzer.ordersByCustomer("      "), "IllegalArgumentException expected");
    }

    @Test
    void testOrdersByCustomerExistingCustomer() {
        List<Order> expected = List.of( DATA_SET.get(1), DATA_SET.get(3), DATA_SET.get(7));
        List<Order> actual = orderAnalyzer.ordersByCustomer("Michael Brown");
        assertIterableEquals(expected, actual, "not what was expected");
    }

    @Test
    void testOrdersByCustomerNotExistingCustomer() {
        List<Order> actual = orderAnalyzer.ordersByCustomer("koko");
        assertIterableEquals(List.of(), actual, "not what was expected");
    }

    @Test
    void testDateWithMostOrdersEmptySet() {
        orderAnalyzer = new OrderAnalyzerImpl(List.of());
        assertNull(orderAnalyzer.dateWithMostOrders(), "null expected");
    }

    @Test
    void testDateWithMostOrdersActualSet() {
        var expected = Map.entry(LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")), Long.valueOf(1));
        var actual = orderAnalyzer.dateWithMostOrders();
        assertEquals(expected, actual, "not what was expected");
    }

    @Test
    void testTopNMostOrderedProductsNegativeN() {
        assertThrows(IllegalArgumentException.class, () -> orderAnalyzer.topNMostOrderedProducts(-1), "IllegalArgumentException expected");
    }

    @Test
    void testTopNMostOrderedProductsTop2() {

    }
}