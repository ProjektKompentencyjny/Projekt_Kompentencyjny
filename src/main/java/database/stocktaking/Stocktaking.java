package database.stocktaking;

import database.locationsTable.LocationsEntity;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Stocktaking {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");



    public static void insertStockating(StocktakingEntity stocktakingEntity){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        session.beginTransaction();

        session.save(stocktakingEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static List<StocktakingEntity> getAllFromStocktaking() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        return session.createQuery("SELECT a FROM StocktakingEntity a", StocktakingEntity.class).getResultList();
    }
}
