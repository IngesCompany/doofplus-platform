package com.ingescompany.doofplus.platform.iam.infrastructure.authorization.sfs.configuration;

import com.ingescompany.doofplus.platform.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter;
import com.ingescompany.doofplus.platform.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import com.ingescompany.doofplus.platform.iam.infrastructure.tokens.jwt.BearerTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Web Security Configuration.
 * <p>
 * This class is responsible for configuring the web security.
 * It enables the method security and configures the security filter chain.
 * It includes the authentication manager, the authentication provider, the password encoder and the authentication entry point.
 * </p>
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private final UserDetailsService userDetailsService;

    private final BearerTokenService tokenService;

    private final BCryptHashingService hashingService;

    private final AuthenticationEntryPoint unauthorizedRequestHandler;

    private final List<String> allowedOrigins;

    /**
     * This method creates the Bearer Authorization Request Filter.
     * @return The Bearer Authorization Request Filter
     * @see BearerAuthorizationRequestFilter
     */
    @Bean
    public BearerAuthorizationRequestFilter authorizationRequestFilter() {
        return new BearerAuthorizationRequestFilter(tokenService, userDetailsService);
    }

    /**
     * This method creates the authentication manager.
     * @param authenticationConfiguration The {@link AuthenticationConfiguration} object with the authentication configuration
     * @return The {@link AuthenticationManager} instance from the authentication configuration
     *
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * This method creates the authentication provider.
     * @return The {@link DaoAuthenticationProvider} authentication provider with the user details service and the password encoder
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(hashingService);
        return authenticationProvider;
    }

    /**
     * This method creates the password encoder.
     * @return The {@link PasswordEncoder} instance with the hashing service
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return hashingService;
    }

    /**
     * Matches the RUC lookup of the registration form ({@code GET /api/v1/organizations?ruc=}), so that a visitor can
     * check whether a laboratory is already registered without listing every organization.
     *
     * @return the request matcher of the RUC lookup
     */
    private static RequestMatcher isRucLookup() {
        return request -> HttpMethod.GET.matches(request.getMethod())
                && "/api/v1/organizations".equals(request.getRequestURI().substring(request.getContextPath().length()))
                && request.getParameter("ruc") != null;
    }

    /**
     * This method creates the security filter chain.
     * It also configures the http security.
     *
     * @param http The {@link HttpSecurity} object to configure with the security filter chain
     * @return The {@link SecurityFilterChain} instance with the application http security configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(configurer -> configurer.configurationSource(_ -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(allowedOrigins);
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        }));
        http.csrf(csrfConfigurer -> csrfConfigurer.disable())
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedRequestHandler))
                .sessionManagement( customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users", "/api/v1/tokens").permitAll()
                        .requestMatchers("/api/v1/authentication/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/error").permitAll()
                        // Only the administrators list every user of the platform.
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMINISTRATOR")
                        .requestMatchers("/api/v1/users/**", "/api/v1/roles/**").authenticated()
                        .requestMatchers("/api/v1/invitations", "/api/v1/invitations/**").hasRole("ADMINISTRATOR")
                        // TS12, scenario 3: only the administrators read the contact inquiries.
                        .requestMatchers(HttpMethod.GET, "/api/v1/contact-inquiries", "/api/v1/contact-inquiries/**").hasRole("ADMINISTRATOR")
                        // Public zone of the Web Application: contact form and registration of a new laboratory,
                        // which happens before its first administrator has an account.
                        .requestMatchers(HttpMethod.POST, "/api/v1/contact-inquiries").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/plans", "/api/v1/plans/**").permitAll()
                        .requestMatchers(isRucLookup()).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/organizations", "/api/v1/facilities", "/api/v1/subscriptions").permitAll()
                        // ThingsBoard has no user: the telemetry endpoint checks its own API key.
                        .requestMatchers(HttpMethod.POST, "/api/v1/telemetry").permitAll()
                        // Every other endpoint needs the JWT of a signed-in user.
                        .anyRequest().authenticated());
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authorizationRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    /**
     * This is the constructor of the class.
     * @param userDetailsService The user details service
     * @param tokenService The token service
     * @param hashingService The hashing service
     * @param authenticationEntryPoint The authentication entry point
     */
    public WebSecurityConfiguration(@Qualifier("defaultUserDetailsService") UserDetailsService userDetailsService, BearerTokenService tokenService, BCryptHashingService hashingService, AuthenticationEntryPoint authenticationEntryPoint,
                                    @Value("${cors.allowed-origins}") List<String> allowedOrigins) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.hashingService = hashingService;
        this.unauthorizedRequestHandler = authenticationEntryPoint;
        this.allowedOrigins = allowedOrigins;
    }
}
