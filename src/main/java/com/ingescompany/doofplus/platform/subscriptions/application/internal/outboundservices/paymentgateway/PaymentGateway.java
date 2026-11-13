package com.ingescompany.doofplus.platform.subscriptions.application.internal.outboundservices.paymentgateway;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;

/**
 * Outbound port to the payment gateway that charges the subscriptions (Niubiz).
 */
public interface PaymentGateway {

    /**
     * Asks the gateway to authorize a charge.
     *
     * @param amount    amount to charge
     * @param cardToken card token created by the gateway form in the Web App
     * @return the result of the authorization
     */
    PaymentResult authorize(Money amount, String cardToken);
}
