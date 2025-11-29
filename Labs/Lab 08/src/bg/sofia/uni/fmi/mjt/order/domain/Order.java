package bg.sofia.uni.fmi.mjt.order.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public record Order(String id, LocalDate date, String product, Category category, double price, int quantity,
                    double totalSales, String customerName, String customerLocation, PaymentMethod paymentMethod,
                    Status status) {

    private static final int INDEX_ID = 0;
    private static final int INDEX_DATE = 1;
    private static final int INDEX_PRODUCT = 2;
    private static final int INDEX_CATEGORY = 3;
    private static final int INDEX_PRICE = 4;
    private static final int INDEX_QUANTITY = 5;
    private static final int INDEX_TOTAL_SALES = 6;
    private static final int INDEX_CUSTOMER_NAME = 7;
    private static final int INDEX_CUSTOMER_LOCATION = 8;
    private static final int INDEX_PAYMENT_METHOD = 9;
    private static final int INDEX_STATUS = 10;

    public static Order of(String line) {
        List<String> tokens = Arrays.stream(line.split(",")).map(String::strip).toList();

        return new Order(
            tokens.get(INDEX_ID),
            LocalDate.parse(tokens.get(INDEX_DATE), DateTimeFormatter.ofPattern("dd-MM-yy")),
            tokens.get(INDEX_PRODUCT),
            Category.valueOf(tokens.get(INDEX_CATEGORY)),
            Double.parseDouble(tokens.get(INDEX_PRICE)),
            Integer.parseInt(tokens.get(INDEX_QUANTITY)),
            Double.parseDouble(tokens.get(INDEX_TOTAL_SALES)),
            tokens.get(INDEX_CUSTOMER_NAME),
            tokens.get(INDEX_CUSTOMER_LOCATION),
            PaymentMethod.valueOf(tokens.get(INDEX_PAYMENT_METHOD)),
            Status.valueOf(tokens.get(INDEX_STATUS))
        );
    }
}
