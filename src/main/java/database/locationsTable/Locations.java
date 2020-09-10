package database.locationsTable;

import org.hibernate.Session;

import javax.persistence.*;
import java.util.List;

public class Locations {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");



    public static void insertLocation(LocationsEntity locationsEntity){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        session.beginTransaction();

        session.save(locationsEntity);
        session.getTransaction().commit();
        session.close();

    }



    public static List<LocationsEntity> getAllFromLocations() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        return session.createQuery("SELECT a FROM LocationsEntity a", LocationsEntity.class).getResultList();
    }

    public static List<LocationsEntity> getAllFromLocationbyLocationId(Integer locationId) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM LocationsEntity as a WHERE a.idLocation = :locationId";
        TypedQuery<LocationsEntity> tq = session.createQuery(query,LocationsEntity.class);
        tq.setParameter("locationId",locationId);
        return tq.getResultList();
    }

    public static void updateLocations(Integer locID, String locName, String streetAddres,String postalCode,
                                   String city,byte[] locImage,byte[] qCode){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("UPDATE LocationsEntity set nameLocation=:locName,streetAddres=:streetAddres," +
                "postalCode=:postalCode,city=:city,locImage=:locImage,qrCode=:qCode WHERE idLocation = :locId ");
        query.setParameter("locName",locName);
        query.setParameter("streetAddres",streetAddres);
        query.setParameter("postalCode",postalCode);
        query.setParameter("city",city);
        query.setParameter("locImage",locImage);
        query.setParameter("qCode",qCode);
        query.setParameter("locId",locID);
        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    public static byte[] getByteArrayImage(Integer locId){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT a FROM LocationsEntity a where a.idLocation=:locId";
        TypedQuery<LocationsEntity> tq = em.createQuery(query,LocationsEntity.class);
        tq.setParameter("locId",locId);
        LocationsEntity locationsEntity = null;
        byte[] image = null;
        try {
            locationsEntity=tq.getSingleResult();
            image=locationsEntity.getLocImage();
            return image;
        }catch (NoResultException ex){
            return image;
        }finally {
            em.close();
        }

    }

    public static void deleteFromItemsTemp(Integer locId){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("delete from LocationsEntity where idLocation=:locId");
        query.setParameter("locId",locId);

        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        /*
        if(executeUpdate>0)
            System.out.println("Employee email is updated..");
        */
        session.close();

    }




}
