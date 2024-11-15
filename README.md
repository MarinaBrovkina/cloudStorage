## Облачное хранилище файлов

Этот проект представляет собой простое облачное хранилище файлов, реализованное с использованием Spring Boot и PostgreSQL.

### Функциональность:

• Загрузка файлов: Пользователи могут загружать файлы на сервер.
• Хранение файлов:  Файлы хранятся на сервере в каталоге uploads.
• Получение списка файлов:  Пользователи могут получить список своих загруженных файлов.
• Удаление файлов:  Пользователи могут удалять свои файлы.
• Аутентификация: Базовая HTTP-аутентификация для защиты доступа к файлам.
• CORS:  Настроен CORS для разрешения запросов из других доменов.

### Технические детали:

• Язык: Java
• Фреймворк: Spring Boot
• База данных: PostgreSQL
• Зависимости: Spring Data JPA, Spring Security, Lombok
• Тестирование: JUnit, Mockito, Testcontainers

### Запуск проекта:

1. Клонируйте репозиторий:
   git clone <репозиторий>

2. Настройте базу данных: Создайте базу данных PostgreSQL и настройте application.properties или application.yaml  с правильными настройками подключения.
3. Запустите приложение:
   mvn spring-boot:run

### Дополнительная информация:

•  Этот проект - это только базовая реализация, которая может быть расширена для поддержки дополнительных функций, таких как:
*  Разные типы файлов
*  Предварительный просмотр файлов
*  Поделиться файлами
*  Доступ к файлам из разных устройств
*  Дополнительные варианты аутентификации
•  Используйте этот проект как основу для создания своего собственного облачного хранилища файлов.
