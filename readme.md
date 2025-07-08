# Пример проекта "Управление мероприятиями"

При запуске будет создана схема БД, ожидаемое подключение к PSQL:

host: `localhost`  
port: `5432`  
db: `eventify_db`  
schema: `eventify`  
user: `postgres`  
password: `postgres`  

При необходимости измените параметры подключения в `src/main/resources/application.yaml`

В системе добавлено два пользователя:  
с правами администратора: `admin@admin.ru` / `admin123`  
с правами пользователя: `user@user.ru` / `user1234`  

