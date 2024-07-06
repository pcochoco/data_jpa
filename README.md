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


   paging, sorting (application.yml / PageableDefault)

8. data jpa 구현체 분석
   - transactional : readonly -> 변경 없이 조회 -> persistence context flush x -> 성능 향상
   - save method : 새 엔티티라면 persist, 아니면 merge
   - 새 엔티티인지 구별하는 방법
     식별자가 객체인 경우 null, primitive인 경우 0으로 구별
   - 구별에 관한 문제 
     genereated value인 경우 save 호출 시점에 식별자가 없으므로 문제되지 않지만
     @Id로 직접 할당할 경우 식별자 받은 상태로 save -> merge -> db select로 확인
     => 비효율성 
     

      -> persistable interface로 방지

9. 이외 기능 (query dsl로 대체)
    - specification
    - query by example
    - projection : root entity (closed - getter / open)
    - native query 
   
