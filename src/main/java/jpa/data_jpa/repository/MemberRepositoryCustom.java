package jpa.data_jpa.repository;

import jpa.data_jpa.domain.Member;

import java.util.List;

//user defined repository interface
/*
    jpa repository interface -> spring에서 구현체
    => 만일 추가 method를 만들 경우 interface method 모두 직접 구현해야
    => 별도의 사용자 정의 repository를 만드는 것
        jpa와 별개로 @Repository가 붙는 Repository를 만들어도 됨
 */
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
