FROM openjdk:17-jdk-slim

# 한국 시간대 설정
ENV TZ=Asia/Seoul
RUN apt-get update && apt-get install -y tzdata && \
    ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata

WORKDIR /app

# 실행 환경 설정 (prod)
ENV SPRING_PROFILES_ACTIVE=prod

# 애플리케이션 JAR 복사
COPY build/libs/*.jar app.jar

EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]