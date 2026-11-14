package com.ingescompany.doofplus.platform.subscriptions.domain.model.entities;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentTest {

    private static Payment pendingPayment() {
        return new Payment("INV-1-1", new Money(new BigDecimal("49.90"), "USD"));
    }

    @Test
    void newPaymentShouldBePending() {
        var payment = pendingPayment();

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.PENDING);
        assertThat(payment.isApproved()).isFalse();
        assertThat(payment.getProcessedAt()).isNull();
    }

    @Test
    void newPaymentShouldRequireAnInvoiceNumberAndAnAmount() {
        var amount = new Money(BigDecimal.ONE, "USD");

        assertThatThrownBy(() -> new Payment(" ", amount)).hasMessage("payment.error.invoiceNumber.notBlank");
        assertThatThrownBy(() -> new Payment("INV-1-1", null)).hasMessage("money.error.amount.invalid");
    }

    @Test
    void approveShouldKeepTheTransactionId() {
        var payment = pendingPayment();

        payment.approve("993201");

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.APPROVED);
        assertThat(payment.isApproved()).isTrue();
        assertThat(payment.getGatewayTransactionId()).isEqualTo("993201");
        assertThat(payment.getProcessedAt()).isNotNull();
    }

    @Test
    void approveShouldRequireATransactionId() {
        assertThatThrownBy(() -> pendingPayment().approve("")).hasMessage("payment.error.transactionId.notBlank");
    }

    @Test
    void rejectShouldMarkThePaymentAsRejected() {
        var payment = pendingPayment();

        payment.reject("Operacion denegada");

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.REJECTED);
        assertThat(payment.getGatewayTransactionId()).isNull();
        assertThat(payment.getProcessedAt()).isNotNull();
    }

    @Test
    void aProcessedPaymentShouldNotChangeAgain() {
        var payment = pendingPayment();
        payment.approve("993201");

        assertThatThrownBy(() -> payment.reject("late")).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> payment.approve("993202")).isInstanceOf(IllegalStateException.class);
    }
}
