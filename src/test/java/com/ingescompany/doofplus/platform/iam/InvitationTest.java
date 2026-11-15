package com.ingescompany.doofplus.platform.iam;

import com.ingescompany.doofplus.platform.iam.application.internal.commandservices.InvitationCommandServiceImpl;
import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.Invitation;
import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.User;
import com.ingescompany.doofplus.platform.iam.domain.model.commands.SendInvitationCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.InvitationStatus;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.Roles;
import com.ingescompany.doofplus.platform.iam.domain.repositories.InvitationRepository;
import com.ingescompany.doofplus.platform.iam.domain.repositories.UserRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Rules of the invitations of US55 (as InvitationTests of AW).
 */
class InvitationTest {

    private static SendInvitationCommand command(String email, String role) {
        return new SendInvitationCommand(1L, " Ate manufacturing ", role, " Óscar Ramos ", email, null);
    }

    @Test
    void aNewInvitationIsPendingWithItsDataTrimmed() {
        var invitation = new Invitation(command(" Oscar.Ramos@Andinos.com.pe ", "internal-auditor"), Roles.ROLE_INTERNAL_AUDITOR);
        assertThat(invitation.getStatus()).isEqualTo(InvitationStatus.PENDING);
        assertThat(invitation.getEmail()).isEqualTo("oscar.ramos@andinos.com.pe");
        assertThat(invitation.getFullName()).isEqualTo("Óscar Ramos");
        assertThat(invitation.getFacility()).isEqualTo("Ate manufacturing");
        assertThat(invitation.getNote()).isEmpty();
        assertThat(invitation.getSentAt()).isNotNull();
    }

    @Test
    void onlyAPendingInvitationIsAcceptedOrExpired() {
        var invitation = new Invitation(command("a@b.pe", "qa-specialist"), Roles.ROLE_QA_SPECIALIST);
        assertThat(invitation.accept()).isTrue();
        assertThat(invitation.expire()).isFalse();
        assertThat(invitation.getStatus()).isEqualTo(InvitationStatus.ACCEPTED);
    }

    @Test
    void theServiceAnswers400And409() {
        var invitations = mock(InvitationRepository.class);
        var users = mock(UserRepository.class);
        var active = mock(User.class);
        when(active.getStatus()).thenReturn("active");
        when(users.findByEmail("maria.mexico@andinos.com.pe")).thenReturn(Optional.of(active));
        when(users.findByEmail("new@andinos.com.pe")).thenReturn(Optional.empty());
        when(users.findByEmail("pending@andinos.com.pe")).thenReturn(Optional.empty());
        when(invitations.existsPendingByEmail("pending@andinos.com.pe")).thenReturn(true);
        when(invitations.save(any())).thenAnswer(call -> call.getArgument(0));
        var service = new InvitationCommandServiceImpl(invitations, users);

        assertThat(code(service.handle(command("not-an-email", "qa-specialist")))).isEqualTo("VALIDATION_ERROR");
        assertThat(code(service.handle(command("new@andinos.com.pe", "chef")))).isEqualTo("VALIDATION_ERROR");
        assertThat(code(service.handle(command("Maria.Mexico@andinos.com.pe", "qa-specialist")))).isEqualTo("INVITATION_CONFLICT");
        assertThat(code(service.handle(command("pending@andinos.com.pe", "qa-specialist")))).isEqualTo("INVITATION_CONFLICT");
        verify(invitations, never()).save(any());

        var sent = service.handle(command("new@andinos.com.pe", "internal-auditor"));
        assertThat(sent).isInstanceOf(Result.Success.class);
        assertThat(((Result.Success<Invitation, ApplicationError>) sent).value().getRole()).isEqualTo(Roles.ROLE_INTERNAL_AUDITOR);
    }

    private static String code(Result<Invitation, ApplicationError> result) {
        assertThat(result).isInstanceOf(Result.Failure.class);
        return ((Result.Failure<Invitation, ApplicationError>) result).error().code();
    }
}
