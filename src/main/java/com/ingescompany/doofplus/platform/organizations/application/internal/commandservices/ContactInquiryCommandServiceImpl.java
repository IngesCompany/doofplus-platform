package com.ingescompany.doofplus.platform.organizations.application.internal.commandservices;

import com.ingescompany.doofplus.platform.organizations.application.commandservices.ContactInquiryCommandService;
import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.ContactInquiry;
import com.ingescompany.doofplus.platform.organizations.domain.model.commands.SendContactInquiryCommand;
import com.ingescompany.doofplus.platform.organizations.domain.repositories.ContactInquiryRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ContactInquiryCommandServiceImpl implements ContactInquiryCommandService {
    private final ContactInquiryRepository contactInquiryRepository;

    public ContactInquiryCommandServiceImpl(ContactInquiryRepository contactInquiryRepository) {
        this.contactInquiryRepository = contactInquiryRepository;
    }

    @Override
    public Result<ContactInquiry, ApplicationError> handle(SendContactInquiryCommand command) {
        try {
            var contactInquiry = new ContactInquiry(command);
            return Result.success(contactInquiryRepository.save(contactInquiry));
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("contact-inquiry", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("send-contact-inquiry", e.getMessage()));
        }
    }
}
