CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    profile_picture_path VARCHAR(255), -- Путь к файлу на сервере
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS user_steps (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    steps INT NOT NULL DEFAULT 0,
    goal INT NOT NULL DEFAULT 10000,
    date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS products(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    affiliation TEXT NOT NULL,
    water DOUBLE PRECISION,
    mass DOUBLE PRECISION,

    proteins DOUBLE PRECISION,

    fiber DOUBLE PRECISION,
    sugar DOUBLE PRECISION,
    added_sugar DOUBLE PRECISION,

    saturated_fat DOUBLE PRECISION,
    polyunsaturated_fat DOUBLE PRECISION,

    cholesterol DOUBLE PRECISION,
    salt DOUBLE PRECISION,
    alcohol DOUBLE PRECISION,

    vitamin_b7 DOUBLE PRECISION,
    vitamin_c DOUBLE PRECISION,
    vitamin_d DOUBLE PRECISION,
    vitamin_e DOUBLE PRECISION,
    vitamin_k DOUBLE PRECISION,

    calcium DOUBLE PRECISION,
    iron DOUBLE PRECISION,
    zinc DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS user_products (
    username VARCHAR(255) NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (username, product_id),
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS recipes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS recipe_products (
    recipe_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (recipe_id, product_id),
    FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS meals (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    username VARCHAR(255) NOT NULL,
    added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS meals_content (
    meal_id BIGINT NOT NULL,
    content_id BIGINT NOT NULL,
    type_content VARCHAR(100) NOT NULL,
    FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_measurements (
    username         VARCHAR(50)      PRIMARY KEY,
    gender           VARCHAR(10)      NULL DEFAULT 'no_gender',         -- Пол (male/female)
    weight           NUMERIC(8,2)     NULL,         -- Вес (кг)
    height           NUMERIC(8,2)     NULL,         -- Рост (см)
    waist            NUMERIC(8,2)     NULL,         -- Талия (см)
    hips             NUMERIC(8,2)     NULL,         -- Бёдра (см)
    chest            NUMERIC(8,2)     NULL,         -- Грудь (см)
    arms             NUMERIC(8,2)     NULL,         -- Руки (см)
    body_fat         NUMERIC(5,2)     NULL,         -- Объём жира (%)
    muscle_mass      NUMERIC(8,2)     NULL,         -- Мышечная масса (кг)
    blood_glucose    NUMERIC(5,2)     NULL,         -- Глюкоза в крови (ммоль/л)
    bp_systolic      INTEGER          NULL,         -- Давление систолическое (мм рт.ст.)
    bp_diastolic     INTEGER          NULL,         -- Давление диастолическое (мм рт.ст.)
    measured_at      TIMESTAMP WITH TIME ZONE DEFAULT NOW()  -- дата/время замера
);