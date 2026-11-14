package com.ingescompany.doofplus.platform.organizations.infrastructure.persistence.jpa.migrations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Moves the profiles saved in the old {@code profiles} table to {@code user_profiles}, the name the Web App uses.
 * It only acts when the old table exists and the new one is empty; after copying the rows it drops the old table.
 */
@Slf4j
@Component
public class UserProfilesTableMigration implements ApplicationRunner {
    private final JdbcTemplate jdbcTemplate;

    public UserProfilesTableMigration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!tableExists("profiles") || !tableExists("user_profiles")) return;
        Integer pending = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_profiles", Integer.class);
        if (pending == null || pending > 0) return;
        List<String> columns = jdbcTemplate.queryForList(
                "SELECT column_name FROM information_schema.columns WHERE table_schema = DATABASE() "
                        + "AND table_name = 'profiles' AND column_name IN (SELECT column_name FROM information_schema.columns "
                        + "WHERE table_schema = DATABASE() AND table_name = 'user_profiles')", String.class);
        String list = String.join(", ", columns);
        int copied = jdbcTemplate.update("INSERT INTO user_profiles (" + list + ") SELECT " + list + " FROM profiles");
        Integer original = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM profiles", Integer.class);
        if (original != null && original == copied) {
            jdbcTemplate.execute("DROP TABLE profiles");
            log.info("Profiles moved from profiles to user_profiles: {}", copied);
        }
    }

    private boolean tableExists(String table) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = ?",
                Integer.class, table);
        return count != null && count > 0;
    }
}
