package jpa.data_jpa.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//entity class는 access 수준이 protected 이상인 default 생성자 만들어줘야함 -> annotation으로 자동 생성

@ToString(of = {"id", "username", "age"})
//향후 해당 객체 정보 출력을 위해 설정 -> 가급적이면 연관관계 없는 내부 필드만 적음
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) //EAGER : N + 1 문제
    @JoinColumn(name = "team_id")
    private Team team;

    //protected Member() {} : entity는 default protected 이상 생성자 필요
    public Member(String username){
        this.username = username;
    }

    public Member(String username, int age, Team team){
        this.username = username;
        this.age = age;
        if (team != null){
            changeTeam(team);
        }
    }

    //연관관계 편의 메서드를 통해 양방향 연관관계 객체 처리
    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}

