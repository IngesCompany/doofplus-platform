package com.ingescompany.doofplus.platform.subscriptions.infrastructure.paymentgateway.niubiz;

import com.ingescompany.doofplus.platform.subscriptions.application.internal.outboundservices.paymentgateway.PaymentGateway;
import com.ingescompany.doofplus.platform.subscriptions.application.internal.outboundservices.paymentgateway.PaymentResult;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * {@link PaymentGateway} implemented with the Niubiz e-commerce REST API.
 * It creates a security token and then authorizes the charge with the card token.
 * The URL, merchant and credentials come from the {@code niubiz.*} properties (sandbox by default).
 */
@Slf4j
@Component
public class NiubizPaymentGateway implements PaymentGateway {
    private static final String APPROVED_ACTION_CODE = "000";
    private static final String DEFAULT_REJECTION_MESSAGE = "Charge rejected by the payment gateway";
    private static final ParameterizedTypeReference<Map<String, Object>> JSON_OBJECT = new ParameterizedTypeReference<>() {};
    private static final String SECURITY_PATH = "/api.security/v1/security";
    private static final String AUTHORIZATION_PATH = "/api.authorization/v3/authorization/ecommerce/{merchantId}";

    private final RestClient restClient;
    private final String merchantId;
    private final String username;
    private final String password;

    public NiubizPaymentGateway(@Value("${niubiz.base-url}") String baseUrl,
                                @Value("${niubiz.merchant-id}") String merchantId,
                                @Value("${niubiz.username}") String username,
                                @Value("${niubiz.password}") String password) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
        this.merchantId = merchantId;
        this.username = username;
        this.password = password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentResult authorize(Money amount, String cardToken) {
        if (merchantId.isBlank() || username.isBlank() || password.isBlank()) {
            log.warn("Niubiz is not configured: set NIUBIZ_MERCHANT_ID, NIUBIZ_USERNAME and NIUBIZ_PASSWORD");
            return PaymentResult.unavailable("Payment gateway is not configured");
        }
        try {
            var securityToken = restClient.get()
                    .uri(SECURITY_PATH)
                    .headers(headers -> headers.setBasicAuth(username, password))
                    .retrieve()
                    .body(String.class);
            var response = restClient.post()
                    .uri(AUTHORIZATION_PATH, merchantId)
                    .header(HttpHeaders.AUTHORIZATION, securityToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(authorizationRequest(amount, cardToken))
                    .retrieve()
                    .body(JSON_OBJECT);
            return toResult(response);
        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode().is5xxServerError()) {
                log.error("Niubiz failed: status={}", exception.getStatusCode().value());
                return PaymentResult.unavailable("Payment gateway is not available");
            }
            var reason = actionDescription(exception.getResponseBodyAs(JSON_OBJECT));
            log.warn("Niubiz rejected the charge: status={}, reason={}", exception.getStatusCode().value(), reason);
            return PaymentResult.rejected(reason);
        } catch (RestClientException exception) {
            log.error("Niubiz is not available: {}", exception.getMessage());
            return PaymentResult.unavailable("Payment gateway is not available");
        }
    }

    private Map<String, Object> authorizationRequest(Money amount, String cardToken) {
        return Map.of(
                "channel", "web",
                "captureType", "manual",
                "countable", true,
                "order", Map.of(
                        "tokenId", cardToken,
                        "purchaseNumber", purchaseNumber(),
                        "amount", amount.amount(),
                        "currency", amount.currency()));
    }

    private PaymentResult toResult(Map<String, Object> response) {
        var data = field(response, "dataMap");
        if (!APPROVED_ACTION_CODE.equals(text(data, "ACTION_CODE"))) {
            return PaymentResult.rejected(actionDescription(response));
        }
        return PaymentResult.approved(text(data, "TRANSACTION_ID"), text(data, "ACTION_DESCRIPTION"));
    }

    private static String actionDescription(Map<String, Object> body) {
        var description = text(field(body, "data"), "ACTION_DESCRIPTION");
        if (description.isBlank()) description = text(field(body, "dataMap"), "ACTION_DESCRIPTION");
        return description.isBlank() ? DEFAULT_REJECTION_MESSAGE : description;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> field(Map<String, Object> body, String name) {
        return body != null && body.get(name) instanceof Map<?, ?> map ? (Map<String, Object>) map : Map.of();
    }

    private static String text(Map<String, Object> body, String name) {
        var value = body.get(name);
        return value == null ? "" : value.toString();
    }

    /**
     * @return a numeric purchase number of up to 12 digits, as Niubiz requires
     */
    private static long purchaseNumber() {
        return ThreadLocalRandom.current().nextLong(100_000_000_000L, 1_000_000_000_000L);
    }
}
