FROM gradle:6.7.1-jdk11 as build
COPY . /home/app
WORKDIR /home/app
RUN gradle clean build

FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /home/app/build/libs/spring-kotlin-template-*.jar /usr/local/lib/spring-kotlin-template.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/spring-kotlin-template.jar"]