### Spring Data JPA

1. entity 
- protected 이상 : @NoArgsConstructor(access = AccessLevel.PROTECTED)
- 객체 정보 출력 : @ToString(of = {"id", "username", "age"})
    연관관계 있는 경우 제외
- @Transactional : db 작업 성공 시 유지 
- @Rollback : transaction 실패 시 원상 복구 

2. 순수 JPA 기반 repository
-> jpql query 생략한 JpaRepository
-> 사용자 정의 repository로 이외 메서드 구현 : Repository 인터페이스명 + Impl 혹은 사용자 정의 Repository 인터페이스명 + Impl


임의의 repository에 @Repository를 붙일 수 있음

4. NamedQuery -> @Query 등록

5. paging
- slice
- page : 추가 정보까지 보여줌 
- Pageable interface -> PageRequest 구현체

5. entity graph : jpql 없는 fetch join

6. auditing : 엔티티 생성, 변경 시각, 사람 추적
   - @EnableJpaAuditing, @EntityListeners(AuditingEntityListener.class)
   - AuditorAware
   - @CreatedDate, @CreatedBy, @LastModifiedDate, @LastModifiedBy 지원


   저장 시점에 등록일, 등록자, 수정일, 수정자 저장
   -> 유지보수 관점에서 편리 : null 방지

7. 웹 확장 : domain class converter
   entity 객체를 중간 과정에서 가져옴

8. paging, sorting
   
   
