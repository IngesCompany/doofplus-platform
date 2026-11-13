package com.ingescompany.doofplus.platform.organizations.application.commandservices;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.ContactInquiry;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.SendContactInquiryCommand;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

public interface ContactInquiryCommandService {
    Result<ContactInquiry, ApplicationError> handle(SendContactInquiryCommand command);
}
