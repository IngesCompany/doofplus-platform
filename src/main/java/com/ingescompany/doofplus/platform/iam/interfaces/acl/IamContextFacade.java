package com.ingescompany.doofplus.platform.iam.interfaces.acl;

import com.ingescompany.doofplus.platform.iam.application.queryservices.UserQueryService;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetUserByEmailQuery;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetUserByIdQuery;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

/**
 * Anti-corruption layer that lets other bounded contexts read users without knowing the IAM model.
 */
@Service
public class IamContextFacade {

    private final UserQueryService userQueryService;

    public IamContextFacade(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    /**
     * @param email the email address of the user
     * @return the id of the user, or 0 when it does not exist
     */
    public Long fetchUserIdByEmail(String email) {
        return userQueryService.handle(new GetUserByEmailQuery(email)).map(user -> user.getId()).orElse(0L);
    }

    /**
     * @param userId the id of the user
     * @return the email address of the user, or empty when it does not exist
     */
    public String fetchEmailByUserId(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId)).map(user -> user.getEmail()).orElse(Strings.EMPTY);
    }

    /**
     * @param userId the id of the user
     * @return the id of the organization of the user, or 0 when it does not exist
     */
    public Long fetchOrganizationIdByUserId(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId)).map(user -> user.getOrganizationId()).orElse(0L);
    }
}
