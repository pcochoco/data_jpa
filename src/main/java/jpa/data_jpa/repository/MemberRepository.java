package jpa.data_jpa.repository;

import jpa.data_jpa.domain.Member;
import jpa.data_jpa.domain.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

//spring data jpa 활용해 동작하는 repository
//data jpa가 구현 클래스 대신 생성 -> 프록시 기술 활용
//@Repository 생략해도 컴포넌트 스캔 가능
public interface MemberRepository extends JpaRepository<Member, Long> {
    //@Query(name = "Member.findByUsername")
    @Query("select m.username from Member m")
    <List>Member findByUsername(String username); //another way to use NamedQuery
    //실행할 NamedQuery가 없으면 메서드 이름으로 쿼리 생성 전략

    /*
    T : 엔티티, ID, S : 엔티티와 자식 타입
    메서드 : save, delete, getOne(엔티티 프록시로 조회), findAll ...
     */

    //using DTO class
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    //@Param for parameter bindings ex) :names
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names")Collection<String> names);

    //collection : if no result, return empty collection
    List<Member> findByUsernameAndAgeGreaterThan(String Username, int age);
    //single look up : if no result, return null, if there are many -> NonUniqueResultException
    Member findMemberByUsername(String username);
    //Single look up for Optional
    Optional<Member> findOptionalByUsername(String username);

}
