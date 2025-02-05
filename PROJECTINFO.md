# 프로젝트정보
 > Rest Api 방식
### 버전및구성 정보

* 프레임워크: Springboot 3.4.1
* 언어: java 21
* Data Access: JPA
* 인증방식: JWT 0.12.6
* Database: Maria, h2
* 아키텍처 : Layered(도매인기반)
* 빌드: gradle-8.11.1-bin
* swagger 2.3.0
* 테스트: junit 5.11.4
* queryDSL: 5.0.0

### 적용모듈 정보 및 사용방법
* 스프링 시큐리티
* 다국어: MessageConfig
* 프로퍼티암호화: JasyptConfig
* JPA 스파이쿼리: P6spyConfig


### 응답구조
#### * 성공 (Status Code: 200)
````
 {
    "header": {
        "message": "성공",
        "locale": "ko",
        "timestamp": "2025-02-05 13:28:17.06"
    },
    "data": {
        "pageInfo": {
            "totalElements": 22,
            "size": 1,
            "totalPages": 22,
            "number": 0,
            "numberOfElements": 1,
            "first": true,
            "last": false,
            "empty": false,
            "sort": {
                "empty": false,
                "sorted": true,
                "unsorted": false
            }
        },
        "resultList": [
            {
            "boardSeq": 1,
            "boardTitle": "오리지지지",
            "boardContents": "나러ㅏㅁ너라ㅣㅁㄴㅇㄹ",
            "viewCount": 0,
            "priorityYn": "Y",
            "deletedYn": "N",
            "createdBy": null,
            "createdDttm": "2025-01-23 22:10:21.60",
            "modifiedBy": "sil1",
            "modifiedDttm": "2025-02-05 13:15:53.40",
            "commentCount": 6,
            "fileCount": 1,
            "files": null
            }
        ]
    }
}
````
#### * 실패 (Status Code: 200이 아닌 모든것)

````
{
  "errorCode": "1006",
  "header": {
    "message": "필드 유효성 검증 실패입니다..",
    "locale": "ko",
    "timestamp": "2025-02-05 13:32:57.35"
  },
  "error": {
    "priorityYn": "[Y2K]허용되지 않은 값입니다."
  }
}
````

#### * 로컬적용
> * [SWAGGER](http://localhost:8080/swagger-ui/index.html)
> * H2 db 사용가능