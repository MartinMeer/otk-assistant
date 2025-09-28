FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
#Cashe-dependencies Layer
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle/ ./gradle/
RUN ./gradlew dependencies --no-daemon
#Loading the source code
COPY src/ ./src/
#BUilding form sources
RUN ./gradlew clean build --no-daemon
CMD ["java", "-jar", "./build/libs/otk-assistant", "*.jar"]

#ENTRYPOINT ["top", "-b"]