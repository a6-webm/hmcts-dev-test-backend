CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100),
    task_description VARCHAR(3000),
    task_status SMALLINT,
    due_time TIMESTAMP
);