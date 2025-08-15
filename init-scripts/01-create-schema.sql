-- Создание схемы eventify
CREATE SCHEMA IF NOT EXISTS eventify;

-- Установка схемы по умолчанию для пользователя postgres
ALTER USER postgres SET search_path TO eventify, public; 