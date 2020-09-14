package database.amortizationTable;

import database.itemsTable.ItemsEntity;
import database.locationsTable.LocationsEntity;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class Amortization {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");



    public static void insertAmortization(AmortizationEntity amortizationEntity){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        session.beginTransaction();

        session.save(amortizationEntity);
        session.getTransaction().commit();
        session.close();

    }


    public static List<AmortizationEntity> getAllFromAmortizationbyItemID(ItemsEntity itemsEntity) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM AmortizationEntity as a WHERE a.itemID = :itemsEntity";
        TypedQuery<AmortizationEntity> tq = session.createQuery(query,AmortizationEntity.class);
        tq.setParameter("itemsEntity",itemsEntity);
        return tq.getResultList();
    }
}
