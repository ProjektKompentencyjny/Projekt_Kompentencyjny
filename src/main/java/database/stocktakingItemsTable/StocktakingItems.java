package database.stocktakingItemsTable;

import database.roomTable.RoomEntity;
import database.stocktaking.StocktakingEntity;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class StocktakingItems {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");


    public static void insertStockItem (StocktakingItemsEntity stocktakingItemsEntity){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        session.beginTransaction();

        session.save(stocktakingItemsEntity);
        session.getTransaction().commit();
        session.close();

    }

    public static List<StocktakingItemsEntity> getAllByRoomId(StocktakingEntity stockID) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM StocktakingItemsEntity as a WHERE a.stockId = :stockID";
        TypedQuery<StocktakingItemsEntity> tq = session.createQuery(query,StocktakingItemsEntity.class);
        tq.setParameter("stockID",stockID);
        return tq.getResultList();
    }

}
