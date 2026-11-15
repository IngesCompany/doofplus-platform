package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.QualityDocumentCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.QualityDocumentQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllQualityDocumentsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetQualityDocumentByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateQualityDocumentResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.QualityDocumentResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.UpdateQualityDocumentResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateQualityDocumentCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.QualityDocumentResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.UpdateQualityDocumentCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller of the quality documents, as in contratos/quality-api.json.
 */
@RestController
@RequestMapping(value = "/api/v1/quality-documents", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Quality documents", description = "Quality documents of Quality and Compliance")
public class QualityDocumentsController {

    private final QualityDocumentCommandService commandService;
    private final QualityDocumentQueryService queryService;

    /**
     * Creates the controller.
     *
     * @param commandService the command service
     * @param queryService   the query service
     */
    public QualityDocumentsController(QualityDocumentCommandService commandService, QualityDocumentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Creates a quality document.
     *
     * @param resource the data of the quality document
     * @return 201 with the quality document, 400 when the data is not valid
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a quality document")
    public ResponseEntity<?> createQualityDocument(@RequestBody CreateQualityDocumentResource resource) {
        var command = CreateQualityDocumentCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                QualityDocumentResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    /**
     * Lists the quality documents, optionally filtered by code.
     *
     * @param code the code, or none for all
     *
     * @return 200 with the quality documents
     */
    @GetMapping
    @Operation(summary = "Get the quality documents")
    public ResponseEntity<List<QualityDocumentResource>> getQualityDocuments(@RequestParam(required = false) String code) {
        var resources = queryService.handle(new GetAllQualityDocumentsQuery(code)).stream()
                .map(QualityDocumentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Gets a quality document by its identifier.
     *
     * @param qualityDocumentId the identifier
     * @return 200 with the quality document, or 404
     */
    @GetMapping("/{qualityDocumentId}")
    @Operation(summary = "Get a quality document by its identifier")
    public ResponseEntity<?> getQualityDocumentById(@PathVariable Long qualityDocumentId) {
        return queryService.handle(new GetQualityDocumentByIdQuery(qualityDocumentId))
                .<ResponseEntity<?>>map(found -> ResponseEntity.ok(QualityDocumentResourceFromEntityAssembler.toResourceFromEntity(found)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("QualityDocument", String.valueOf(qualityDocumentId))));
    }

    /**
     * Replaces all the data of a quality document.
     *
     * @param qualityDocumentId the identifier
     * @param resource the new data
     * @return 200 with the quality document, 400, 404
     */
    @PutMapping(value = "/{qualityDocumentId}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a quality document")
    public ResponseEntity<?> updateQualityDocument(@PathVariable Long qualityDocumentId, @RequestBody UpdateQualityDocumentResource resource) {
        var command = UpdateQualityDocumentCommandFromResourceAssembler.toCommandFromResource(qualityDocumentId, resource);
        return ResponseEntityAssembler.toResponseEntityFromResult(commandService.handle(command),
                QualityDocumentResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }
}
