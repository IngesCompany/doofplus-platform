package com.ingescompany.doofplus.platform.quality.interfaces.rest;

import com.ingescompany.doofplus.platform.quality.application.commandservices.QualityDocumentCommandService;
import com.ingescompany.doofplus.platform.quality.application.queryservices.QualityDocumentQueryService;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetAllQualityDocumentsQuery;
import com.ingescompany.doofplus.platform.quality.domain.model.queries.GetQualityDocumentByIdQuery;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.CreateQualityDocumentResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.resources.QualityDocumentResource;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.CreateQualityDocumentCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.quality.interfaces.rest.transform.QualityDocumentResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/quality-documents", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Quality Documents", description = "Quality document management endpoints")
public class QualityDocumentController {

    private final QualityDocumentCommandService commandService;
    private final QualityDocumentQueryService queryService;

    public QualityDocumentController(
            QualityDocumentCommandService commandService,
            QualityDocumentQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Create a quality document")
    public ResponseEntity<?> createDocument(
            @RequestBody CreateQualityDocumentResource resource) {

        var command =
                CreateQualityDocumentCommandFromResourceAssembler.toCommandFromResource(resource);

        var result = commandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                QualityDocumentResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{documentId}")
    @Operation(summary = "Get quality document by ID")
    public ResponseEntity<QualityDocumentResource> getDocumentById(
            @PathVariable Long documentId) {

        return queryService.handle(new GetQualityDocumentByIdQuery(documentId))
                .map(document -> ResponseEntity.ok(
                        QualityDocumentResourceFromEntityAssembler.toResourceFromEntity(document)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all quality documents")
    public ResponseEntity<List<QualityDocumentResource>> getAllDocuments() {

        List<QualityDocumentResource> documents =
                queryService.handle(new GetAllQualityDocumentsQuery())
                        .stream()
                        .map(QualityDocumentResourceFromEntityAssembler::toResourceFromEntity)
                        .toList();

        return ResponseEntity.ok(documents);
    }
}