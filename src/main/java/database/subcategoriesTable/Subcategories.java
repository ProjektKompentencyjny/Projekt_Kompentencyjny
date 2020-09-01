package database.subcategoriesTable;

import database.categoriesTable.CategoriesEntity;
import database.locationsTable.LocationsEntity;
import database.roomTable.RoomEntity;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class Subcategories {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");

    public static List<SubcategoriesEntity> getAllByLocId(CategoriesEntity categoriesEntity) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM SubcategoriesEntity a where a.categoryId = :catId";
        TypedQuery<SubcategoriesEntity> tq = session.createQuery(query,SubcategoriesEntity.class);
        tq.setParameter("catId",categoriesEntity);
        return tq.getResultList();
    }


}
