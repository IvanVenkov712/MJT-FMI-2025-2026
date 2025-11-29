package bg.sofia.uni.fmi.mjt.order.analyzer;

import bg.sofia.uni.fmi.mjt.order.domain.Category;
import bg.sofia.uni.fmi.mjt.order.domain.Order;
import bg.sofia.uni.fmi.mjt.order.domain.PaymentMethod;
import bg.sofia.uni.fmi.mjt.order.domain.Status;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderAnalyzerImpl implements OrderAnalyzer {
    private final List<Order> orders;
    private static final double MIN_PRICE = 100.0;
    private static final int MAX_ORDERS = 3;

    private static final Comparator<Map.Entry<PaymentMethod, Long>> PAYMENT_METHOD_COMP =
        Map.Entry.<PaymentMethod, Long>comparingByValue().thenComparing(
            Comparator.comparing(
                (Map.Entry<PaymentMethod, Long> e) -> e.getKey().toString()
            ).reversed()
        );

    private static final Comparator<Map.Entry<String, Long>> LOCATION_COMP =
        Map.Entry.<String, Long>comparingByValue().thenComparing(
            Map.Entry.<String, Long>comparingByKey().reversed()
        );

    public OrderAnalyzerImpl(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public List<Order> allOrders() {
        return List.copyOf(orders);
    }

    @Override
    public List<Order> ordersByCustomer(String customer) {
        if (customer == null || customer.isBlank()) {
            throw new IllegalArgumentException("customer is empty or blank");
        }
        return orders.stream()
            .filter((order) -> order.customerName().equals(customer))
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Map.Entry<LocalDate, Long> dateWithMostOrders() {
        return orders.stream()
            .collect(Collectors.groupingBy(Order::date, Collectors.counting()))
            .entrySet().stream()
            .max(
                Comparator.<Map.Entry<LocalDate, Long>>comparingLong(Map.Entry::getValue)
                .thenComparing(
                    Map.Entry.<LocalDate, Long>comparingByKey().reversed()
                )
            ).orElse(null);
    }

    @Override
    public List<String> topNMostOrderedProducts(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n is negative");
        }
        return orders.stream()
            .collect(Collectors.groupingBy(Order::product, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
            .limit(n).map(Map.Entry::getKey).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Map<Category, Double> revenueByCategory() {
        return orders.stream()
            .collect(Collectors.groupingBy(Order::category, Collectors.summingDouble(Order::totalSales)));
    }

    @Override
    public Set<String> suspiciousCustomers() {
        return orders.stream()
            .collect(Collectors.groupingBy(
                Order::customerName,
                Collectors.filtering(
                    o -> o.price() < MIN_PRICE && o.status() == Status.CANCELLED,
                    Collectors.counting()
                )))
            .entrySet().stream()
            .filter(e -> e.getValue() > MAX_ORDERS)
            .map(Map.Entry::getKey).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Map<Category, PaymentMethod> mostUsedPaymentMethodForCategory() {
        return orders.stream().collect(
            Collectors.groupingBy(
                Order::category,
                Collectors.collectingAndThen(
                    Collectors.groupingBy(Order::paymentMethod, Collectors.counting()),
                    map -> map.entrySet().stream().max(PAYMENT_METHOD_COMP)
                        .orElse(Map.entry(PaymentMethod.CREDIT_CARD, 0L)).getKey()
                )
            )
        );
    }

    @Override
    public String locationWithMostOrders() {
        return orders.stream().collect(
            Collectors.groupingBy(Order::customerLocation, Collectors.counting())
        ).entrySet().stream().max(LOCATION_COMP).map(Map.Entry::getKey).orElse(null);
    }

    @Override
    public Map<Category, Map<Status, Long>> groupByCategoryAndStatus() {
        return orders.stream().collect(
            Collectors.groupingBy(
                Order::category,
                Collectors.groupingBy(
                    Order::status,
                    Collectors.counting()
                )
            )
        );
    }
}
