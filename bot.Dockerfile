FROM openjdk:21
ADD bot/target/*.jar bot.jar

ENV SCRAPPER_ADDRESS scrapper:8080
ENV KAFKA_ADDRESS kafka:19092

EXPOSE 8090 7080

ENTRYPOINT java -jar bot.jar
