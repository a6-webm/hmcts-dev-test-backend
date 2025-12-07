# HMCTS Dev Test Backend

# Run instructions
Host a postgresql server using the provided `schema.sql`, then run the following command with the appropriate values for each env variable:
```
DB_HOST=<localhost> DB_PORT=<5432> DB_NAME=<tasks> DB_USER_NAME=<test_user> DB_PASSWORD=<*****> ./gradlew run
```

# API endpoints

## Create task
Creates a task on the server database, returning the created task if successful.

**Request:** POST

**Path:** /task

**Returns:** 200 OK on success, 500 internal server error otherwise.

### Body

Content Type: application/x-www-form-urlencoded

`title`: string

`description`: string

`due-time-day`: string

`due-time-month`: string

`due-time-year`: string

### Response

Content Type: application/json

`id`: int

`title`: string

`description`: string

`status`: string (from Java enum)

`dueTime`: string (date)