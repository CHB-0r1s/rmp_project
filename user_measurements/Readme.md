# Микросервис для работы с параметрами пользователя

> Базовый путь: `/user_measurements`  
> Все запросы требуют заголовок авторизации `Authorization: Bearer <токен>`

---

## Эндпоинты

### 1. Получение всех измерений

- **Метод:** `GET`  
- **URL:** `/user_measurements/measurements`  
- **Аутентификация:** Bearer-токен

#### Пример запроса
```bash
curl -X GET http://localhost/user_measurements/measurements \
  -H "Authorization: Bearer <Токен_пользователя>" \
  -H "Accept: application/json"
````

#### Успешный ответ `200 OK`

```json
{
  "gender": "male",
  "weight": 123.0,
  "height": 0.0,
  "waist": 82.0,
  "hips":  90.0,
  "chest": 100.0,
  "arms": 32.5,
  "bodyFat": 18.2,
  "muscleMass": 30.4,
  "bloodGlucose": 5.6,
  "bloodPressureSystolic": 120,
  "bloodPressureDiastolic": 80,
  "measuredAt": "2025-05-06T10:42:08.994192",
}
```

| Поле                     | Тип        | Описание                               |
| ------------------------ | ---------- | -------------------------------------- |
| `gender`                 | `string`   | Пол (male, female)                     |
| `weight`                 | `float`    | Вес (кг)                               |
| `height`                 | `float`    | Рост (см)                               |
| `waist`                  | `float`    | Обхват талии (см)                      |
| `hips`                   | `float`    | Обхват бёдер (см)                      |
| `chest`                  | `float`    | Обхват груди (см)                      |
| `arms`                   | `float`    | Обхват рук (см)                        |
| `bodyFat`                | `float`    | Процент жира (%)                       |
| `muscleMass`             | `float`    | Мышечная масса (кг)                    |
| `bloodGlucose`           | `float`    | Глюкоза в крови (ммоль/л)              |
| `bloodPressureSystolic`  | `integer`  | Систолическое давление (мм рт. ст.)    |
| `bloodPressureDiastolic` | `integer`  | Диастолическое давление (мм рт. ст.)   |
| `measuredAt`             | `datetime` | Время последнего обновления (ISO-8601) |

#### Возможные ошибки

* `401 Unauthorized` — неверный или отсутствующий токен
* `500 Internal Server Error` — внутренняя ошибка сервиса

---

### 2. Обновление отдельных метрик

> Все эндпоинты из этой группы принимают и возвращают полный объект `MeasurementsResponse` (см. выше), но обновляют только одну метрику.

#### 2.1. Обновление веса

* **Метод:** `POST`
* **URL:** `/user_measurements/update-weight`

##### Пример запроса

```bash
curl -X POST http://localhost/user_measurements/update-weight \
  -H "Authorization: Bearer <Токен_пользователя>" \
  -H "Content-Type: application/json" \
  -d '{"weight": 75.0}'
```

##### Тело запроса

| Поле     | Тип     | Описание |
| -------- | ------- | -------- |
| `weight` | `float` | Вес (кг) |

##### Пример успешного ответа `200 OK`

```json
{
  "gender": "male",
  "weight": 123.0,
  "height": 0.0,
  "waist": 82.0,
  "hips": 95.0,
  "chest": 100.0,
  "arms": 32.5,
  "bodyFat": 18.2,
  "muscleMass": 30.4,
  "bloodGlucose": 5.6,
  "bloodPressureSystolic": 120,
  "bloodPressureDiastolic": 80,
  "measuredAt": "2025-05-06T12:15:30.123456",
}
```

#### 2.2. Обновление талии

* **Метод:** `POST`
* **URL:** `/user_measurements/update-waist`

##### Тело запроса

| Поле    | Тип     | Описание          |
| ------- | ------- | ----------------- |
| `waist` | `float` | Обхват талии (см) |

##### Пример

```bash
curl -X POST http://localhost/user_measurements/update-waist \
  -H "Authorization: Bearer <Токен>" \
  -H "Content-Type: application/json" \
  -d '{"waist": 80.0}'
```

*(ответ аналогичен примеру в пункте 2.1)*

#### 2.3. Обновление бёдер

* **Метод:** `POST`
* **URL:** `/user_measurements/update-hips`

##### Тело запроса

| Поле   | Тип     | Описание          |
| ------ | ------- | ----------------- |
| `hips` | `float` | Обхват бёдер (см) |

#### 2.4. Обновление груди

* **Метод:** `POST`
* **URL:** `/user_measurements/update-chest`

##### Тело запроса

| Поле    | Тип     | Описание          |
| ------- | ------- | ----------------- |
| `chest` | `float` | Обхват груди (см) |

#### 2.5. Обновление объёма жира

* **Метод:** `POST`
* **URL:** `/user_measurements/update-body-fat`

##### Тело запроса

| Поле      | Тип     | Описание         |
| --------- | ------- | ---------------- |
| `bodyFat` | `float` | Процент жира (%) |

#### 2.6. Обновление мышечной массы

* **Метод:** `POST`
* **URL:** `/user_measurements/update-muscle-mass`

##### Тело запроса

| Поле         | Тип     | Описание            |
| ------------ | ------- | ------------------- |
| `muscleMass` | `float` | Мышечная масса (кг) |

#### 2.7. Обновление глюкозы в крови

* **Метод:** `POST`
* **URL:** `/user_measurements/update-blood-glucose`

##### Тело запроса

| Поле           | Тип     | Описание                  |
| -------------- | ------- | ------------------------- |
| `bloodGlucose` | `float` | Глюкоза в крови (ммоль/л) |

#### 2.8. Обновление систолического давления

* **Метод:** `POST`
* **URL:** `/user_measurements/update-blood-pressure-systolic`

##### Тело запроса

| Поле       | Тип       | Описание                            |
| ---------- | --------- | ----------------------------------- |
| `systolic` | `integer` | Систолическое давление (мм рт. ст.) |

#### 2.9. Обновление диастолического давления

* **Метод:** `POST`
* **URL:** `/user_measurements/update-blood-pressure-diastolic`

##### Тело запроса

| Поле        | Тип       | Описание                             |
| ----------- | --------- | ------------------------------------ |
| `diastolic` | `integer` | Диастолическое давление (мм рт. ст.) |

#### 2.10. Обновление пола

* **Метод:** `POST`
* **URL:** `/user_measurements/update-gender`

##### Тело запроса

| Поле       | Тип       | Описание                            |
| ---------- | --------- | ----------------------------------- |
| `gender`   | `string`  | Пол (male, female)                  |

#### 2.11. Обновление роста

* **Метод:** `POST`
* **URL:** `/user_measurements/update-height`

##### Тело запроса

| Поле       | Тип       | Описание                            |
| ---------- | --------- | ----------------------------------- |
| `height`   | `float`   | Рост (см)                           |

> **Во всех случаях** успешный ответ — идентичный ответу из пункта 1 (полный объект MeasurementsResponse) с обновлённым полем и новым `measuredAt`.

#### Возможные ошибки (для всех POST)

* `400 Bad Request` — неверный JSON или отсутствует обязательное поле
* `401 Unauthorized` — неверный/отсутствующий токен
* `500 Internal Server Error` — внутренняя ошибка сервиса

---

## Модели

```kotlin
// Запрос на обновление отдельных полей
data class UpdateGenderRequest(val gender: String) // Пол
data class UpdateWeightRequest(val weight: Float) // Вес
data class UpdateHeightRequest(val height: Float) // Рост
data class UpdateWaistRequest(val waist: Float) // Талия
data class UpdateHipsRequest(val hips: Float) // Бёдра
data class UpdateChestRequest(val chest: Float) // Грудь
data class UpdateArmsRequest(val arms: Float) // Руки
data class UpdateBodyFatRequest(val bodyFat: Float) // Объём жира
data class UpdateMuscleMassRequest(val muscleMass: Float) // Мышечная масса
data class UpdateBloodGlucoseRequest(val bloodGlucose: Float) // Глюкоза в крови
data class UpdateBloodPressureSystolicRequest(val systolic: Int) // Давление систолическое
data class UpdateBloodPressureDiastolicRequest(val diastolic: Int) // Давление диастолическое

// Ответ со всеми замерами
data class MeasurementsResponse(
    val gender: String,
    val weight: Float,
    val height: Float,
    val waist: Float,
    val hips: Float,
    val chest: Float,
    val arms: Float,
    val bodyFat: Float,
    val muscleMass: Float,
    val bloodGlucose: Float,
    val bloodPressureSystolic: Int,
    val bloodPressureDiastolic: Int,
    val measuredAt: String
)
```
