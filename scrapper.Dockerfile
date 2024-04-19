FROM openjdk:21
ADD scrapper/target/*.jar scrapper.jar

ENV SCRAPPER_ADDRESS scrapper:8080
ENV KAFKA_ADDRESS kafka:19092

EXPOSE 8080 7070

ENTRYPOINT java -jar scrapper.jar
