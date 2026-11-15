package com.ingescompany.doofplus.platform.iam.application.internal.commandservices;

import com.ingescompany.doofplus.platform.iam.application.commandservices.InvitationCommandService;
import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.Invitation;
import com.ingescompany.doofplus.platform.iam.domain.model.commands.SendInvitationCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.Roles;
import com.ingescompany.doofplus.platform.iam.domain.repositories.InvitationRepository;
import com.ingescompany.doofplus.platform.iam.domain.repositories.UserRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Sends the invitations of US55.
 */
@Service
public class InvitationCommandServiceImpl implements InvitationCommandService {

    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;

    public InvitationCommandServiceImpl(InvitationRepository invitationRepository, UserRepository userRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Result<Invitation, ApplicationError> handle(SendInvitationCommand command) {
        if (isBlank(command.fullName()) || isBlank(command.facility()) || isBlank(command.email())
                || !EMAIL.matcher(command.email().trim()).matches() || command.organizationId() == null) {
            return Result.failure(ApplicationError.validationError("invitation",
                    "The organization, the full name, a valid email and the facility are required."));
        }
        Roles role;
        try {
            role = Roles.fromKey(command.role());
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("role", "The role is not valid."));
        }
        // US55: the account of an invited member exists in the invited status until the invitation is accepted.
        var email = command.email().trim().toLowerCase();
        var user = userRepository.findByEmail(email);
        if ((user.isPresent() && !"invited".equals(user.get().getStatus()))
                || invitationRepository.existsPendingByEmail(email)) {
            return Result.failure(ApplicationError.conflict("Invitation",
                    "A user or a pending invitation already uses the email."));
        }
        return Result.success(invitationRepository.save(new Invitation(command, role)));
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
