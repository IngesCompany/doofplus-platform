package com.ingescompany.doofplus.platform.manufacturing.infrastructure.persistence.jpa.migrations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Moves the batches saved with the old statuses to the ones of the Web App.
 * {@code CLOSED} and {@code FINISHED} meant that production finished, so they become
 * {@code RELEASE_REQUESTED}. It runs once at startup and does nothing when there is nothing to move.
 */
@Slf4j
@Component
public class BatchStatusDataMigration implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;

    public BatchStatusDataMigration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        int moved = jdbcTemplate.update(
                "UPDATE production_batches SET status = 'RELEASE_REQUESTED' WHERE status IN ('CLOSED', 'FINISHED')");
        if (moved > 0) log.info("Batches moved from CLOSED or FINISHED to RELEASE_REQUESTED: {}", moved);
    }
}
