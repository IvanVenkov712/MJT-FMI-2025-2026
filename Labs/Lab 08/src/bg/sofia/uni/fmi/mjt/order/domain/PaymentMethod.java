package bg.sofia.uni.fmi.mjt.order.domain;

public enum PaymentMethod implements Comparable<PaymentMethod> {
    AMAZON_PAY,
    CREDIT_CARD,
    DEBIT_CARD,
    GIFT_CARD,
    PAYPAL
}
