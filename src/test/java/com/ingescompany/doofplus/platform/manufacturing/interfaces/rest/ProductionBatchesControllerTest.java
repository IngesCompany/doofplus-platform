package com.ingescompany.doofplus.platform.manufacturing.interfaces.rest;

import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.ProductionBatchCommandService;
import com.ingescompany.doofplus.platform.manufacturing.application.queryservices.ProductionBatchQueryService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.queries.GetBatchByIdQuery;
import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductionBatchesControllerTest {

    @Test
    void getBatchByIdAnswersNotFoundWithProblemDetail() {
        var queryService = mock(ProductionBatchQueryService.class);
        when(queryService.handle(any(GetBatchByIdQuery.class))).thenReturn(Optional.empty());
        var controller = new ProductionBatchesController(mock(ProductionBatchCommandService.class), queryService);

        var response = controller.getBatchById(42L);

        assertEquals(404, response.getStatusCode().value());
        var problemDetail = assertInstanceOf(ProblemDetail.class, response.getBody());
        assertEquals(404, problemDetail.getStatus());
        assertEquals("PRODUCTIONBATCH_NOT_FOUND", problemDetail.getProperties().get("code"));
    }
}
