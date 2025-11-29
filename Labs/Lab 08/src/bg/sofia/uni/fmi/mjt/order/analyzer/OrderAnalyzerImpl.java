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

    OrderAnalyzerImpl(List<Order> orders) {
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
            .max(Comparator.<Map.Entry<LocalDate, Long>>comparingLong(Map.Entry::getValue)
                .thenComparing(Map.Entry::getKey)).orElse(null);
    }

    @Override
    public List<String> topNMostOrderedProducts(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n is negative");
        }
        return orders.stream()
            .collect(Collectors.groupingBy(Order::product))
            .entrySet().stream().map(e -> Map.entry(e.getKey(), e.getValue().size()))
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
            .limit(n).map(Map.Entry::getKey).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Map<Category, Double> revenueByCategory() {
        return orders.stream()
            .collect(Collectors.groupingBy(Order::category))
            .entrySet().stream()
            .map(e -> Map.<Category, Double>entry(e.getKey(), e.getValue()
                .stream().mapToDouble(Order::totalSales).sum())
            ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Set<String> suspiciousCustomers() {
        return orders.stream().collect(Collectors.groupingBy(Order::customerName))
            .entrySet().stream()
    }

    @Override
    public Map<Category, PaymentMethod> mostUsedPaymentMethodForCategory() {
        return Map.of();
    }

    @Override
    public String locationWithMostOrders() {
        return "";
    }

    @Override
    public Map<Category, Map<Status, Long>> groupByCategoryAndStatus() {
        return Map.of();
    }
}
