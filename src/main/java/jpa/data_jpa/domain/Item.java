package jpa.data_jpa.domain;
import jpa.data_jpa.Persistable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String>{
    @Id
    private String id; //식별자 가진 상태에서 save 호출 (식별자 생성 전략이 따로 없음)

    @CreatedDate //id 만으로 새 엔티티인지 구분 어려움 -> 해당 field 값이 없으면 jpa 상에서 생성 x -> 새 엔티티임을 판단하도록 기능
    private LocalDateTime createdDate;

    public Item(String id){
        this.id = id;
    }

    @Override
    public boolean isNew(){
        return createdDate == null;
    }


}
