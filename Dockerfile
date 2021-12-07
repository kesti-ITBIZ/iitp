FROM java:8
EXPOSE 9200
ARG JAR_FILE=build/libs/*.war
COPY ${JAR_FILE} ROOT.war
ENTRYPOINT ["java", "-jar", "/ROOT.war"]
