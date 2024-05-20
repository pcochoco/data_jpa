package jpa.data_jpa.repository;

import jpa.data_jpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

//spring data jpa 활용해 동작하는 repository
//data jpa가 구현 클래스 대신 생성 -> 프록시 기술 활용
//@Repository 생략해도 컴포넌트 스캔 가능
public interface MemberRepository extends JpaRepository<Member, Long> {
    //@Query(name = "Member.findByUsername")
    <List>Member findByUsername(String username); //another way to use NamedQuery
    //실행할 NamedQuery가 없으면 메서드 이름으로 쿼리 생성 전략

    /*
    T : 엔티티, ID, S : 엔티티와 자식 타입
    메서드 : save, delete, getOne(엔티티 프록시로 조회), findAll ...
     */
}
