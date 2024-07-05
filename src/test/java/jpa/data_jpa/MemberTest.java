package jpa.data_jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpa.data_jpa.domain.Member;
import jpa.data_jpa.domain.Team;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest //Junit5부터는 @RunWith(SrpingRunner.class)를 사용하지 않아도 됨
@Transactional //각 테스트 코드에서 db로부터 데이터를 가져오고 테스트가 끝나면 db를 원래 상태로 돌려놓음
@Rollback(false) //method 실행 후 db data rollback 결정
//test 이후 날린 query를 보낸 것들을 테스트 완료 후에도 롤백하지 않음
public class MemberTest {
    @PersistenceContext
    EntityManager em;

    //직접 EntityManager로 Member 저장
    @Test
    public void testEntity(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamA);
        Member member3 = new Member("member1", 10, teamB);
        Member member4 = new Member("member1", 10, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush(); //반영
        em.clear(); //초기화

        //담긴 멤버 조회, 확인
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        for(Member member : members){
            System.out.println("member = " + member);
            System.out.println("team = " + member.getTeam());
        }

    }
}
