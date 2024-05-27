package jpa.data_jpa.repository;

import jakarta.persistence.EntityManager;
import jpa.data_jpa.domain.Member;
import lombok.RequiredArgsConstructor;

import java.util.List;

//user defined repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{
    private EntityManager em;

    @Override
    public List<Member> findMemberCustom(){
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
}
