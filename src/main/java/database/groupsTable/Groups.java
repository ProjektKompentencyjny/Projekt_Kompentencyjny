package database.groupsTable;

import database.categoriesTable.CategoriesEntity;
import database.roomTable.RoomEntity;
import database.subcategoriesTable.Subcategories;
import database.subcategoriesTable.SubcategoriesEntity;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class Groups {


    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");

    public static List<GroupsEntity> getAllByLocId(SubcategoriesEntity subcategoriesEntity) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM GroupsEntity a where a.subCatId = :subCatId";
        TypedQuery<GroupsEntity> tq = session.createQuery(query,GroupsEntity.class);
        tq.setParameter("subCatId",subcategoriesEntity);
        return tq.getResultList();
    }

    public static GroupsEntity getAllByGroupId(Integer groupId) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM GroupsEntity as a WHERE a.groupId = :roomId";
        TypedQuery<GroupsEntity> tq = session.createQuery(query,GroupsEntity.class);
        tq.setParameter("roomId",groupId);
        return tq.getSingleResult();
    }


}
