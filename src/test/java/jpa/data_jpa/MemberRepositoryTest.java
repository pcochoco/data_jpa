package jpa.data_jpa;

import jakarta.persistence.EntityManager;
import jpa.data_jpa.domain.Member;
import jpa.data_jpa.domain.MemberDto;
import jpa.data_jpa.domain.Team;
import jpa.data_jpa.repository.MemberRepository;
import jpa.data_jpa.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Rollback(false) //method 실행 후 db data rollback
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository; //구현체가 없는 인터페이스 상태임에도 정상적으로 db에 데이터를 저장하거나 조회 가능

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EntityManager em;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Optional<Member> byId = memberRepository.findById(savedMember.getId());
        //Optional : npe(null pointer exception) 방지용, null 올 수 있는 값을 감싸는 wrapper class
        //내부에 static 변수로 empty 객체를 생성해 미리 가지고 있음
        Member findMember = byId.get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);

    }

    @Test
    public void paging(){
        memberRepository.save(new Member("m1", 10));
        memberRepository.save(new Member("m2", 10));
        memberRepository.save(new Member("m3", 10));
        memberRepository.save(new Member("m4", 10));
        memberRepository.save(new Member("m5", 10));

        int age = 10;
        Pageable pageRequest= PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        List<Member> content = page.getContent();

        //then
        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void slicing(){
        memberRepository.save(new Member("m1", 10));
        memberRepository.save(new Member("m2", 10));
        memberRepository.save(new Member("m3", 10));
        memberRepository.save(new Member("m4", 10));
        memberRepository.save(new Member("m5", 10));

        int age = 10;
        Pageable pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Slice<Member> slice = memberRepository.findSliceByAge(age, pageRequest);
        List<Member> content = slice.getContent();

        //then
        assertThat(content.size()).isEqualTo(3);
        assertThat(slice.getNumber()).isEqualTo(0);
        assertThat(slice.isFirst()).isTrue();
        assertThat(slice.hasNext()).isTrue();
    }

    @Test
    public void pagingDto(){

        memberRepository.save(new Member("m1", 10));
        memberRepository.save(new Member("m2", 10));
        memberRepository.save(new Member("m3", 10));
        memberRepository.save(new Member("m4", 10));
        memberRepository.save(new Member("m5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        List<Member> content = page.getContent();

        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null);

        //then
        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void bulkUpdate(){
        memberRepository.save(new Member("m1", 10));
        memberRepository.save(new Member("m2", 10));
        memberRepository.save(new Member("m3", 10));
        memberRepository.save(new Member("m4", 10));
        memberRepository.save(new Member("m5", 10));

        //when
        int resultCnt = memberRepository.bulkAgePlus(20);

        Member m5 = memberRepository.findByUsername("m5").get(0);
        assertThat(m5.getAge()).isEqualTo(11);

        //then
        assertThat(resultCnt).isEqualTo(3);
    }

    //EntityGraph : sql 한번에 조회(like fetch join)
    @Test
    public void findMemberLazy(){
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member m1 = new Member("m1", 10, teamA);
        Member m2 = new Member("m2", 10, teamB);
        memberRepository.save(m1);
        memberRepository.save(m2);

        em.flush();
        em.clear();

        //N + 1 problem occurs
        List<Member> members = memberRepository.findAll();
        for(Member member : members){
            System.out.println("member = " + member.getUsername());
            System.out.println("member.getTeam() = " + member.getTeam());

        }
    }

    @Test
    public void findMemberEntityGraph(){
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member m1 = new Member("m1", 10, teamA);
        Member m2 = new Member("m2", 10, teamB);
        Member m3 = new Member("m3", 15, teamB);
        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);

        em.flush();
        em.clear();

        //when
        List<Member> members = memberRepository.findAll();
        for(Member member : members){
            System.out.println("member = " + member.getUsername());
            System.out.println("member.getTeam() = " + member.getTeam());
        }

        List<Member> memberEntityGraph = memberRepository.findMemberEntityGraph();
        for(Member member : memberEntityGraph){
            System.out.println("member = " + member);
        }

        List<Member> member1EntityGraph = memberRepository.findEntityGraphByUsername("m1");
        for(Member member : member1EntityGraph){
            System.out.println("member = " + member);
        }
    }

    @Test
    public void queryHint(){
        //given
        Member m1 = new Member("m1", 10);
        memberRepository.save(m1);

        //when
        Member findMem = memberRepository.findReadOnlyByUsername(m1.getUsername());
        findMem.setUsername("mA");

        //then
        em.flush();
    }

}
