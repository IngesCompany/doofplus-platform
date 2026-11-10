package com.ingescompany.doofplus.platform.monitoring.application.internal.commandservices;

import com.ingescompany.doofplus.platform.monitoring.application.commandservices.ReadingCommandService;
import com.ingescompany.doofplus.platform.monitoring.domain.model.aggregates.Reading;
import com.ingescompany.doofplus.platform.monitoring.domain.model.commands.CreateReadingCommand;
import com.ingescompany.doofplus.platform.monitoring.domain.repositories.ReadingRepository;
import com.ingescompany.doofplus.platform.shared.application.result.ApplicationError;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link ReadingCommandService}.
 */
@Service
@Transactional
public class ReadingCommandServiceImpl implements ReadingCommandService {

    private final ReadingRepository readingRepository;

    public ReadingCommandServiceImpl(ReadingRepository readingRepository) {
        this.readingRepository = readingRepository;
    }

    @Override
    public Result<Reading, ApplicationError> handle(CreateReadingCommand command) {
        var reading = new Reading(command);
        var savedReading = readingRepository.save(reading);
        return Result.success(savedReading);
    }
}
