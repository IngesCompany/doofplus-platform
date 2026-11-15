package com.ingescompany.doofplus.platform.quality.infrastructure.persistence.jpa.entities;

import com.ingescompany.doofplus.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Row of the task_comments table, as in db-06-quality.
 */
@Entity
@Table(name = "task_comments")
@Getter
@Setter
@NoArgsConstructor
public class TaskCommentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "record_code", length = 60, nullable = false)
    private String recordCode;

    @Column(name = "author", length = 100, nullable = false)
    private String author;

    @Column(name = "initials", length = 5, nullable = false)
    private String initials;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;
}
