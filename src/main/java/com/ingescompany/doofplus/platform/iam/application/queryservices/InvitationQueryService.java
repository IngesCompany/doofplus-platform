package com.ingescompany.doofplus.platform.iam.application.queryservices;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.Invitation;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetAllInvitationsQuery;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetInvitationByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Queries of the invitations.
 */
public interface InvitationQueryService {
    List<Invitation> handle(GetAllInvitationsQuery query);

    Optional<Invitation> handle(GetInvitationByIdQuery query);
}
