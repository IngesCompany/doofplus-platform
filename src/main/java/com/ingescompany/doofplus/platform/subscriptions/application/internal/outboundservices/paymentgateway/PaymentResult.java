package com.ingescompany.doofplus.platform.subscriptions.application.internal.outboundservices.paymentgateway;

/**
 * Result of a charge authorization in the payment gateway.
 *
 * @param approved      {@code true} when the gateway approved the charge
 * @param transactionId transaction identifier returned by the gateway, {@code null} when rejected
 * @param message       reason given by the gateway
 */
public record PaymentResult(boolean approved, String transactionId, String message) {

    public static PaymentResult approved(String transactionId, String message) {
        return new PaymentResult(true, transactionId, message);
    }

    public static PaymentResult rejected(String message) {
        return new PaymentResult(false, null, message);
    }
}
