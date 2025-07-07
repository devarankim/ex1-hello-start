package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
/*
//시퀀스전략
@SequenceGenerator(
        name="member_seq_generator",
        sequenceName = "member_seq"
)

@TableGenerator(
        name="memer_seq_generator",
        table="my_sequence",
        pkColumnValue = "member_seq", allocationSize = 1
)
 */
//@Table(uniqueConstraints="유니크명") //복합키도 가능. 유니크명을 지정할 수 있음
//@Table(name="MBR") //객체와 테이블명이 다를 땐 해당 어노테이션 사용(name뒤에는 테이블명)
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "name", nullable=false) //객체는 USERNAME, 테이블컬럼은 name인 경우
    private String username;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); //편의 메소드!
    }

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    private Integer age;

    //@column()
    //private BigDecimal age; //소수점 사용 가능
    @Enumerated(EnumType.STRING) //ORDINAL <- 운영에서는 쓰지 말자. 데이터갸 변경되거나 삭제되면 복구 힘듬
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    /*
    최신버전은 아래 LocalDate 사용하면된다
     */
    private LocalDate createDate2; //년월일
    private LocalDateTime testLocalDateTime; //년월일시간

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description; //문자형이면 clob으로 자료형 자동 생성



    public Member() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", team=" + team + //team.toString()을 호출하는 것과 같다. 무한루프 됨 (member, team 서로 양쪽에서 호출..!)
                ", age=" + age +
                ", roleType=" + roleType +
                ", createdDate=" + createdDate +
                ", createDate2=" + createDate2 +
                ", testLocalDateTime=" + testLocalDateTime +
                ", lastModifiedDate=" + lastModifiedDate +
                ", description='" + description + '\'' +
                '}';
    }
}
