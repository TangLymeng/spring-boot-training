# Use a base image with Java installed
FROM openjdk:21-jdk

# Set the working directory inside the container
VOLUME /tmp

# Copy the JAR file from the host machine to the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
