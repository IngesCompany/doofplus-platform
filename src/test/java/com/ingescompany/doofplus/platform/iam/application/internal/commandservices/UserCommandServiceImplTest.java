package com.ingescompany.doofplus.platform.iam.application.internal.commandservices;

import com.ingescompany.doofplus.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.ingescompany.doofplus.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.Invitation;
import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.User;
import com.ingescompany.doofplus.platform.iam.domain.model.commands.SendInvitationCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.commands.SignInCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.commands.SignUpCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.InvitationStatus;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.Roles;
import com.ingescompany.doofplus.platform.iam.domain.repositories.InvitationRepository;
import com.ingescompany.doofplus.platform.iam.domain.repositories.RoleRepository;
import com.ingescompany.doofplus.platform.iam.domain.repositories.UserRepository;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserCommandServiceImplTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private InvitationRepository invitationRepository;
    private UserCommandServiceImpl service;

    /**
     * Fake hashing: the hash of a value is "hash:" + value.
     */
    private static final HashingService HASHING = new HashingService() {
        @Override
        public String encode(CharSequence rawPassword) {
            return "hash:" + rawPassword;
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return ("hash:" + rawPassword).equals(encodedPassword);
        }
    };

    private static User user(String twoFactorHash) {
        return User.reconstitute(7L, "elena.paz@andinos.com.pe", "hash:Secret123", "Elena Paz", "qa", "Lima",
                "active", "none", 1L, "Laboratorios Andinos", "Ate plant", twoFactorHash,
                Set.of(new Role(2L, Roles.ROLE_QA_SPECIALIST)));
    }

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        invitationRepository = mock(InvitationRepository.class);
        when(invitationRepository.findPendingByEmail(any())).thenReturn(Optional.empty());
        when(roleRepository.findByName(any())).thenAnswer(call -> Optional.of(new Role(call.<Roles>getArgument(0))));
        TokenService tokenService = mock(TokenService.class);
        when(tokenService.generateToken(any())).thenReturn("jwt");
        service = new UserCommandServiceImpl(userRepository, HASHING, tokenService, roleRepository, invitationRepository);
    }

    private static String reason(Result<?, com.ingescompany.doofplus.platform.shared.application.result.ApplicationError> result) {
        assertThat(result).isInstanceOf(Result.Failure.class);
        var error = ((Result.Failure<?, com.ingescompany.doofplus.platform.shared.application.result.ApplicationError>) result).error();
        assertThat(error.code()).isEqualTo("UNAUTHORIZED");
        return error.details();
    }

    @Test
    void aWrongPasswordOrAnUnknownEmailGiveTheSameReason() {
        when(userRepository.findByEmail("elena.paz@andinos.com.pe")).thenReturn(Optional.of(user(null)));
        assertThat(reason(service.handle(new SignInCommand("elena.paz@andinos.com.pe", "wrong", null))))
                .isEqualTo(UserCommandServiceImpl.INVALID_CREDENTIALS);
        assertThat(reason(service.handle(new SignInCommand("nobody@andinos.com.pe", "Secret123", null))))
                .isEqualTo(UserCommandServiceImpl.INVALID_CREDENTIALS);
    }

    @Test
    void aUserWithTwoFactorNeedsTheRightCode() {
        when(userRepository.findByEmail("elena.paz@andinos.com.pe")).thenReturn(Optional.of(user("hash:123456")));
        assertThat(reason(service.handle(new SignInCommand(" Elena.Paz@andinos.com.pe ", "Secret123", null))))
                .isEqualTo(UserCommandServiceImpl.TWO_FACTOR_REQUIRED);
        assertThat(reason(service.handle(new SignInCommand("elena.paz@andinos.com.pe", "Secret123", "000000"))))
                .isEqualTo(UserCommandServiceImpl.INVALID_TWO_FACTOR_CODE);
        var result = service.handle(new SignInCommand("elena.paz@andinos.com.pe", "Secret123", "123456"));
        assertThat(result).isInstanceOf(Result.Success.class);
    }

    @Test
    void aUserWithoutTwoFactorSignsInWithThePassword() {
        when(userRepository.findByEmail("elena.paz@andinos.com.pe")).thenReturn(Optional.of(user(null)));
        var result = service.handle(new SignInCommand("elena.paz@andinos.com.pe", "Secret123", null));
        assertThat(result).isInstanceOf(Result.Success.class);
    }

    @Test
    void signUpHashesThePasswordAndTheCodeAndRejectsARepeatedEmail() {
        when(userRepository.findByEmail("elena.paz@andinos.com.pe")).thenReturn(Optional.of(user("hash:123456")));
        var command = new SignUpCommand("Elena.Paz@andinos.com.pe", "Secret123", "Elena Paz", List.of(), "qa",
                "Lima", null, null, 1L, "Laboratorios Andinos", "Ate plant", "123456");

        assertThat(service.handle(command)).isInstanceOf(Result.Success.class);
        verify(userRepository).save(argThat(saved -> saved.getPassword().equals("hash:Secret123")
                && saved.getTwoFactorCode().equals("hash:123456")
                && saved.getEmail().equals("elena.paz@andinos.com.pe")
                && saved.getRoleKey().equals("administrator")));

        when(userRepository.existsByEmail("elena.paz@andinos.com.pe")).thenReturn(true);
        var repeated = service.handle(command);
        assertThat(repeated).isInstanceOf(Result.Failure.class);
    }

    @Test
    void signUpValidatesItsData() {
        assertThatThrownBy(() -> new SignUpCommand("elena", "Secret123", "Elena Paz", List.of(), "qa",
                "Lima", null, null, 1L, "Laboratorios Andinos", "Ate plant", null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new SignUpCommand("elena.paz@andinos.com.pe", "short", "Elena Paz", List.of(), "qa",
                "Lima", null, null, 1L, "Laboratorios Andinos", "Ate plant", null))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Roles.fromKey("chef")).isInstanceOf(IllegalArgumentException.class);
        assertThat(Roles.fromKey("production-supervisor")).isEqualTo(Roles.ROLE_PRODUCTION_SUPERVISOR);
    }

    private static SignUpCommand signUp(String email, String role, String environment, Long organizationId,
                                        String privilege) {
        return new SignUpCommand(email, "Secret123", "Elena Paz", List.of(new Role(Roles.fromKey(role))),
                environment, "Ate plant", null, privilege, organizationId, "Laboratorios Andinos", "Lima", null);
    }

    private static String errorCode(Result<?, com.ingescompany.doofplus.platform.shared.application.result.ApplicationError> result) {
        assertThat(result).isInstanceOf(Result.Failure.class);
        return ((Result.Failure<?, com.ingescompany.doofplus.platform.shared.application.result.ApplicationError>) result).error().code();
    }

    @Test
    void theFirstUserOfAnOrganizationIsItsAdministratorWhateverRoleItAsks() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user(null)));
        var result = service.handle(signUp("elena.paz@andinos.com.pe", "qa-specialist", "qa", 1L, null));

        assertThat(result).isInstanceOf(Result.Success.class);
        verify(userRepository).save(argThat(saved -> saved.getRoleKey().equals("administrator")
                && saved.getPrivilege().equals("administrator")
                && saved.getEnvironment().equals("administration")
                && saved.getOrganizationId().equals(1L)));
    }

    @Test
    void anotherUserWithoutAnInvitationCannotJoinTheOrganization() {
        when(userRepository.existsByOrganizationId(1L)).thenReturn(true);

        var result = service.handle(signUp("intruder@mail.com", "administrator", "administration", 1L, "administrator"));

        assertThat(errorCode(result)).isEqualTo("FORBIDDEN");
        verify(userRepository, never()).save(any());
    }

    @Test
    void anInvitedUserTakesTheRoleAndTheOrganizationOfTheInvitation() {
        when(userRepository.existsByOrganizationId(any())).thenReturn(true);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user(null)));
        var invitation = new Invitation(new SendInvitationCommand(1L, "Callao plant", "production-supervisor",
                "Luis Rojas", "luis.rojas@andinos.com.pe", null), Roles.ROLE_PRODUCTION_SUPERVISOR);
        when(invitationRepository.findPendingByEmail("luis.rojas@andinos.com.pe")).thenReturn(Optional.of(invitation));

        var result = service.handle(signUp("luis.rojas@andinos.com.pe", "administrator", "production", 2L, "administrator"));

        assertThat(result).isInstanceOf(Result.Success.class);
        verify(userRepository).save(argThat(saved -> saved.getRoleKey().equals("production-supervisor")
                && saved.getOrganizationId().equals(1L)
                && saved.getFacility().equals("Callao plant")
                && saved.getPrivilege().equals("none")));
        assertThat(invitation.getStatus()).isEqualTo(InvitationStatus.ACCEPTED);
        verify(invitationRepository).save(invitation);
    }
}
