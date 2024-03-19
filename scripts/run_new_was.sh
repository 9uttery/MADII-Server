#!/bin/bash

CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current port of running WAS is ${CURRENT_PORT}."

if [ "${CURRENT_PORT}" -eq 8081 ]; then
  TARGET_PORT=8082
elif [ "${CURRENT_PORT}" -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "> No WAS is connected to nginx"
fi

# CURRENT_PORT에 연결된 WAS의 PID 찾기
CURRENT_PID=$(lsof -Fp -i TCP:${CURRENT_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

# CURRENT_PORT에서 실행 중인 WAS 종료
if [ -n "${CURRENT_PID}" ]; then
  echo "> Kill WAS running at ${CURRENT_PORT}."
  sudo kill ${CURRENT_PID}
fi

# TARGET_PORT에서 실행할 새로운 WAS의 PID 찾기 및 종료
TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ -n "${TARGET_PID}" ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi

nohup java -jar -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=dev -Duser.timezone=Asia/Seoul /home/ubuntu/seesaw-app/build/libs/madii-0.0.1-SNAPSHOT.jar > /home/ubuntu/nohup.out 2>&1 &
echo "> Now new WAS runs at ${TARGET_PORT}."
exit 0
