package jpa.data_jpa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") //Member ManyToOne, fk 있음 -> 연관관계의 주인
    private List<Member> members = new ArrayList<>();

    public Team(String name){
        this.name = name;
    }
}
