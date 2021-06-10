# 개념

### 목차

    1. JDBC와 DBCP
    2. Security

#### JDBC와 DBCP

- DBCP의 정의

  1.  DBCP(DataBase Connection Pool)
  2.  데이터베이스와 애플리케이션을 효율적으로 연 결하는 커넥션 풀 라이브러리

이러한 DBCP는 WAS가 실행되면서 미리 일정량의 DB Connection 객체를 생성하고 Pool이라는 공간에 저장합니다. 저장된 DB Connection 객체는 요청에 따라 필요할 때마다 Pool에서 가져다 쓰고 반환할 수 있습니다. 따라서 요청할 때마다 DB Driver에 로드하여 물리적인 Connection 객체를 생성하는 비용이 줄어들어 앞서 말씀드린 문제를 해결할 수 있게 됩니다.

    DBCP 고려사항

maxActive
DBCP에서 가장 중요한 성능 요소는 maxActive(커넥션의 최대 개수)이며, 이를 설정할 때는 고려해야 할 사항이 있습니다.

DBMS가 수용할 수 있는 Connection의 개수 확인
애플리케이션 서버 인스턴스 1개가 사용하기에 적절한 개수 설정
maxAcitve가 중요한 이유
'사용자가 몰려서 커넥션을 많이 사용할 경우 maxActive값이 충분히 크지 않다면, 서버에서는 많은 요청을 처리하지 못하고 병목현상이 발생하게 됩니다. 반대로, 사용자에 비해 maxActive값이 너무 크다면, 불필요하게 메모리를 많이 점유하게 됩니다. 따라서, 실제 운영 환경에서 직접 성능테스트를 진행하며 최적화된 값을 찾아내는 것이 중요하다고 할 수 있습니다.

WAS Thread
또한 중요한 것은, WAS Thread 수는 DB Connection Pool 개수보다 크게 설정하는 것이 좋습니다. 그 이유는, 애플리케이션에 대한 모든 요청이 DB에 접근하는 것이 아니기 때문입니다. 따라서 WAS Thread 수는 환경에 따라 다르겠지만 아래와 같이 설정하는 것이 좋습니다.

WAS Thread = Connection Pool + 10

#### Security

dasfdsfas
