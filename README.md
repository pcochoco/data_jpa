### Spring Data JPA

1. entity 
- protected 이상 : @NoArgsConstructor(access = AccessLevel.PROTECTED)
- 객체 정보 출력 : @ToString(of = {"id", "username", "age"})
    연관관계 있는 경우 제외
- @Transactional
- @Rollback(false)

2. 순수 JPA 기반 repository
-> jpql query 생략한 JpaRepository
-> 사용자 정의 repository로 이외 메서드 구현
   
4. NamedQuery -> @Query 등록

5. paging
- slice
- page : 추가 정보
- Pageable interface -> PageRequest 구현체

5. entity graph : jpql 없는 fetch join

6. auditing : 엔티티 생성, 변경 시각, 사람 추적 
