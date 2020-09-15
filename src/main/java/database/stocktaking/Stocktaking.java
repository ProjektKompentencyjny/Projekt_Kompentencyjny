package database.stocktaking;

import database.invoicesTable.InvoicesEntity;
import database.locationsTable.LocationsEntity;
import org.hibernate.Session;

import javax.persistence.*;
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

    public static void updateReport(Integer stockID, byte[] report){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("UPDATE StocktakingEntity set raport =:report WHERE stockID = :stockID");
        query.setParameter("report",report);
        query.setParameter("stockID",stockID);
        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    public static byte[] getPdf(Integer stockID){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "Select a From StocktakingEntity a where a.stockID =:stockID";
        TypedQuery<StocktakingEntity> tq  = session.createQuery(query,StocktakingEntity.class);
        tq.setParameter("stockID", stockID);
        return tq.getSingleResult().getRaport();
    }
}
