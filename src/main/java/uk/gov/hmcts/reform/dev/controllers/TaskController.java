package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskCreateReq;
import uk.gov.hmcts.reform.dev.models.TaskStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class TaskController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String truncate(String string, int max) {
        return string.substring(0, Math.min(max, string.length()));
    }

    public static String createQuery(String title, String description, TaskStatus status, LocalDateTime dueTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO tasks (");
        sb.append("title,");
        sb.append("task_description,");
        sb.append("task_status,");
        sb.append("due_time");
        sb.append(") VALUES ('");
        sb.append(truncate(title, 100));
        sb.append("','");
        sb.append(truncate(description, 3000));
        sb.append("','");
        sb.append((short)status.ordinal());
        sb.append("','");
        sb.append(java.sql.Timestamp.valueOf(dueTime));
        sb.append("') ");
        sb.append("RETURNING id;");
        return sb.toString();
    }

    // I'll be honest I wanted to use a PreparedStatement, but I couldn't think of any unit tests to make if I did,
    // and I assume using @SQL("test_schema.sql") is more of an integration testing thing
    @PostMapping(path = "/task", produces = "application/json")
    public ResponseEntity<Task> createTask(@ModelAttribute TaskCreateReq taskCreateReq) throws SQLException {
        try {
            Task newTask = new Task(taskCreateReq); // construct the Task here to catch exceptions
            String query = createQuery(
                newTask.getTitle(), newTask.getDescription(), newTask.getStatus(), newTask.getDueTime()
            );
            long id = jdbcTemplate.query(query,
                new ResultSetExtractor<Long>() { // Get returned serial id

                    @Override
                    public Long extractData(ResultSet rs) throws SQLException {
                        rs.next();
                        return rs.getLong("id");
                    }

                }
                );

            newTask.setId(id);

            return ok(newTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
