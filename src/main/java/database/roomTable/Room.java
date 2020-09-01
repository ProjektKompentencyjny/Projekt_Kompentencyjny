package database.roomTable;

import database.itemsTableUsual.ItemsEntity;
import database.locationsTable.LocationsEntity;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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


}
