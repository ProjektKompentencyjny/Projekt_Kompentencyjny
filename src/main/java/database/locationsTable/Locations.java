package database.locationsTable;

import database.itemsTableUsual.ItemsEntity;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class Locations {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");



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

}
