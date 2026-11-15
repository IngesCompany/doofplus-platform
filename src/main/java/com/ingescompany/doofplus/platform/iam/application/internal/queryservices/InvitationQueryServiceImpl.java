package com.ingescompany.doofplus.platform.iam.application.internal.queryservices;

import com.ingescompany.doofplus.platform.iam.application.queryservices.InvitationQueryService;
import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.Invitation;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetAllInvitationsQuery;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetInvitationByIdQuery;
import com.ingescompany.doofplus.platform.iam.domain.repositories.InvitationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Reads the invitations.
 */
@Service
public class InvitationQueryServiceImpl implements InvitationQueryService {

    private final InvitationRepository invitationRepository;

    public InvitationQueryServiceImpl(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @Override
    public List<Invitation> handle(GetAllInvitationsQuery query) {
        return query.organizationId() == null
                ? invitationRepository.findAll()
                : invitationRepository.findAllByOrganizationId(query.organizationId());
    }

    @Override
    public Optional<Invitation> handle(GetInvitationByIdQuery query) {
        return invitationRepository.findById(query.invitationId());
    }
}
