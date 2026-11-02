# Java / Spring challenge

A deliberately complex Spring Boot trigonometric calculation app, the documentation here is also very limited to make it more difficult to understand :]

## Requirements

- Java 21
- Docker and Docker Compose

## Run (Linux)

```bash
docker compose up -d
./gradlew bootRun
```

Running this on Windows should be simple enough too.

## API

Create a task:

```bash
curl -i -X POST http://localhost:8080/tasks \
  -H 'Content-Type: application/json' \
  -d '{"value":1.57079632679,"function":"sin"}'
```

The `202 Accepted` response contains a `taskId`.<br>
You can retrieve the task information:

```bash
curl http://localhost:8080/tasks/<taskId>
```
