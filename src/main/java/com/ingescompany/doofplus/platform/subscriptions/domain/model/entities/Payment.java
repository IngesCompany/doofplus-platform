package com.ingescompany.doofplus.platform.subscriptions.domain.model.entities;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PaymentStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Payment of one billing period of a subscription. It belongs to the {@code Subscription}
 * aggregate, starts as pending and is approved or rejected by the payment gateway (Niubiz).
 */
@Getter
public class Payment {
    private static final String INVOICE_NUMBER_NOT_BLANK_MESSAGE_KEY = "payment.error.invoiceNumber.notBlank";
    private static final String AMOUNT_INVALID_MESSAGE_KEY = "money.error.amount.invalid";
    private static final String TRANSACTION_ID_NOT_BLANK_MESSAGE_KEY = "payment.error.transactionId.notBlank";
    private static final String NOT_PENDING_MESSAGE = "Only a pending payment can be approved or rejected";

    private Long id;
    private String invoiceNumber;
    private Money amount;
    private String gatewayTransactionId;
    private PaymentStatus status;
    private LocalDateTime processedAt;

    private Payment() {}

    /**
     * Creates a pending payment.
     *
     * @param invoiceNumber invoice number, for example {@code INV-2026-0801}
     * @param amount        amount charged for the period
     */
    public Payment(String invoiceNumber, Money amount) {
        if (invoiceNumber == null || invoiceNumber.isBlank()) {
            throw new IllegalArgumentException(INVOICE_NUMBER_NOT_BLANK_MESSAGE_KEY);
        }
        if (amount == null) throw new IllegalArgumentException(AMOUNT_INVALID_MESSAGE_KEY);
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    /**
     * Rebuilds a stored payment.
     *
     * @return the payment with its persistent identifier
     */
    public static Payment reconstitute(Long id, String invoiceNumber, Money amount, String gatewayTransactionId,
                                       PaymentStatus status, LocalDateTime processedAt) {
        var payment = new Payment();
        payment.id = id;
        payment.invoiceNumber = invoiceNumber;
        payment.amount = amount;
        payment.gatewayTransactionId = gatewayTransactionId;
        payment.status = status;
        payment.processedAt = processedAt;
        return payment;
    }

    /**
     * Marks the payment as approved by the gateway.
     *
     * @param transactionId transaction identifier returned by the gateway
     * @throws IllegalStateException when the payment is not pending
     */
    public void approve(String transactionId) {
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException(TRANSACTION_ID_NOT_BLANK_MESSAGE_KEY);
        }
        ensurePending();
        this.gatewayTransactionId = transactionId;
        this.status = PaymentStatus.APPROVED;
        this.processedAt = LocalDateTime.now();
    }

    /**
     * Marks the payment as rejected by the gateway. The reason is reported by the gateway
     * and is not stored, as in the {@code payments} table of the report.
     *
     * @param reason reason given by the gateway
     * @throws IllegalStateException when the payment is not pending
     */
    public void reject(String reason) {
        ensurePending();
        this.status = PaymentStatus.REJECTED;
        this.processedAt = LocalDateTime.now();
    }

    /**
     * @return {@code true} when the gateway approved the payment
     */
    public boolean isApproved() {
        return status == PaymentStatus.APPROVED;
    }

    private void ensurePending() {
        if (status != PaymentStatus.PENDING) throw new IllegalStateException(NOT_PENDING_MESSAGE);
    }
}
