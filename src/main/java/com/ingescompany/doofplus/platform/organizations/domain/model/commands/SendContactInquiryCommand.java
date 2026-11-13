package com.ingescompany.doofplus.platform.organizations.domain.model.commands;

import java.util.regex.Pattern;

/**
 * Command to send a contact inquiry from the public contact us page.
 */
public record SendContactInquiryCommand(String fullName, String email, String message) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final int FULL_NAME_MAX_LENGTH = 150;
    private static final int EMAIL_MAX_LENGTH = 255;

    public SendContactInquiryCommand {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("organizations.error.contactInquiry.fullName.notBlank");
        }
        if (fullName.length() > FULL_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("organizations.error.contactInquiry.fullName.size");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("organizations.error.contactInquiry.email.notBlank");
        }
        if (email.length() > EMAIL_MAX_LENGTH || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("organizations.error.contactInquiry.email.pattern");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("organizations.error.contactInquiry.message.notBlank");
        }
    }
}
