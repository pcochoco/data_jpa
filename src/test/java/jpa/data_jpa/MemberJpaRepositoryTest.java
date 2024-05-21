package jpa.data_jpa;

import jpa.data_jpa.domain.Member;
import jpa.data_jpa.repository.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//순수 jpa repository test용
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
public class MemberJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD(){
        Member m1 = new Member("m1");
        Member m2 = new Member("m2");

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        Member findMem1 = memberJpaRepository.findById(m1.getId()).get();
        assertThat(findMem1).isEqualTo(m1);

        Member findMem2 = memberJpaRepository.findById(m2.getId()).get();
        assertThat(findMem2).isEqualTo(m2);

        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        memberJpaRepository.delete(m1);
        memberJpaRepository.delete(m2);

        long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan(){
        Member m1 = new Member("A", 10);
        Member m2 = new Member("B", 20);

        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("B", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("B");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void paging(){
        memberJpaRepository.save(new Member("m1", 10));
        memberJpaRepository.save(new Member("m2", 10));
        memberJpaRepository.save(new Member("m3", 10));
        memberJpaRepository.save(new Member("m4", 10));
        memberJpaRepository.save(new Member("m4", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;

        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        assertThat(members.size()).isEqualTo(3); //wanted size for paging
        assertThat(totalCount).isEqualTo(5); //expected number of age of 10 is 5
    }
}
