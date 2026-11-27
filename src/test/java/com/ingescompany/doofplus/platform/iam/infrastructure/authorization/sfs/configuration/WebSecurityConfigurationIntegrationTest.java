package com.ingescompany.doofplus.platform.iam.infrastructure.authorization.sfs.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Checks which endpoints the RESTful API serves without the JWT of a signed-in user.
 * The requests do not create records: the public ones send invalid bodies or only read.
 */
@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityConfigurationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void privateEndpointWithoutTokenAnswersUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/deviations")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/v1/production-batches")).andExpect(status().isUnauthorized());
        mockMvc.perform(post("/api/v1/production-batches/1/executions")).andExpect(status().isUnauthorized());
    }

    @Test
    void privateEndpointWithInvalidTokenAnswersUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/deviations").header("Authorization", "Bearer not-a-valid-token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void organizationListNeedsTokenButRucLookupIsPublic() throws Exception {
        mockMvc.perform(get("/api/v1/organizations")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/v1/organizations").param("ruc", "20000000001")).andExpect(status().isOk());
    }

    @Test
    void plansArePublic() throws Exception {
        mockMvc.perform(get("/api/v1/plans")).andExpect(status().isOk());
    }

    @Test
    void publicRegistrationAndContactEndpointsDoNotAskForToken() throws Exception {
        for (var path : new String[]{"/api/v1/contact-inquiries", "/api/v1/organizations",
                "/api/v1/facilities", "/api/v1/subscriptions"}) {
            var status = mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON).content("{}"))
                    .andReturn().getResponse().getStatus();
            assertNotEquals(401, status, path);
            assertNotEquals(403, status, path);
        }
    }

    @Test
    void telemetryChecksItsOwnApiKey() throws Exception {
        mockMvc.perform(post("/api/v1/telemetry").contentType(MediaType.APPLICATION_JSON).content("{}")
                        .header("X-Api-Key", "wrong-key"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void corsAllowsTheWebApplicationOrigin() throws Exception {
        mockMvc.perform(options("/api/v1/deviations")
                        .header("Origin", "https://doofplus-webapp.web.app")
                        .header("Access-Control-Request-Method", "GET")
                        .header("Access-Control-Request-Headers", "Authorization"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "https://doofplus-webapp.web.app"));
    }

    @Test
    void corsRejectsAnUnknownOrigin() throws Exception {
        mockMvc.perform(options("/api/v1/deviations")
                        .header("Origin", "https://unknown.example.com")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isForbidden())
                .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
    }
}
