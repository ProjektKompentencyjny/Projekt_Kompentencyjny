package database.roomTable;

import database.locationsTable.LocationsEntity;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.List;

public class Room {


    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");

    public static List<RoomEntity> getAllByLocId(LocationsEntity locationsEntity) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM RoomEntity a where a.locationsEntityId = :locId";
        TypedQuery<RoomEntity> tq = session.createQuery(query,RoomEntity.class);
        tq.setParameter("locId",locationsEntity);
        return tq.getResultList();
    }

    public static List<RoomEntity> getAllByRoomId(Integer roomId) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM RoomEntity as a WHERE a.idRoom = :roomId";
        TypedQuery<RoomEntity> tq = session.createQuery(query,RoomEntity.class);
        tq.setParameter("roomId",roomId);
        return tq.getResultList();
    }

    public static List<RoomEntity> getAllFromLocations() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        return session.createQuery("SELECT a FROM RoomEntity a", RoomEntity.class).getResultList();
    }

    public static byte[] getByteArrayImage(Integer roomId){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT a FROM RoomEntity a where a.idRoom=:roomId";
        TypedQuery<RoomEntity> tq = em.createQuery(query,RoomEntity.class);
        tq.setParameter("roomId",roomId);
        RoomEntity roomEntity = null;
        byte[] image = null;
        try {
            roomEntity=tq.getSingleResult();
            image=roomEntity.getRoomImage();
            return image;
        }catch (NoResultException ex){
            return image;
        }finally {
            em.close();
        }

    }

    public static void deleteFromRoom(Integer roomID){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("delete from RoomEntity a where a.idRoom=:roomID");
        query.setParameter("roomID",roomID);

        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        /*
        if(executeUpdate>0)
            System.out.println("Employee email is updated..");
        */
        session.close();

    }

    public static void updateRoom(Integer roomID, String roomName, byte[] roomImage, byte[] qCode, LocationsEntity locationsEntity){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("UPDATE RoomEntity set roomName =: roomName, locationsEntityId = :locationsEntity" +
                ", roomImage= : roomImage, qrCode = : qCode WHERE idRoom =: roomID");
        query.setParameter("roomID",roomID);
        query.setParameter("roomName",roomName);
        query.setParameter("locationsEntity",locationsEntity);
        query.setParameter("roomImage",roomImage);
        query.setParameter("qCode",qCode);

        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        session.close();


    }

    public static void insertRoom(RoomEntity roomEntity){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        session.beginTransaction();

        session.save(roomEntity);
        session.getTransaction().commit();
        session.close();

    }


}
