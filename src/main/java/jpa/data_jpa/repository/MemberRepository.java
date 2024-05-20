package jpa.data_jpa.repository;

import jpa.data_jpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

//spring data jpa 활용해 동작하는 repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
