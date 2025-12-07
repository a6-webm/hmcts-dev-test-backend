package uk.gov.hmcts.reform.dev.models;

import org.springframework.web.bind.annotation.BindParam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateReq {
    private String title;
    private String description;
    private String dueTimeDay;
    private String dueTimeMonth;
    private String dueTimeYear;

    public TaskCreateReq(
        String title,
        String description,
        @BindParam("due-time-day") String dueTimeDay,
        @BindParam("due-time-month") String dueTimeMonth,
        @BindParam("due-time-year") String dueTimeYear
    ) {
        this.title = title;
        this.description = description;
        this.dueTimeDay = dueTimeDay;
        this.dueTimeMonth = dueTimeMonth;
        this.dueTimeYear = dueTimeYear;
    }
}
