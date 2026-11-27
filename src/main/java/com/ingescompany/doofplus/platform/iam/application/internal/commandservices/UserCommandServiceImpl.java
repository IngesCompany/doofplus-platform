package com.ingescompany.doofplus.platform.iam.application.internal.commandservices;

import com.ingescompany.doofplus.platform.iam.application.commandservices.UserCommandService;
import com.ingescompany.doofplus.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.ingescompany.doofplus.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.User;
import com.ingescompany.doofplus.platform.iam.domain.model.commands.SignInCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.commands.SignUpCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.Roles;
import com.ingescompany.doofplus.platform.iam.domain.repositories.InvitationRepository;
import com.ingescompany.doofplus.platform.iam.domain.repositories.RoleRepository;
import com.ingescompany.doofplus.platform.iam.domain.repositories.UserRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Command service of the users: sign-in with two-factor authentication and sign-up.
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    /**
     * Reason (details) of the 401 when the email or the password is wrong.
     */
    public static final String INVALID_CREDENTIALS = "invalid-credentials";

    /**
     * Reason (details) of the 401 when the password is right but the two-factor code is missing.
     */
    public static final String TWO_FACTOR_REQUIRED = "two-factor-required";

    /** Reason of a sign-up to an organization that already has users, without an invitation. */
    static final String INVITATION_REQUIRED = "iam.error.signUp.invitationRequired";

    private static final String ADMINISTRATION_ENVIRONMENT = "administration";
    private static final String ADMINISTRATOR_PRIVILEGE = "administrator";
    private static final String NO_PRIVILEGE = "none";

    private final InvitationRepository invitationRepository;

    /**
     * Reason (details) of the 401 when the two-factor code is wrong.
     */
    public static final String INVALID_TWO_FACTOR_CODE = "invalid-two-factor-code";

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(
            UserRepository userRepository,
            HashingService hashingService,
            TokenService tokenService,
            RoleRepository roleRepository,
            InvitationRepository invitationRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.invitationRepository = invitationRepository;
    }

    /**
     * Signs in a user. The token is issued only after the password and, when the user enrolled it,
     * the two-factor code are verified. Every failure answers 401 with its reason, without telling
     * whether the email exists.
     *
     * @param command the email, the password and the two-factor code
     * @return the user and the JWT, or the reason of the 401
     */
    @Override
    public Result<ImmutablePair<User, String>, ApplicationError> handle(SignInCommand command) {
        var email = command.email() == null ? "" : command.email().trim().toLowerCase();
        var user = userRepository.findByEmail(email);
        if (user.isEmpty() || command.password() == null
                || !hashingService.matches(command.password(), user.get().getPassword())) {
            return Result.failure(ApplicationError.unauthorized("sign-in", INVALID_CREDENTIALS));
        }
        if (user.get().hasTwoFactor()) {
            if (command.twoFactorCode() == null || command.twoFactorCode().isBlank()) {
                return Result.failure(ApplicationError.unauthorized("sign-in", TWO_FACTOR_REQUIRED));
            }
            if (!hashingService.matches(command.twoFactorCode().trim(), user.get().getTwoFactorCode())) {
                return Result.failure(ApplicationError.unauthorized("sign-in", INVALID_TWO_FACTOR_CODE));
            }
        }
        var token = tokenService.generateToken(user.get().getEmail());
        return Result.success(ImmutablePair.of(user.get(), token));
    }

    /**
     * Registers a user with the password and the two-factor code hashed with BCrypt.
     *
     * @param command the data of the user
     * @return the new user, or 409 when the email is already used
     */
    @Override
    public Result<User, ApplicationError> handle(SignUpCommand command) {
        var email = command.email().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            return Result.failure(ApplicationError.conflict("User", "A user with the same email already exists"));
        }
        // The role, the privilege and the organization never come from the client: the first user of an
        // organization is its administrator, an invited user takes the role and the organization of the pending
        // invitation, and anybody else is rejected (403).
        var invitation = invitationRepository.findPendingByEmail(email);
        Roles roleName;
        if (invitation.isPresent()) {
            roleName = invitation.get().getRole();
            command = withAccess(command, command.environment(), invitation.get().getOrganizationId(),
                    invitation.get().getFacility(),
                    roleName == Roles.ROLE_ADMINISTRATOR ? ADMINISTRATOR_PRIVILEGE : NO_PRIVILEGE);
        } else if (userRepository.existsByOrganizationId(command.organizationId())) {
            return Result.failure(ApplicationError.forbidden("sign-up", INVITATION_REQUIRED));
        } else {
            roleName = Roles.ROLE_ADMINISTRATOR;
            command = withAccess(command, ADMINISTRATION_ENVIRONMENT, command.organizationId(), command.facility(),
                    ADMINISTRATOR_PRIVILEGE);
        }
        var role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Role", roleName.name()));
        }
        var resolvedRoles = List.of(role.get());
        var hashedTwoFactorCode = command.twoFactorCode() == null || command.twoFactorCode().isBlank()
                ? null
                : hashingService.encode(command.twoFactorCode().trim());
        var user = new User(command, hashingService.encode(command.password()), hashedTwoFactorCode, resolvedRoles);
        userRepository.save(user);
        invitation.ifPresent(pending -> {
            pending.accept();
            invitationRepository.save(pending);
        });
        return userRepository.findByEmail(email)
                .<Result<User, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.unexpected("sign-up", "Created user could not be reloaded")));
    }

    /**
     * Copies a sign-up with the environment, the organization, the facility and the privilege that the server assigns.
     */
    private static SignUpCommand withAccess(SignUpCommand command, String environment, Long organizationId,
                                            String facility, String privilege) {
        return new SignUpCommand(command.email(), command.password(), command.fullName(), List.of(), environment,
                facility, command.status(), privilege, organizationId, command.organizationName(), command.plant(),
                command.twoFactorCode());
    }
}
