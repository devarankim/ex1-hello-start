package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //persistence.xml에서 선언한 unit-name을 넣어준다.
        EntityManager em = emf.createEntityManager();

        //jpa에서는 무조건 !트랜잭션 안에서! 작업해야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            //team.getMembers().add(member);
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team); //연관관계의 주인에 꼭 값 설정.
            em.persist(member);


            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member member1 : members) {
                System.out.println("member1.getUsername() = " + member1.getUsername());
                
            }

            /*
            //Member mm = new Member(3L, "a");
            //Member ms = new Member(4L, "b");
            //System.out.println("result = " + (ms==ms)); //true
            //System.out.println("result = " + (ms==mm)); //false
            //Member member1 = em.find(Member.class, 3L);
            System.out.println("======"); //실행해보면 이 전에 절대 쿼리 날라가지 않는다.

            //엔티티를 생성한 상태(비영속)
            Member member = new Member();
            member.setId(8L);
            member.setUsername("helloD");

            //엔티티를 영속
            System.out.println("=== before ===");
            em.persist(member); //db를 영속
            System.out.println("=== after ===");

            /*
            //조회
            Member member1 = em.find(Member.class, 3L);
            System.out.println("member1.getName() = " + member1.getName());

            List<Member> selectMFromMemberM = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(5) //페이징
                    .setMaxResults(8) //페이징 --> DB언어에 맞게 바꿔줌 ex)limit, rownum ...
                    .getResultList();
            for (Member member2 : selectMFromMemberM) {
                System.out.println("member2.getName() = " + member2.getName());
            }

            //수정
            member1.setName("helloJPA"); //변경감지! JPA가 트랜잭션 커밋 직전에 데이터 변경 유무 체크한다
            //절대로 수정하고 싶다고 persist를 하면 안된다!

 */
            tx.commit(); // 이 시점에 쿼리 날라감
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
