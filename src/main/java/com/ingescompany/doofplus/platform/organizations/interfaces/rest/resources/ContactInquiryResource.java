package com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources;

import java.time.LocalDateTime;

/**
 * Contact inquiry response resource. The status goes in lower case (received or answered).
 */
public record ContactInquiryResource(Long id, String fullName, String email, String message, String status,
                                     LocalDateTime receivedAt) {
}
