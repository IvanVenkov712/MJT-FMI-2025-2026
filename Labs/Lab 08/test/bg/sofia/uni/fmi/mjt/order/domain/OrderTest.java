package bg.sofia.uni.fmi.mjt.order.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOfNullLine() {
        assertThrows(IllegalArgumentException.class, () -> Order.of(null), "IllegalArgumentException expecteed");
    }
    @Test
    void testOfActualData() {
        Order expected = new Order(
            "ORD0007",
            LocalDate.parse("18-03-25", DateTimeFormatter.ofPattern("dd-MM-yy")),
            "Smartwatch",
            Category.ELECTRONICS,
            150,
            4,
            600,
            "Emma Clark",
            "Houston",
            PaymentMethod.PAYPAL,
            Status.COMPLETED
        );
        Order actual = Order.of("ORD0007,18-03-25,Smartwatch,Electronics,150,4,600,Emma Clark,Houston,PayPal,Completed");
        assertEquals(expected, actual, "not what was expected");
    }

    @Test
    void testOfActualDataEnumWS() {
        String data = "ORD0011,17-02-25,Book,Home Appliances,15,2,30,David Lee,Boston,Amazon Pay,Pending";
        Order expected = new Order(
            "ORD0011",
            LocalDate.parse("17-02-25", DateTimeFormatter.ofPattern("dd-MM-yy")),
            "Book",
            Category.HOME_APPLIANCES,
            15,
            2,
            30,
            "David Lee",
            "Boston",
            PaymentMethod.AMAZON_PAY,
            Status.PENDING
        );
        Order actual = Order.of(data);
        assertEquals(expected, actual, "not what was expected");
    }
}