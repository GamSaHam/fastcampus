docker -v 

rem 도커 파일 버젼 확인

docker run -i -t ubuntu:14.04

rem 도커파일 우분투 이미지 만들기

exit

rem 도커 컨테이너에서 빠져 나오기

docker images

rem 도커 이미지 목록

docker create -i -t --name mycentos centos:7

docker start mycentos

docker attach mycentos

rem 도커 이미지안에 들어갑니다.

rem ctrl + p,q 를 통해서 빠져나오기 실행을 종료하지 않고

docker ps

rem 도커에서 정지 되지 않은 목록들을 보여줍니다.

docker ps -a

rem 모든 컨테이너를 출력합니다.

docker inspect mycentos | grep Id

rem 전체 아이디 조회

rem docker rename angry_morse my_container
rem 이름 변경하기


docker rm brave_chebychev

rem 도커를 이름을 통해 컨테이너를 삭제합니다.

docker stop mycentos
docker rm mycentos

rem 실행중인 도커 컨테이너는 삭제할수 없음으로 stop 후에 rm을 해야한다.

docker rm -f mycentos
rem 실행여부 상관없이 도커 컨테이너를 삭제합니다.


docker container prune
docker ps -a -q
rem 모든 컨테이너를 삭제

docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

rem 모든 도커 컨테이너를 중지하고 삭제한다.

docker run -i -t --name network_test ubuntu:14.04

rem ifconfig 를 통해서 컨테이너 네트워크 인터페이스를 확인합니다.

docker run -i -t --name mywebserver -p 80:80 ubuntu:14.04

docker run -i -t -p 3306:3306 -p 192.168.0.100:7777:80 ubuntu:14.04

rem 외부 포트 개방인 -p 3306을 개방하고 192.168.0.100아이피에 7777:80 포트를 개방한다
rem 7777포트는 80 포트랑 연결 짓는다.

docker run -d --name detach_test ubuntu:14.04

docker run -i -t \
--name mysql_attach_test \
-e MYSQL_ROOT_PASSWORD=password \
-e MYSQL_DATABASE=wordpress \
mysql:5.





