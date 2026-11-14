package com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.organizations.domain.model.commands.SendContactInquiryCommand;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.SendContactInquiryResource;

public final class SendContactInquiryCommandFromResourceAssembler {

    private SendContactInquiryCommandFromResourceAssembler() {
    }

    public static SendContactInquiryCommand toCommandFromResource(SendContactInquiryResource resource) {
        return new SendContactInquiryCommand(resource.fullName(), resource.email(), resource.message());
    }
}
