FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/mongo-shop*.jar /app/mongo-shop.jar
EXPOSE 8080
CMD ["java", "-Xms256m", "-Xmx256m", "-jar", "/app/mongo-shop.jar"]