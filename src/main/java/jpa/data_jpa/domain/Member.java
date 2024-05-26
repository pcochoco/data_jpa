package jpa.data_jpa.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//entity class access level starts from protected, default constructor needed -> annotation using

@ToString(of = {"id", "username", "age"})
//향후 해당 객체 정보 출력을 위해 설정 -> 가급적이면 연관관계 없는 내부 필드만 적음

/* example of using NamedQuery
@NamedQuery( //named first, reusable, qualified by loading time check
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username" //syntax error leads to compile error
)

 */

public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) //EAGER : N + 1 problem
    @JoinColumn(name = "team_id") //Member ManyToOne, fk 있음 -> 연관관계의 주인
    private Team team;

    //protected Member() {} : entity는 default protected 이상 생성자 필요 -> annotation으로 설정
    public Member(String username){
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team){
        this.username = username;
        this.age = age;
        if (team != null){
            changeTeam(team);
        }
    }

    //relation methods for both side relations
    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}

