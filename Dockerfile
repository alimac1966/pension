FROM eclipse-temurin:21-jdk AS build

WORKDIR /build

COPY . .

RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /deployments

COPY --from=build /build/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /build/target/quarkus-app/*.jar /deployments/
COPY --from=build /build/target/quarkus-app/app/ /deployments/app/
COPY --from=build /build/target/quarkus-app/quarkus/ /deployments/quarkus/

ENTRYPOINT ["java", "-jar", "/deployments/quarkus-run.jar"]