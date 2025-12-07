package uk.gov.hmcts.reform.dev.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    private long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime dueTime;

    public Task(TaskCreateReq taskCreateReq) throws NumberFormatException {
        this.title = taskCreateReq.getTitle();
        this.description = taskCreateReq.getDescription();
        this.status = TaskStatus.UNFINISHED;
        this.dueTime = LocalDateTime.of(
                Integer.parseInt(taskCreateReq.getDueTimeYear()),
                Integer.parseInt(taskCreateReq.getDueTimeMonth()),
                Integer.parseInt(taskCreateReq.getDueTimeDay()),
                8, 0
            );
    }
}