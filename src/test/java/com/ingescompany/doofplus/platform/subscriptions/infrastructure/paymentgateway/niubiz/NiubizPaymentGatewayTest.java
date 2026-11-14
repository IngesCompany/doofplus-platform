package com.ingescompany.doofplus.platform.subscriptions.infrastructure.paymentgateway.niubiz;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the Niubiz adapter against a local HTTP server that answers like the Niubiz sandbox.
 */
class NiubizPaymentGatewayTest {
    private static final Money AMOUNT = new Money(new BigDecimal("49.90"), "USD");

    private HttpServer server;
    private final List<String> authorizationRequests = new ArrayList<>();
    private String securityAuthorizationHeader;
    private String authorizationHeader;

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/api.security/v1/security", exchange -> {
            securityAuthorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
            respond(exchange, 201, "security-token");
        });
        server.createContext("/api.authorization/v3/authorization/ecommerce/", exchange -> {
            authorizationHeader = exchange.getRequestHeaders().getFirst("Authorization");
            var body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            authorizationRequests.add(exchange.getRequestURI().getPath() + " " + body);
            if (body.contains("\"DENIED\"")) {
                respond(exchange, 400, "{\"data\":{\"ACTION_CODE\":\"191\",\"ACTION_DESCRIPTION\":\"Operacion denegada\"}}");
            } else {
                respond(exchange, 200, "{\"dataMap\":{\"ACTION_CODE\":\"000\",\"TRANSACTION_ID\":\"993201\",\"ACTION_DESCRIPTION\":\"Aprobado\"}}");
            }
        });
        server.start();
    }

    @AfterEach
    void stopServer() {
        server.stop(0);
    }

    private static void respond(HttpExchange exchange, int status, String body) throws IOException {
        var bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", body.startsWith("{") ? "application/json" : "text/plain");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private NiubizPaymentGateway gateway() {
        return new NiubizPaymentGateway("http://localhost:" + server.getAddress().getPort(), "456879852", "user", "secret");
    }

    @Test
    void authorizeShouldReturnTheTransactionWhenNiubizApproves() {
        var result = gateway().authorize(AMOUNT, "card-token");

        assertThat(result.approved()).isTrue();
        assertThat(result.transactionId()).isEqualTo("993201");
        assertThat(securityAuthorizationHeader).startsWith("Basic ");
        assertThat(authorizationHeader).isEqualTo("security-token");
        assertThat(authorizationRequests).singleElement().asString()
                .startsWith("/api.authorization/v3/authorization/ecommerce/456879852 ")
                .contains("\"tokenId\":\"card-token\"", "\"amount\":49.90", "\"currency\":\"USD\"");
    }

    @Test
    void authorizeShouldReturnTheReasonWhenNiubizDenies() {
        var result = gateway().authorize(AMOUNT, "DENIED");

        assertThat(result.approved()).isFalse();
        assertThat(result.unavailable()).isFalse();
        assertThat(result.transactionId()).isNull();
        assertThat(result.message()).isEqualTo("Operacion denegada");
    }

    @Test
    void authorizeShouldRejectWhenNiubizIsNotAvailable() {
        server.stop(0);

        var result = gateway().authorize(AMOUNT, "card-token");

        assertThat(result.unavailable()).isTrue();
        assertThat(result.message()).isEqualTo("Payment gateway is not available");
    }

    @Test
    void authorizeShouldRejectWithoutCallingNiubizWhenItIsNotConfigured() {
        var result = new NiubizPaymentGateway("http://localhost:" + server.getAddress().getPort(), "", "", "")
                .authorize(AMOUNT, "card-token");

        assertThat(result.unavailable()).isTrue();
        assertThat(result.message()).isEqualTo("Payment gateway is not configured");
        assertThat(authorizationRequests).isEmpty();
    }
}
