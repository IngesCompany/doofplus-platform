package com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources;

/**
 * Send contact inquiry request resource.
 */
public record SendContactInquiryResource(String fullName, String email, String message) {
}
