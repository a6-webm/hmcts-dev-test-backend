package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.reform.dev.controllers.TaskController;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.models.TaskStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

class TaskUnitTest {

    @Test
    void createQueryTest() {
        LocalDateTime due = LocalDateTime.of(2025, 9, 20, 10, 30);
        Task task = new Task(0, "Test", "This is a test task testing testing.", TaskStatus.UNFINISHED, due);

        String query = TaskController.createQuery(
                task.getTitle(), task.getDescription(), task.getStatus(), task.getDueTime()
            );
        assertEquals("INSERT INTO tasks (title,task_description,task_status,due_time) "
                + "VALUES ('Test','This is a test task testing testing.','0','2025-09-20 10:30:00.0') "
                + "RETURNING id;", query);
    }

    private String repeatChar(char c, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    @Test
    void createQueryTruncateTest() {
        String chars130 = repeatChar('t', 130);
        String chars3200 = repeatChar('d', 3200);
        LocalDateTime due = LocalDateTime.of(2025, 9, 20, 10, 30);
        Task task = new Task(0, chars130, chars3200, TaskStatus.UNFINISHED, due);

        String query = TaskController.createQuery(
                task.getTitle(), task.getDescription(), task.getStatus(), task.getDueTime()
            );

        assertEquals("INSERT INTO tasks (title,task_description,task_status,due_time) "
                + "VALUES ('"
                + repeatChar('t', 100)
                + "','"
                + repeatChar('d', 3000)
                + "','0','2025-09-20 10:30:00.0') RETURNING id;", query);
    }
}
