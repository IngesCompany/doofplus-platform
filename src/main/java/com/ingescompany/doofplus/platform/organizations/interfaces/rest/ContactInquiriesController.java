package com.ingescompany.doofplus.platform.organizations.interfaces.rest;

import com.ingescompany.doofplus.platform.organizations.application.commandservices.ContactInquiryCommandService;
import com.ingescompany.doofplus.platform.organizations.application.queryservices.ContactInquiryQueryService;
import com.ingescompany.doofplus.platform.organizations.domain.model.queries.GetAllContactInquiriesQuery;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.ContactInquiryResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.resources.SendContactInquiryResource;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform.ContactInquiryResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.organizations.interfaces.rest.transform.SendContactInquiryCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes the contact inquiries sent from the public contact us page.
 */
@RestController
@RequestMapping(value = "/api/v1/contact-inquiries", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Contact Inquiries", description = "Contact us form endpoints")
public class ContactInquiriesController {

    private final ContactInquiryCommandService commandService;
    private final ContactInquiryQueryService queryService;

    public ContactInquiriesController(ContactInquiryCommandService commandService,
                                      ContactInquiryQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Send a contact inquiry")
    public ResponseEntity<?> sendContactInquiry(@RequestBody SendContactInquiryResource resource) {
        var command = SendContactInquiryCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ContactInquiryResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get every contact inquiry, the most recent first")
    public ResponseEntity<List<ContactInquiryResource>> getAllContactInquiries() {
        var resources = queryService.handle(new GetAllContactInquiriesQuery()).stream()
                .map(ContactInquiryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
