package com.ingescompany.doofplus.platform.manufacturing.application.internal.commandservices;

import com.ingescompany.doofplus.platform.manufacturing.application.commandservices.MasterFormulaCommandService;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.aggregates.MasterFormula;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.ApproveMasterFormulaCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.model.commands.CreateMasterFormulaCommand;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.MasterFormulaRepository;
import com.ingescompany.doofplus.platform.manufacturing.domain.repositories.ProductRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Command service of master formulas: the product must exist, the version is unique per product,
 * and approving a version locks the one approved before.
 */
@Service
public class MasterFormulaCommandServiceImpl implements MasterFormulaCommandService {
    private final MasterFormulaRepository masterFormulaRepository;
    private final ProductRepository productRepository;

    public MasterFormulaCommandServiceImpl(MasterFormulaRepository masterFormulaRepository,
                                           ProductRepository productRepository) {
        this.masterFormulaRepository = masterFormulaRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Result<MasterFormula, ApplicationError> handle(CreateMasterFormulaCommand command) {
        if (!productRepository.existsByCode(command.productCode())) {
            return Result.failure(ApplicationError.notFound("Product", command.productCode()));
        }
        if (masterFormulaRepository.existsByProductCodeAndVersion(command.productCode(), command.version())) {
            return Result.failure(ApplicationError.conflict("MasterFormula", "manufacturing.error.formula.version.duplicate"));
        }
        return Result.success(masterFormulaRepository.save(new MasterFormula(command)));
    }

    @Override
    @Transactional
    public Result<MasterFormula, ApplicationError> handle(ApproveMasterFormulaCommand command) {
        var found = masterFormulaRepository.findById(command.masterFormulaId());
        if (found.isEmpty()) {
            return Result.failure(ApplicationError.notFound("MasterFormula", command.masterFormulaId().toString()));
        }
        var formula = found.get();
        try {
            formula.approve(command);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.invalidState("MasterFormula", e.getMessage()));
        }
        masterFormulaRepository.findAllByProductCode(formula.getProductCode()).stream()
                .filter(other -> !other.getId().equals(formula.getId()) && other.isApproved())
                .forEach(other -> {
                    other.lock();
                    masterFormulaRepository.save(other);
                });
        return Result.success(masterFormulaRepository.save(formula));
    }
}
