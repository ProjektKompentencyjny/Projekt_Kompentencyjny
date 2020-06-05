package database;

import javax.persistence.*;

public class Users {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");

    public static int checkUserLogin(String userLogin) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT c FROM Helpusers c WHERE c.userLogin = :UserLogin";

        TypedQuery<Helpusers> tq = em.createQuery(query, Helpusers.class);
        tq.setParameter("UserLogin", userLogin);

        Helpusers helpusers = null;

        try {
            helpusers = tq.getSingleResult();
            System.out.println(tq.getSingleResult());
            int userId = helpusers.getId();
            return userId;
        } catch (NoResultException ex) {
            ex.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }
    public static boolean checkUserLoginPassword(String userLogin, String userPassword) {

        if (checkUserLogin(userLogin) != 0) {
            EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
            String query = "SELECT c FROM Helpusers c WHERE c.userPassword = :UserPassword and c.id = :U_Id";

            TypedQuery<Helpusers> tq = em.createQuery(query, Helpusers.class);
            tq.setParameter("UserPassword", userPassword);
            tq.setParameter("U_Id",checkUserLogin(userLogin));

            Helpusers helpusers = null;
            try {
                helpusers = tq.getSingleResult();
                return true;
            } catch (NoResultException ex) {
                return false;
            } finally {
                em.close();
            }
        }
        return false;

    }
}
