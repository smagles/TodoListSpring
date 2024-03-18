FROM openjdk:17

COPY ./build/libs/TodoListSpring-0.0.1-SNAPSHOT.jar /app/TodoListSpring-0.0.1-SNAPSHOT.jar

WORKDIR /app

CMD ["java", "-jar", "TodoListSpring-0.0.1-SNAPSHOT.jar"]

