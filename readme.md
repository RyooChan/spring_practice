# 스프링부트 게시판

**리팩터링 진행중**
* https://ec2-13-124-106-98.ap-northeast-2.compute.amazonaws.com:8443/
* OSIV, Dirty checking, RESTful개발, 쿼리 최적화 등을 고민하며 진행중입니다.

## ERD
![](https://i.imgur.com/mjAjQh0.png)

- 유저는 **자신이 쓴 글 / 쓴 댓글 / 좋아요한 글** 을 확인할 수 있음
- 게시글에서는 **작성자 / 댓글리스트 / 좋아요 관련 정보** 를 확인할 수 있음

위의 정보들을 위해 Reply, Heart와 일대다 매핑을 Board, User모두에 적용함.

## 사용중 기능
* mapstruct를 사용한 Entity-DTO간 매핑
* spring security의 OAuth2를 사용하여 소셜 로그인 기능 부여
    * 2022-02-16기준 구글, 카카오, 네이버 로그인 기능 부여 완료
      * ec2 서버에서 구글, 카카오, 네이버 로그인 가능. 
* spring security기능 사용하여 권한과 로그인 여부에 따른 기능 차등 부여
* https적용 -> 8443포트 
* AWS를 통한 서버 배포 기능 
    * 4월달을 마지막으로 해제 예정
* [OSIV를 적용](https://hello-backend.tistory.com/148)하여 성능 최적화에 관한 공부 진행

---

## 기능적인 고민
- 글 내용 불러오기 - 댓글 - 좋아요 를 각각 따로따로 비동기적으로 호출할 예정이며, Soft Delete를 사용할 예정이다. 
   - 이 때에 굳이 연관관계를 통해 연결하는 것이 과연 필요한지, 조금 더 고민해야 할 것 같다
