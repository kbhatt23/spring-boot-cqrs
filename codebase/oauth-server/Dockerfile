#running base image
FROM adoptopenjdk/openjdk11:alpine-jre

#documentation
LABEL "MAINTAINER" "KANISHK BHATT"

#documentation
EXPOSE 8080


#cleaning up
RUN rm -rf /tmp/*

#copying latest jar
COPY ./target/*.jar /tmp/app.jar

#DO not want folks to run it command while running
#they can do while docker container exec commadn to get into container
ENTRYPOINT ["java","-jar","/tmp/app.jar"]
