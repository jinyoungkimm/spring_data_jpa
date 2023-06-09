package study.datajpa.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NamedQuery(
        name="Member.findByUsername",
        query="select m from Member m where m.username = :username")
@Getter@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 protected로 자동 생성해준다.
@ToString(of = {"id", "username", "age"})

public class Member extends BaseEntity{ // 등록일,수정일 정보가 필요하므로, JpaBaseEntity를 상속 받았다.

    @Id@GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;


    public Member(String username) {
        this(username, 0);
    }
    public Member(String username, int age) {
        this(username, age, null);
    }
    public Member(String username, int age, Team team) {

        this.username = username;
        this.age = age;

        if (team != null) {
            changeTeam(team);
        }
    }

    //양방향 연관관계 한번에 처리(연관관계 편의 메소드)
    public void changeTeam(Team team){

        this.team = team;
        this.team.getMembers().add(this);

    }

}
