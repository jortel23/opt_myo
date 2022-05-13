FROM openjdk:8-jdk-alpine
LABEL author="jortel"
COPY target/*.jar otp_myo.jar
ENTRYPOINT ["java", "-jar", "/otp_myo.jar"]
EXPOSE 8090