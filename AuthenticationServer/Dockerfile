# Use lightweight OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy the WAR file into the container
COPY target/JwtAuthenticationServer.war app.war

# Expose the port (change if your app runs on a different one)
EXPOSE 8080

# Run the WAR file
ENTRYPOINT ["java", "-jar", "app.war"]
