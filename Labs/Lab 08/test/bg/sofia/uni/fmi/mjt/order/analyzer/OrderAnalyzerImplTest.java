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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderAnalyzerImplTest {

    private static final List<Order> DATA_SET1 = List.of(
        new Order("ORD0011", LocalDate.parse("17-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.BOOKS,15,2,30, "David Lee", "Boston", PaymentMethod.AMAZON_PAY, Status.PENDING),
        new Order("ORD0012", LocalDate.parse("13-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Jeans", Category.CLOTHING,40,4,160, "Michael Brown","Dallas", PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0013", LocalDate.parse("01-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Laptop", Category.ELECTRONICS,800,2,1600, "Daniel Harris", "San Francisco", PaymentMethod.GIFT_CARD,Status.PENDING),
        new Order("ORD0014", LocalDate.parse("04-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Washing Machine", Category.HOME_APPLIANCES,600,3,1800, "Michael Brown", "Miami",PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0015", LocalDate.parse("20-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartwatch", Category.ELECTRONICS,150,4,600,"John Doe", "Seattle",PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0016", LocalDate.parse("26-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Refrigerator", Category.HOME_APPLIANCES,1200,1,1200,"John Doe", "Boston", PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0017", LocalDate.parse("01-04-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"T-Shirt", Category.CLOTHING,20,1,20, "Emma Clark", "New York", PaymentMethod.AMAZON_PAY,Status.COMPLETED),
        new Order("ORD0018", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartphone", Category.ELECTRONICS,500,2,1000,"Michael Brown","Los Angeles", PaymentMethod.AMAZON_PAY,Status.COMPLETED)
    );

    private static final List<Order> DATA_SET2 = List.of(
        new Order("ORD0011", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.BOOKS,15,2,30, "David Lee", "Boston", PaymentMethod.AMAZON_PAY, Status.PENDING),
        new Order("ORD0012", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Jeans", Category.CLOTHING,40,4,160, "Michael Brown","Dallas", PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0013", LocalDate.parse("11-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Laptop", Category.ELECTRONICS,800,2,1600, "Daniel Harris", "San Francisco", PaymentMethod.GIFT_CARD,Status.PENDING),
        new Order("ORD0014", LocalDate.parse("11-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Washing Machine", Category.HOME_APPLIANCES,600,3,1800, "Michael Brown", "Miami",PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0015", LocalDate.parse("20-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartwatch", Category.ELECTRONICS,150,4,600,"John Doe", "Seattle",PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0016", LocalDate.parse("26-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Refrigerator", Category.HOME_APPLIANCES,1200,1,1200,"John Doe", "Boston", PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0017", LocalDate.parse("01-04-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"T-Shirt", Category.CLOTHING,20,1,20, "Emma Clark", "New York", PaymentMethod.AMAZON_PAY,Status.COMPLETED),
        new Order("ORD0018", LocalDate.parse("28-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartphone", Category.ELECTRONICS,500,2,1000,"Michael Brown","Los Angeles", PaymentMethod.AMAZON_PAY,Status.COMPLETED)
    );

    private static final List<Order> DATA_SET3 = List.of(
        new Order("ORD0011", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.BOOKS,15,2,30, "David Lee", "Boston", PaymentMethod.AMAZON_PAY, Status.PENDING),
        new Order("ORD0012", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.CLOTHING,40,4,160, "Michael Brown","Dallas", PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0013", LocalDate.parse("11-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.ELECTRONICS,800,2,1600, "Daniel Harris", "San Francisco", PaymentMethod.GIFT_CARD,Status.PENDING),
        new Order("ORD0014", LocalDate.parse("11-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Car", Category.HOME_APPLIANCES,600,3,1800, "Michael Brown", "Miami",PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0015", LocalDate.parse("20-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Car", Category.ELECTRONICS,150,4,600,"John Doe", "Seattle",PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0016", LocalDate.parse("26-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Car", Category.HOME_APPLIANCES,1200,1,1200,"John Doe", "Boston", PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0017", LocalDate.parse("01-04-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"T-Shirt", Category.CLOTHING,20,1,20, "Emma Clark", "New York", PaymentMethod.AMAZON_PAY,Status.COMPLETED),
        new Order("ORD0018", LocalDate.parse("28-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartphone", Category.ELECTRONICS,500,2,1000,"Michael Brown","Los Angeles", PaymentMethod.AMAZON_PAY,Status.COMPLETED)
    );

    private static final List<Order> DATA_SET4 = List.of(
        new Order("ORD0011", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.BOOKS,15,2,30, "Ivan", "Boston", PaymentMethod.AMAZON_PAY, Status.CANCELLED),
        new Order("ORD0012", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.CLOTHING,40,4,160, "David","Dallas", PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0111", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.BOOKS,15,2,30, "Ivan", "Boston", PaymentMethod.AMAZON_PAY, Status.CANCELLED),
        new Order("ORD0112", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.CLOTHING,40,4,160, "David","Dallas", PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0013", LocalDate.parse("11-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.ELECTRONICS,80,2,1600, "Ivan", "San Francisco", PaymentMethod.GIFT_CARD,Status.CANCELLED),
        new Order("ORD0014", LocalDate.parse("11-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Car", Category.HOME_APPLIANCES,90,3,1800, "Ivan", "Miami",PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0015", LocalDate.parse("20-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Car", Category.ELECTRONICS,12,4,600,"David", "Seattle",PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0016", LocalDate.parse("26-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Car", Category.HOME_APPLIANCES,99.999,1,1200,"David", "Boston", PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0017", LocalDate.parse("01-04-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"T-Shirt", Category.CLOTHING,20,1,20, "Emma Clark", "New York", PaymentMethod.AMAZON_PAY,Status.COMPLETED),
        new Order("ORD0018", LocalDate.parse("28-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartphone", Category.ELECTRONICS,500,2,1000,"Michael Brown","Los Angeles", PaymentMethod.AMAZON_PAY,Status.COMPLETED)
    );

    private static final List<Order> DATA_SET5 = List.of(
        new Order("ORD0011", LocalDate.parse("17-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.ELECTRONICS,15,2,30, "David Lee", "Boston", PaymentMethod.AMAZON_PAY, Status.PENDING),
        new Order("ORD0012", LocalDate.parse("13-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Jeans", Category.ELECTRONICS,40,4,160, "Michael Brown","Dallas", PaymentMethod.AMAZON_PAY,Status.COMPLETED),
        new Order("ORD0013", LocalDate.parse("01-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Laptop", Category.ELECTRONICS,800,2,1600, "Daniel Harris", "San Francisco", PaymentMethod.CREDIT_CARD,Status.PENDING),
        new Order("ORD0014", LocalDate.parse("04-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Washing Machine", Category.ELECTRONICS,600,3,1800, "Michael Brown", "Miami",PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0015", LocalDate.parse("20-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartwatch", Category.CLOTHING,150,4,600,"John Doe", "Seattle",PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0016", LocalDate.parse("26-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Refrigerator", Category.CLOTHING,1200,1,1200,"John Doe", "Boston", PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0017", LocalDate.parse("01-04-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"T-Shirt", Category.CLOTHING,20,1,20, "Emma Clark", "New York", PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0018", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartphone", Category.CLOTHING,500,2,1000,"Michael Brown","Los Angeles", PaymentMethod.AMAZON_PAY,Status.COMPLETED)
    );

    private static final List<Order> DATA_SET6 = List.of(
        new Order("ORD0011", LocalDate.parse("17-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.ELECTRONICS,15,2,30, "David Lee", "Boston", PaymentMethod.AMAZON_PAY, Status.PENDING),
        new Order("ORD0012", LocalDate.parse("13-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Jeans", Category.ELECTRONICS,40,4,160, "Michael Brown","Boston", PaymentMethod.AMAZON_PAY,Status.COMPLETED),
        new Order("ORD0013", LocalDate.parse("01-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Laptop", Category.ELECTRONICS,800,2,1600, "Daniel Harris", "Astana", PaymentMethod.CREDIT_CARD,Status.PENDING),
        new Order("ORD0014", LocalDate.parse("04-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Washing Machine", Category.ELECTRONICS,600,3,1800, "Michael Brown", "Astana",PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0015", LocalDate.parse("20-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartwatch", Category.CLOTHING,150,4,600,"John Doe", "Astana",PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0016", LocalDate.parse("26-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Refrigerator", Category.CLOTHING,1200,1,1200,"John Doe", "Boston", PaymentMethod.CREDIT_CARD,Status.CANCELLED),
        new Order("ORD0017", LocalDate.parse("01-04-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"T-Shirt", Category.CLOTHING,20,1,20, "Emma Clark", "New York", PaymentMethod.CREDIT_CARD,Status.COMPLETED),
        new Order("ORD0018", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartphone", Category.CLOTHING,500,2,1000,"Michael Brown","Los Angeles", PaymentMethod.AMAZON_PAY,Status.COMPLETED)
    );

    private OrderAnalyzerImpl orderAnalyzer;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testOrdersByCustomerNullCustomer() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET1);
        assertThrows(IllegalArgumentException.class, () -> orderAnalyzer.ordersByCustomer(null), "IllegalArgumentException expected");
    }

    @Test
    void testOrdersByCustomerBlankCustomer() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET1);
        assertThrows(IllegalArgumentException.class, () -> orderAnalyzer.ordersByCustomer("      "), "IllegalArgumentException expected");
    }

    @Test
    void testOrdersByCustomerExistingCustomer() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET1);
        List<Order> expected = List.of( DATA_SET1.get(1), DATA_SET1.get(3), DATA_SET1.get(7));
        List<Order> actual = orderAnalyzer.ordersByCustomer("Michael Brown");
        assertIterableEquals(expected, actual, "not what was expected");
    }

    @Test
    void testOrdersByCustomerNotExistingCustomer() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET1);
        List<Order> actual = orderAnalyzer.ordersByCustomer("koko");
        assertIterableEquals(List.of(), actual, "not what was expected");
    }

    @Test
    void testDateWithMostOrdersEmptySet() {
        orderAnalyzer = new OrderAnalyzerImpl(List.of());
        assertNull(orderAnalyzer.dateWithMostOrders(), "null expected");
    }

    @Test
    void testDateWithMostOrdersDataSet1() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET1);
        var expected = Map.entry(LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")), 1L);
        var actual = orderAnalyzer.dateWithMostOrders();
        assertEquals(expected, actual, "not what was expected");
    }

    @Test
    void testDateWithMostOrdersDataSet2() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET2);
        var expected = Map.entry(LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")), 2L);
        var actual = orderAnalyzer.dateWithMostOrders();
        assertEquals(expected, actual, "not what was expected");
    }

    @Test
    void testTopNMostOrderedProductsNegativeN() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET1);
        assertThrows(IllegalArgumentException.class, () -> orderAnalyzer.topNMostOrderedProducts(-1), "IllegalArgumentException expected");
    }

    @Test
    void testTopNMostOrderedProductsDataSet3N1() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET3);
        var expected = List.of("Book");
        var actual = orderAnalyzer.topNMostOrderedProducts(1);
        assertIterableEquals(expected, actual, "[ Book ] expected");
    }

    @Test
    void testTopNMostOrderedProductsDataSet3N2() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET3);
        var expected = List.of("Book", "Car");
        var actual = orderAnalyzer.topNMostOrderedProducts(2);
        assertIterableEquals(expected, actual, "[ Book, Car ] expected");
    }

    @Test
    void testTopNMostOrderedProductsDataSet1N8() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET1);
        var expected = List.of("Book", "Jeans", "Laptop", "Refrigerator", "Smartphone", "Smartwatch", "T-Shirt", "Washing Machine");
        var actual = orderAnalyzer.topNMostOrderedProducts(8);
        assertIterableEquals(expected, actual, "[ \"Book\", \"Jeans\", \"Laptop\", \"Refrigerator\", \"Smartphone\", \"Smartwatch\", \"T-Shirt\", \"Washing Machine\" ] expected");
    }

    @Test
    void testRevenueByCategoryEmptySet() {
        orderAnalyzer = new OrderAnalyzerImpl(List.of());
        assertIterableEquals(Map.of().entrySet(), orderAnalyzer.revenueByCategory().entrySet(), "empty map expected");
    }

    @Test
    void testRevenueByCategoryDataSet1() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET1);
        var expected = Map.of(
            Category.BOOKS, 30.0,
            Category.CLOTHING, 180.0,
            Category.ELECTRONICS, 3200.0,
            Category.HOME_APPLIANCES, 3000.0
        );
        var actual = orderAnalyzer.revenueByCategory();

        expected.forEach(
            (key, value) -> assertEquals(value, actual.get(key), 0.0000000001, "Value was different at key " + key)
        );
        assertTrue(expected.keySet().containsAll(actual.keySet()), "There are some additional categories!");
    }

    @Test
    void testSuspiciousCustomersEmptySet() {
        orderAnalyzer = new OrderAnalyzerImpl(List.of());
        assertIterableEquals(Set.of(), orderAnalyzer.suspiciousCustomers(), "empty set expected");
    }

    @Test
    void testSuspiciousCustomersDataSet4() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET4);
        var expected = Set.of("Ivan", "David");
        var actual = orderAnalyzer.suspiciousCustomers();
        assertTrue(expected.containsAll(actual), "There are additional customers included");
        assertTrue(actual.containsAll(expected), "Not all customers are included");
    }

    @Test
    void testMostUsedPaymentMethodForCategoryEmptySet() {
        orderAnalyzer = new OrderAnalyzerImpl(List.of());
        assertEquals(Map.of(), orderAnalyzer.mostUsedPaymentMethodForCategory(), "Empty map expected");
    }

    @Test
    void testMostUsedPaymentMethodForCategoryDataSet1() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET1);
        var expected = Map.of(
            Category.BOOKS, PaymentMethod.AMAZON_PAY,
            Category.CLOTHING, PaymentMethod.AMAZON_PAY,
            Category.ELECTRONICS, PaymentMethod.AMAZON_PAY,
            Category.HOME_APPLIANCES, PaymentMethod.CREDIT_CARD
        );
        assertEquals(expected, orderAnalyzer.mostUsedPaymentMethodForCategory(), "Not what was expected");
    }

    @Test
    void testMostUsedPaymentMethodForCategoryDataSet5() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET5);
        var expected = Map.of(
            Category.CLOTHING, PaymentMethod.CREDIT_CARD,
            Category.ELECTRONICS, PaymentMethod.AMAZON_PAY
        );
        assertEquals(expected, orderAnalyzer.mostUsedPaymentMethodForCategory(), "Not what was expected");
    }

    @Test
    void testLocationWithMostOrdersEmptySet() {
        orderAnalyzer = new OrderAnalyzerImpl(List.of());
        assertNull(orderAnalyzer.locationWithMostOrders(), "Must be null for empty set");
    }

    @Test
    void testLocationWithMostOrdersDataSet1() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET1);
        assertEquals("Boston", orderAnalyzer.locationWithMostOrders(), "Boston expected");
    }

    @Test
    void testLocationWithMostOrdersDataSet6() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET6);
        assertEquals("Astana", orderAnalyzer.locationWithMostOrders(), "Astana expected");
    }

    @Test
    void testGroupByCategoryAndStatus() {
        orderAnalyzer = new OrderAnalyzerImpl(List.of());
        assertEquals(Map.of(), orderAnalyzer.groupByCategoryAndStatus(), "Empty map expected");
    }

    @Test
    void testGroupByCategoryAndStatusDataSet1() {
        orderAnalyzer = new OrderAnalyzerImpl(DATA_SET1);
        var expected = Map.of(
            Category.BOOKS, Map.of(Status.PENDING, 1L),
            Category.CLOTHING, Map.of(Status.COMPLETED, 2L),
            Category.ELECTRONICS, Map.of(Status.PENDING, 1L, Status.COMPLETED, 2L),
            Category.HOME_APPLIANCES, Map.of(Status.CANCELLED, 2L)
        );
        var actual = orderAnalyzer.groupByCategoryAndStatus();
        assertEquals(expected, actual, "Not what was expected!");
    }

}