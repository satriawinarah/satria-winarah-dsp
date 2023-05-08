FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/dsp-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "dsp-0.0.1-SNAPSHOT.jar"]