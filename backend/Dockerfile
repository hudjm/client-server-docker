FROM maven:3.6.3-jdk-11 as backend
WORKDIR /backend
COPY . /backend/
RUN mkdir -p src/main/resources/static
RUN mvn clean install

FROM openjdk:14-jdk-alpine
COPY --from=backend /backend/target/BackEndSoftPlan-1.0-SNAPSHOT.jar ./app.jar
EXPOSE 8080
RUN adduser -D user
USER user
CMD [ "sh", "-c", "java -Dserver.port=$PORT -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]