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

TOKEN='eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiIj...'

# create / replace
curl -X POST http://localhost:5015/goals \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"goal_type":"weight_loss","activity_level":"medium","weekly_target":0.5,
       "calorie_goal":1500,"water_goal":3000,"steps_goal":10000,"bju_goal":"standard"}'

# read
curl -H "Authorization: Bearer $TOKEN" http://localhost:5015/goals

# update
curl -X PUT http://localhost:5015/goals \
  -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" \
  -d '{"goal_type":"maintain_weight","activity_level":"low","weekly_target":0.0,
       "calorie_goal":2000,"water_goal":2500,"steps_goal":8000,"bju_goal":"balanced"}'

# delete
curl -X DELETE http://localhost:5015/goals -H "Authorization: Bearer $TOKEN"
