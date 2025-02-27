#!/bin/bash

# Crawl current connected port of WAS
CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Nginx currently proxies to ${CURRENT_PORT}."

# Toggle port number
if [ "${CURRENT_PORT}" -eq 8081 ]; then
    TARGET_PORT=8082
elif [ "${CURRENT_PORT}" -eq 8082 ]; then
    TARGET_PORT=8081
else
    echo "> No WAS is connected to nginx"
    exit 1
fi

# Change proxying port into target port
echo "set \$service_url http://127.0.0.1:${TARGET_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

echo "> Now Nginx proxies to ${TARGET_PORT}."

# Reload nginx
sudo service nginx reload

echo "> Nginx reloaded."

# CURRENT_PORT에 연결된 WAS의 PID 찾기
CURRENT_PID=$(lsof -Fp -i TCP:"${CURRENT_PORT}" | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

# CURRENT_PORT에서 실행 중인 WAS 종료
if [ -n "${CURRENT_PID}" ]; then
  echo "> Kill WAS running at ${CURRENT_PORT}."
  sudo kill "${CURRENT_PID}"
fi
