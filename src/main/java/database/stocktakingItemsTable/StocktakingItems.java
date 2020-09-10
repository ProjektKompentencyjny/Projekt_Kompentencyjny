package database.stocktakingItemsTable;

import database.roomTable.RoomEntity;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
}
