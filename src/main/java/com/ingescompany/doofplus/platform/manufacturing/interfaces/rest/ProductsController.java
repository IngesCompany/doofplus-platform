package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest;

import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.ProductCommandService;
import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.ProductQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetAllProductsQuery;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetProductByIdQuery;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.resources.*;
import com.ingescompany.doofplus.platform.manufacturing.interfaces.rest.transform.CatalogResourceAssembler;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import com.ingescompany.doofplus.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Products of the catalog (US35).
 */
@RestController
@RequestMapping(value = "/api/v1/products", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Product catalog endpoints")
public class ProductsController {
    private final ProductCommandService commandService;
    private final ProductQueryService queryService;

    public ProductsController(ProductCommandService commandService, ProductQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Registers a product. A repeated code answers 409.
     */
    @PostMapping
    @Operation(summary = "Register a product")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductResource resource) {
        var result = commandService.handle(CatalogResourceAssembler.toCommand(resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(queryService.handle(new GetAllProductsQuery()).stream()
                .map(CatalogResourceAssembler::toResource).toList());
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get a product by its id")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        return queryService.handle(new GetProductByIdQuery(productId))
                .<ResponseEntity<?>>map(product -> ResponseEntity.ok(CatalogResourceAssembler.toResource(product)))
                .orElseGet(() -> ProblemDetailFromApplicationErrorAssembler.toResponseFromApplicationError(
                        ApplicationError.notFound("Product", productId.toString())));
    }

    /**
     * Updates the name and the dosage form of a product.
     */
    @PutMapping("/{productId}")
    @Operation(summary = "Update a product")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductResource resource) {
        var result = commandService.handle(CatalogResourceAssembler.toCommand(productId, resource));
        return ResponseEntityAssembler.toResponseEntityFromResult(result, CatalogResourceAssembler::toResource, HttpStatus.OK);
    }
}
