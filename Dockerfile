# Official Playwright image containing Java, Maven, Playwright and browsers.
FROM mcr.microsoft.com/playwright/java:v1.52.0-noble

WORKDIR /app

# Copy Maven descriptor first to allow dependency caching.
COPY pom.xml .

# Download project dependencies.
RUN mvn dependency:go-offline

# Copy project source.
COPY src ./src

# Display versions during image build.
RUN java -version && mvn -version

# Default command.
CMD ["mvn", "clean", "test"]