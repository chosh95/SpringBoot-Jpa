package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            //비영속 상태
            Member member1 = new Member(150L,"A");
            Member member2 = new Member(160L,"B");

            //영속 상태
            em.persist(member1);
            em.persist(member2);

            Member member3 = em.find(Member.class, 150L);
            System.out.println(member1.getId() + " " + member3.getId());

            System.out.println(member1.getName());
            //이떄 db에 저장된다.
            tx.commit();

        } catch(Exception e){
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
