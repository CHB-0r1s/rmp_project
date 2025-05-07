# РМП проект
## Запуск Docker-Compose

### Предварительные требования

1. Убедитесь, что у вас установлены Docker и Docker Compose. Если они не установлены, следуйте официальной документации для установки:
   - [Установка Docker](https://docs.docker.com/get-docker/)
   - [Установка Docker Compose](https://docs.docker.com/compose/install/)


### Шаги для запуска проекта

1. Получите .env файл(скорее всего в телеге) и разместите его в корне проекта
2. Запустите:
     ```bash
     docker-compose up
     ```
### Информация для разработчиков сервисов и фронтенда
[Ссыль](./Services.md)

# 📝 User Goals Service

This service allows users to manage their personal goals: create, retrieve, update, and delete goals.

## 🏃‍♂️ Running the Service
Start the service using Docker:
```bash
docker-compose up --build
```

Stop the service:
```bash
docker-compose down
```

View logs:
```bash
docker-compose logs -f user_goals
```

Access the database:
```bash
docker exec -it db psql -U postgres -d mobile
```

## 📌 API Endpoints

### 1. 📥 Create a Goal (POST)
```bash
curl -X POST http://localhost:5015/goals \
     -H "Content-Type: application/json" \
     -d '{"user_id": "test_user", "goal_type": "weight_loss", "activity_level": "medium", "weekly_target": -0.5, "calorie_goal": 1500, "water_goal": 3000, "steps_goal": 10000, "bju_goal": "standard"}'
```

### 2. 🔍 Retrieve User Goals (GET)
```bash
curl -X GET http://localhost:5015/goals/test_user
```

### 3. ✏️ Update a Goal (PUT)
```bash
curl -X PUT http://localhost:5015/goals/2 \
     -H "Content-Type: application/json" \
     -d '{"calorie_goal": 1600}'
```

### 4. 🗑️ Delete a Goal (DELETE)
```bash
curl -X DELETE http://localhost:5015/goals/2
```

## 📝 Error Handling
- Goal not found:
  ```json
  {"error": "Goal not found"}
  ```
- User does not exist:
  ```json
  {"error": "User does not exist. Please register the user first."}
  ```
- Server error:
  ```json
  {"error": "Internal Server Error"}
  ```
