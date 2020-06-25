package database;

import javax.persistence.*;

import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class Users {

    //test

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");

    public static int checkUserLogin(String userLogin) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT c FROM UsersEntity c WHERE c.userLogin = :UserLogin";

        TypedQuery<UsersEntity> tq = em.createQuery(query, UsersEntity.class);
        tq.setParameter("UserLogin", userLogin);

        UsersEntity usersEntity = null;

        try {
            usersEntity = tq.getSingleResult();
            System.out.println(tq.getSingleResult());
            int userId = usersEntity.getId();
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
            String query = "SELECT c FROM UsersEntity c WHERE c.userPassword = :UserPassword and c.id = :U_Id";

            TypedQuery<UsersEntity> tq = em.createQuery(query, UsersEntity.class);
            tq.setParameter("UserPassword", userPassword);
            tq.setParameter("U_Id", checkUserLogin(userLogin));

            UsersEntity usersEntity = null;
            try {
                usersEntity = tq.getSingleResult();
                return true;
            } catch (NoResultException ex) {
                return false;
            } finally {
                em.close();
            }
        }
        return false;

    }

    public static String getUserType(String userLogin) {

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT c FROM UsersEntity c WHERE c.userLogin = :UserLogin";

        TypedQuery<UsersEntity> tq = em.createQuery(query, UsersEntity.class);
        tq.setParameter("UserLogin", userLogin);

        UsersEntity usersEntity = null;

        try {
            usersEntity = tq.getSingleResult();
            String userType = usersEntity.getUserType();
            return userType;
        } catch (NoResultException ex) {
            ex.printStackTrace();
            return "NULL";
        } finally {
            em.close();
        }
    }

    public static String[] getUserInfo(String userLogin){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT c FROM UsersEntity c WHERE c.userLogin = :UserLogin";
        TypedQuery<UsersEntity> tq = em.createQuery(query, UsersEntity.class);
        tq.setParameter("UserLogin", userLogin);

        UsersEntity usersEntity = null;
        String [] info = null;
        try {
            usersEntity = tq.getSingleResult();
            info = new String[]
                    {usersEntity.getName(), usersEntity.getSurname()};
            return info;
        } catch (NoResultException ex) {
            ex.printStackTrace();
            return info;
        } finally {
            em.close();
        }
    }


    public static List<String> getColumnNames(){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();


        Session session = em.unwrap(Session.class);
        SessionFactory sessionFactory = session.getSessionFactory();

        AbstractEntityPersister aep=((AbstractEntityPersister)session.getSessionFactory().getClassMetadata(UsersEntity.class));

        String[] properties=aep.getPropertyNames();

        List<String> list = new ArrayList<String>();

        for(int nameIndex=0;nameIndex!=properties.length;nameIndex++){
            System.out.println("Property name: "+properties[nameIndex]);
            String[] columns=aep.getPropertyColumnNames(nameIndex);
            for(int columnIndex=0;columnIndex!=columns.length;columnIndex++){
                System.out.println("Column name: "+columns[columnIndex]);
                list.add(columns[columnIndex]);
            }

        }
        return list;


    }

    public static List <UsersEntity> getAllfromUsers() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        return session.createQuery("SELECT a FROM UsersEntity a", UsersEntity.class).getResultList();
    }




}