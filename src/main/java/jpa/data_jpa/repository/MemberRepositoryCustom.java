package jpa.data_jpa.repository;

import jpa.data_jpa.domain.Member;

import java.util.List;

//user defined repository interface
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
