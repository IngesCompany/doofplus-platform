package com.ingescompany.doofplus.platform.organizations.domain.model.aggregates;

import com.ingescompany.doofplus.platform.organizations.domain.model.commands.SendContactInquiryCommand;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.InquiryStatus;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * ContactInquiry aggregate root: a question sent by a visitor from the public contact us page.
 */
@Getter
public class ContactInquiry extends AbstractDomainAggregateRoot<ContactInquiry> {
    private Long id;
    private String fullName;
    private String email;
    private String message;
    private InquiryStatus status;
    private LocalDateTime receivedAt;

    public ContactInquiry(SendContactInquiryCommand command) {
        this.fullName = command.fullName();
        this.email = command.email();
        this.message = command.message();
        this.status = InquiryStatus.RECEIVED;
        this.receivedAt = LocalDateTime.now();
    }

    private ContactInquiry() {
    }

    public static ContactInquiry reconstitute(Long id, String fullName, String email, String message,
                                              InquiryStatus status, LocalDateTime receivedAt) {
        var contactInquiry = new ContactInquiry();
        contactInquiry.id = id;
        contactInquiry.fullName = fullName;
        contactInquiry.email = email;
        contactInquiry.message = message;
        contactInquiry.status = status;
        contactInquiry.receivedAt = receivedAt;
        return contactInquiry;
    }

    public void markAsAnswered() {
        if (this.status == InquiryStatus.ANSWERED) {
            throw new IllegalStateException("organizations.error.contactInquiry.alreadyAnswered");
        }
        this.status = InquiryStatus.ANSWERED;
    }
}
