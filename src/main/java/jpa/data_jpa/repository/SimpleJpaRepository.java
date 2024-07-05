package jpa.data_jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class SimpleJpaRepository<T, ID> implements JpaRepositoryImplementation {
    @PersistenceContext
    EntityManager em;

    //새로운 엔티티이면 저장, 아니면 병합
    /*
        새로운 엔티티인지 확인하는 방법
        - 식별자가 객체인 경우 null
        - 식별자가 primitive 타입인 경우 0

        jpa 식별자 생성 전략이 GeneratedValue이면 save 호출 시점에 식별자가 없으므로 새 엔티티로 인식에 동작
        -> Id만 사용해 직접 할당하는 경우 식별자 값을 받은 상태로 save 호출
        -> 이 경우 merge 호출 : db에 select query 날려 값 확인, db 값이 없으면 새로운 엔티티로 인지

        => Persistable interface를 구현해 판단 로직을 변경 가능
     */
    @Transactional
    public <S extends T> S save(S entity){
        if(entityInformation.isNew(entity)){
            em.persist(entity);
            return entity;
        } else{
            return em.merge(entity);
        }
    }


}
