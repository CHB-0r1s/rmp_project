## Модель достижений

Достижение имеет следующую структуру:

```json
{
    "title": "First meal",
    "description": "Added your first meal",
    "isUnlocked": true
}
```

### Обязательные поля
- `title`: Название достижения
- `description`: Описание достижения
- `isUnlocked`: Статус получения достижения (true/false)

## Конечные точки

### Получение всех достижений
Получает информацию о всех достижениях пользователя.

**Конечная точка:** `GET /achievements`

**Заголовки:**
- `X-User-ID`: Имя пользователя аутентифицированного пользователя(только для тестов. В проде этот заголовок проставляется сам из токена)

**Ответ (200 OK):**
```json
{
    "achievements": {
        "FIRST_FOOD_ENTRY": {
            "title": "First meal",
            "description": "Added your first meal",
            "isUnlocked": true
        }
    }
}
```

**Пример curl:**
```bash
curl -X GET http://localhost:5023/achievements \
  -H "X-User-ID: username"
```

### Получение конкретного достижения
Получает информацию о конкретном достижении пользователя.

**Конечная точка:** `GET /achievements/{achievement}`

**Заголовки:**
- `X-User-ID`: Имя пользователя аутентифицированного пользователя(только для тестов. В проде этот заголовок проставляется сам из токена)

**Параметры пути:**
- `achievement`: Название достижения (например, FIRST_FOOD_ENTRY)

**Ответ (200 OK):**
```json
{
    "title": "First meal",
    "description": "Added your first meal",
    "isUnlocked": true
}
```

**Пример curl:**
```bash
curl -X GET http://localhost:5023/achievements/FIRST_FOOD_ENTRY \
  -H "X-User-ID: username"
```
