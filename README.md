# HMCTS Dev Test Backend

# Run instructions
Host a postgresql server using the provided `schema.sql`, then run the following command with the appropriate values for each env variable:
```
DB_HOST=<localhost> DB_PORT=<5432> DB_NAME=<tasks> DB_USER_NAME=<test_user> DB_PASSWORD=<*****> ./gradlew run
```

