
REPOSITORY=/home/ubuntu/app/step2
ROOT_PATH=/home/ubuntu
JAR_PATH=/home/ubuntu/app/step2/zip/build/libs
PROJECT_NAME=Hongsinsa

echo "> Build 파일 복사"
cp $JAR_PATH/*.jar $ROOT_PATH/

echo "> 현재 구동 중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f $PROJECT_NAME)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다"
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $ROOT_PATH/*.jar | tail -n 1)

echo "> JAR_NAME: $JAR_NAME"
echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"
nohup java -jar -Dspring.config.location=classpath:/application.yml,$REPOSITORY/zip/application.yml $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &