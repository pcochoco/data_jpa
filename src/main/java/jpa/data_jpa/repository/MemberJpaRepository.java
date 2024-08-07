package jpa.data_jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpa.data_jpa.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//순수 jpa 기반 repository : EntityManager + 추가 기능은 query 로
@Repository
public class MemberJpaRepository {
    @PersistenceContext
    private EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public void delete(Member member){
        em.remove(member);
    }

    //counting members
    public long count(){
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }

    //find by id
    public Member find(Long id){
        return em.find(Member.class, id);
    }

    //find all members
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //find by id
    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    //이름과 나이 기준의 조회 메서드
    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age){
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age", Member.class)
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }

    //NamedQuery 호출 : Member.findByUsername
    public List<Member> findByUsername(String username){
        return em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    //paging과 정렬
    public List<Member> findByPage(int age, int offset, int limit){
        return em.createQuery("select m from Member m where m.age =:age order by m.username desc", Member.class)
                .setParameter("age", age)
                .setFirstResult(offset) //시작
                .setMaxResults(limit) //개수
                .getResultList();
    }

    public long totalCount(int age){
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }

    //bulk edit query : condition에 맞는 data 전부 수정
    public int bulkAgePlus(int age){
        return em.createQuery("update Member m set m.age = m.age + 1 where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate();
    }


}
