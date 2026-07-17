FROM mcr.microsoft.com/playwright/java:v1.52.0-noble

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN java -version
RUN mvn -version

RUN mvn dependency:go-offline

CMD ["mvn", "clean", "test"]