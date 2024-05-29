package jpa.data_jpa.repository;

import jakarta.persistence.QueryHint;
import jpa.data_jpa.domain.Member;
import jpa.data_jpa.domain.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
/*
    spring data jpa 활용해 동작하는 repository
    data jpa가 구현 클래스 대신 생성 (프록시 기술 활용)
    @Repository 생략해도 컴포넌트 스캔 가능

 */
//T : 엔티티, ID, S : 엔티티와 자식 타입
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    //@Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username); //another way to use NamedQuery : method 이름으로

    //NamedQuery(rarely used) -> method
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List <Member> findMember(@Param("username") String username, @Param("age") int age);


    //이름 이용 조회
    @Query("select m.name from Member m")
    List<String> findUserNameList();

    //Dto using : entity 직접 수정 시 의존 문제
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();


    //@Param for parameter bindings ex) :names
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names")Collection<String> names);


    //collection : if no result, return empty collection(not null)
    List<Member> findByUsernameAndAgeGreaterThan(String Username, int age);

    //single look up : if no result, return null, if there are many -> NonUniqueResultException
    Member findMemberByUsername(String username);

    //Single look up for Optional
    Optional<Member> findOptionalByUsername(String username);


    //paging, sorting
    //1. page : 추가 count query result를 포함
    Page<Member> findByAge(int age, Pageable pageable); //Pageable : interface -> PageRequest 구현체

    //2. slice : 추가 count query 없이 다음 page 확인 : limit + 1 조회, 동작
    Slice<Member> findSliceByAge(int age, Pageable pageable);

    //3. count query 분리 사용
    @Query(
            value = "select m from Member m",
            countQuery = "select count(m.username) from Member m"
    )
    Page<Member> findMemberAllCountBy(Pageable pageable);

    //4. 반환 타입이 List라면 추가 count query 없이 결과만 반환
    List<Member> findTop3By();

    //Bulk edit query
    @Modifying(clearAutomatically = true)//Persistence Context 초기화 -> 과거값이 안남도록
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    //fetch join : 연관된 엔티티 한번에 조회
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();


    //EntityGraph : jpql 없이 fetch join
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = {"team"})
    //@EntityGraph("Member.all") : named entity graph -> entity에 명시
    List<Member> findEntityGraphByUsername(@Param("username") String username);


    //JPA hint
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);
}
