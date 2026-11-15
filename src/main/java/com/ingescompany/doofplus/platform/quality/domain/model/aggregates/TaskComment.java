package com.ingescompany.doofplus.platform.quality.domain.model.aggregates;

import com.ingescompany.doofplus.platform.quality.domain.model.commands.CreateTaskCommentCommand;
import com.ingescompany.doofplus.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.Getter;

/**
 * Task comment of the Quality and Compliance bounded context, as in class-06-quality.
 * People are kept by their name and related records by their code, as the Web Application shows them.
 */
@Getter
public class TaskComment extends AbstractDomainAggregateRoot<TaskComment> {

    /**
     * The identifier; null until the task comment is saved.
     */
    private Long id;

    /**
     * The record code.
     */
    private String recordCode;

    /**
     * The author.
     */
    private String author;

    /**
     * The initials.
     */
    private String initials;

    /**
     * The posted at.
     */
    private LocalDateTime postedAt;

    /**
     * The text.
     */
    private String text;

    private TaskComment() {
    }

    /**
     * Creates the task comment from its command.
     *
     * @param command the data of the task comment
     * @throws IllegalArgumentException with a message key when a required value is blank
     */
    public TaskComment(CreateTaskCommentCommand command) {
        apply(command.recordCode(), command.author(), command.initials(), command.postedAt(), command.text());
    }

    /**
     * Rebuilds a saved task comment without validating it again.
     *
     * @return the task comment
     */
    public static TaskComment reconstitute(Long id, String recordCode, String author, String initials, LocalDateTime postedAt, String text) {
        var taskComment = new TaskComment();
        taskComment.id = id;
        taskComment.recordCode = recordCode;
        taskComment.author = author;
        taskComment.initials = initials;
        taskComment.postedAt = postedAt;
        taskComment.text = text;
        return taskComment;
    }

    private void apply(String recordCode, String author, String initials, LocalDateTime postedAt, String text) {
        if (recordCode == null || recordCode.isBlank()) throw new IllegalArgumentException("quality.taskComment.error.recordCode.notBlank");
        this.recordCode = recordCode == null ? "" : recordCode.trim();
        this.author = author == null ? "" : author.trim();
        this.initials = initials == null ? "" : initials.trim();
        this.postedAt = postedAt != null ? postedAt : LocalDateTime.now(ZoneOffset.UTC);
        if (text == null || text.isBlank()) throw new IllegalArgumentException("quality.taskComment.error.text.notBlank");
        this.text = text == null ? "" : text.trim();
    }
}
