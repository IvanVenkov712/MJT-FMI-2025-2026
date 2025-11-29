package bg.sofia.uni.fmi.mjt.order.domain;

import java.time.LocalDate;

public record Order(String id, LocalDate date, String product, Category category, double price, int quantity,
                    double totalSales, String customerName, String customerLocation, PaymentMethod paymentMethod,
                    Status status) {
    public static Order of(String line) {

    }
}
