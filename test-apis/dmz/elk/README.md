# ELK

## Getting Started

1. ### `.env` 파일 생성
   `ELASTIC_USERNAME`, `ELASTIC_PASSWORD`, `RDS_USERNAME`, `RDS_PASSWORD`와 같은 유저 이름, 비밀번호를 입력합니다. `JDBC_CONNECTION_STRING`의 경우, `RDS 경로`와 `DB 이름`을 알맞게 넣어주시기 바랍니다. `ELK_VERSION`은 원하는 버전대로 설정하시면 됩니다.
   ```
   ELK_VERSION=8.10.1
   ELk_PASSWORD=
   JDBC_DRIVER_LIBRARY='/usr/share/logstash/logstash-core/lib/jars/mysql-connector-java.jar'
   JDBC_CONNECTION_STRING='jdbc:mysql://<RDS 경로>:3306/<DB 이름>'
   JDBC_DRIVER_CLASS='com.mysql.jdbc.Driver'
   RDS_USERNAME=
   RDS_PASSWORD=
   ```
2. ### docker-compose 실행
   ```bash
   docker-compose up
   ```
3. ### Elasticsearch 컨테이너의 `kibana_system` 비밀번호 변경
   `kibana_system`의 비밀번호를 변경하기 위해 ElasticSearch 서비스에 아래의 API를 보냅니다. `kibana_system_password`에 .env에서 설정했던 `ELK_PASSWORD`를 대입하면 됩니다.
   ```bash
   curl -X POST "http://localhost:3200/_security/user/kibana_system/_password" -H "Content-Type: application/json" -d '{
     "password": "<kibana_system_password>"
   }'
   ```
4. ### Elasticsearch에 index 추가
   Elasticsearch에 `genre`, `keyword`, `company`, `channel`, `field`, `movie`, `drama`, `people` index를 추가합니다. 인덱스를 추가하기 위한 curl API는 `es_index.sh`로, 이 파일을 실행하면 ES에 인덱스가 등록됩니다. 각 인덱스에 대한 정보는 `./elasticsearch/index`에 있습니다.
   ```bash
   source es_index.sh
   ```
5. ### Elasticsearch에 bulk로 데이터 삽입
   Elasticsearch의 각 인덱스에 데이터를 삽입합니다. Bulk API를 이용해 대량의 데이터를 한 번에 삽입하며, 각 인덱스마다 curl API를 호출하기 위해 `es_bulk.sh`를 실행합니다.
   ```bash
   source es_bulk.sh
   ```
