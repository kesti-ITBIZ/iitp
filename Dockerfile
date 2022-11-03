FROM openjdk:8-jdk
EXPOSE 9100
ARG JAR_FILE=build/libs/*.war
COPY ${JAR_FILE} ROOT.war
ENTRYPOINT ["java", "-jar", "/ROOT.war"]
