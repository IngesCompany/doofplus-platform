package com.ingescompany.doofplus.platform.subscriptions.application.internal.outboundservices.paymentgateway;

/**
 * Result of a charge authorization in the payment gateway.
 *
 * @param outcome       whether the gateway approved or rejected the charge, or did not answer
 * @param transactionId transaction identifier returned by the gateway, {@code null} when not approved
 * @param message       reason given by the gateway
 */
public record PaymentResult(Outcome outcome, String transactionId, String message) {

    /**
     * Outcome of the authorization.
     */
    public enum Outcome {
        APPROVED,
        REJECTED,
        UNAVAILABLE
    }

    public static PaymentResult approved(String transactionId, String message) {
        return new PaymentResult(Outcome.APPROVED, transactionId, message);
    }

    public static PaymentResult rejected(String message) {
        return new PaymentResult(Outcome.REJECTED, null, message);
    }

    public static PaymentResult unavailable(String message) {
        return new PaymentResult(Outcome.UNAVAILABLE, null, message);
    }

    /**
     * @return {@code true} when the gateway approved the charge
     */
    public boolean approved() {
        return outcome == Outcome.APPROVED;
    }

    /**
     * @return {@code true} when the gateway did not answer or is not configured
     */
    public boolean unavailable() {
        return outcome == Outcome.UNAVAILABLE;
    }
}
