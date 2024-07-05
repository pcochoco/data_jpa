package jpa.data_jpa.repository;

import jakarta.persistence.EntityManager;
import jpa.data_jpa.domain.Member;
import lombok.RequiredArgsConstructor;

import java.util.List;

//user defined repository 구현체
//repository interface + Impl or 사용자 정의 repository interface + Impl 로 이름 만들어야 빈으로 등록
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{
    private EntityManager em;

    @Override
    public List<Member> findMemberCustom(){
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
}
