# Використовуємо образ з Java 17
FROM eclipse-temurin:17-jdk-alpine

# Встановлюємо робочу директорію
WORKDIR /app

# Копіюємо зібраний jar-файл
COPY build/libs/*.jar app.jar

# Відкриваємо порт додатка
EXPOSE 8080

# Команда для запуску
ENTRYPOINT ["java", "-jar", "app.jar"]