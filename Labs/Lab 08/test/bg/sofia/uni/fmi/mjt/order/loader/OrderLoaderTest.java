package bg.sofia.uni.fmi.mjt.order.loader;

import bg.sofia.uni.fmi.mjt.order.domain.Category;
import bg.sofia.uni.fmi.mjt.order.domain.Order;
import bg.sofia.uni.fmi.mjt.order.domain.PaymentMethod;
import bg.sofia.uni.fmi.mjt.order.domain.Status;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderLoaderTest {
    @Test
    void testLoadNullReader() {
        assertThrows(IllegalArgumentException.class, () -> OrderLoader.load(null), "IllegalArgumentException expected");
    }

    @Test
    void testActualData() {
        String data = """
            Order ID,Date,Product,Category,Price,Quantity,Total Sales,Customer Name,Customer Location,Payment Method,Status
            ORD0011,17-02-25,Book,Books,15,2,30,David Lee,Boston,Amazon Pay,Pending
            ORD0012,13-03-25,Jeans,Clothing,40,4,160,Michael Brown,Dallas,Credit Card,Completed
            ORD0013,01-03-25,Laptop,Electronics,800,2,1600,Daniel Harris,San Francisco,Gift Card,Pending
            ORD0014,04-03-25,Washing Machine,Home Appliances,600,3,1800,Michael Brown,Miami,Credit Card,Cancelled
            ORD0015,20-02-25,Smartwatch,Electronics,150,4,600,John Doe,Seattle,Credit Card,Completed
            ORD0016,26-02-25,Refrigerator,Home Appliances,1200,1,1200,John Doe,Boston,Credit Card,Cancelled
            ORD0017,01-04-25,T-Shirt,Clothing,20,1,20,Emma Clark,New York,Amazon Pay,Completed
            ORD0018,10-02-25,Smartphone,Electronics,500,2,1000,Michael Brown,Los Angeles,Amazon Pay,Completed
            """;
        List<Order> expected = List.of(
            new Order("ORD0011", LocalDate.parse("17-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Book", Category.BOOKS,15,2,30, "David Lee", "Boston", PaymentMethod.AMAZON_PAY, Status.PENDING),
            new Order("ORD0012", LocalDate.parse("13-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Jeans", Category.CLOTHING,40,4,160, "Michael Brown","Dallas", PaymentMethod.CREDIT_CARD,Status.COMPLETED),
            new Order("ORD0013", LocalDate.parse("01-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Laptop", Category.ELECTRONICS,800,2,1600, "Daniel Harris", "San Francisco", PaymentMethod.GIFT_CARD,Status.PENDING),
            new Order("ORD0014", LocalDate.parse("04-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Washing Machine", Category.HOME_APPLIANCES,600,3,1800, "Michael Brown", "Miami",PaymentMethod.CREDIT_CARD,Status.CANCELLED),
            new Order("ORD0015", LocalDate.parse("20-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartwatch", Category.ELECTRONICS,150,4,600,"John Doe", "Seattle",PaymentMethod.CREDIT_CARD,Status.COMPLETED),
            new Order("ORD0016", LocalDate.parse("26-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Refrigerator", Category.HOME_APPLIANCES,1200,1,1200,"John Doe", "Boston", PaymentMethod.CREDIT_CARD,Status.CANCELLED),
            new Order("ORD0017", LocalDate.parse("01-04-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"T-Shirt", Category.CLOTHING,20,1,20, "Emma Clark", "New York", PaymentMethod.AMAZON_PAY,Status.COMPLETED),
            new Order("ORD0018", LocalDate.parse("10-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),"Smartphone", Category.ELECTRONICS,500,2,1000,"Michael Brown","Los Angeles", PaymentMethod.AMAZON_PAY,Status.COMPLETED)
        );
        List<Order> actual = OrderLoader.load(new StringReader(data));
        assertEquals(expected, actual, "not what was expected");
    }
}