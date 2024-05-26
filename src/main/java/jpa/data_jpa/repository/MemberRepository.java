package jpa.data_jpa.repository;

import jpa.data_jpa.domain.Member;
import jpa.data_jpa.domain.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
/*
    spring data jpa 활용해 동작하는 repository
    data jpa가 구현 클래스 대신 생성 (프록시 기술 활용)
    @Repository 생략해도 컴포넌트 스캔 가능

 */
//T : 엔티티, ID, S : 엔티티와 자식 타입
public interface MemberRepository extends JpaRepository<Member, Long> {
    //@Query(name = "Member.findByUsername")
    @Query("select m.username from Member m")
    List<Member> findByUsername(String username); //another way to use NamedQuery : method 이름으로

    //NamedQuery(rarely used) -> method
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List <Member> findMember(@Param("username") String username, @Param("age") int age);

    //이름 이용 조회
    @Query("select m.name from Member m")
    List<String> findUserNameList();

    //Dto using
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


}
