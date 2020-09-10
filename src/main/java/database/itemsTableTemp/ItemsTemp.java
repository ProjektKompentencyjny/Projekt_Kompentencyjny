package database.itemsTableTemp;

import database.groupsTable.GroupsEntity;
import database.itemsTableUsual.ItemsUsualEntity;
import database.locationsTable.LocationsEntity;
import database.roomTable.RoomEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import javax.persistence.*;
import java.util.List;

public class ItemsTemp {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");


    public static List<ItemsEntityTemp> getAllFromItems() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        return session.createQuery("SELECT distinct a FROM ItemsEntityTemp a", ItemsEntityTemp.class).getResultList();
    }
    public static List<String> getInvoiceNumber(){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(ItemsEntityTemp.class);
        criteria.setProjection(Projections.distinct(Projections.property("invoiceNumber")));
        List<String> inv = criteria.list();
        return inv;
    }

    public static void insertFromItemsTableUsual(ItemsUsualEntity itemsUsualEntity, byte[] qrCode){

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createNativeQuery("Insert INTO items_temp(Item_ID,Item_Name,Invoice_Number,NetValue,GrossValue,Item_Image,Qr_Code) VALUES (:a,:b,:c,:d,:e,:f,:g)")
                .setParameter("a", itemsUsualEntity.getId())
                .setParameter("b", itemsUsualEntity.getItemName())
                .setParameter("c", itemsUsualEntity.getInvoiceNumber())
                .setParameter("d", itemsUsualEntity.getNetValue())
                .setParameter("e", itemsUsualEntity.getGrossValue())
                .setParameter("f", itemsUsualEntity.getItemImage())
                .setParameter("g",qrCode)
                .executeUpdate();
        et.commit();
        em.close();

    }

    public static List<ItemsEntityTemp> getAllByInvNumber(String invNumber) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM ItemsEntityTemp a where a.invoiceNumber = :invNumber";
        TypedQuery<ItemsEntityTemp> tq = session.createQuery(query,ItemsEntityTemp.class);
        tq.setParameter("invNumber",invNumber);
        return tq.getResultList();
    }

    public static List<ItemsEntityTemp> getAllByLocId(Integer locId) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM ItemsEntityTemp a where a.locationID = :locId";
        TypedQuery<ItemsEntityTemp> tq = session.createQuery(query,ItemsEntityTemp.class);
        tq.setParameter("locId",locId);
        return tq.getResultList();
    }

    public static List<ItemsEntityTemp> getAllByRoomId(Integer roomId) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM ItemsEntityTemp a where a.roomId = :roomId";
        TypedQuery<ItemsEntityTemp> tq = session.createQuery(query,ItemsEntityTemp.class);
        tq.setParameter("roomId",roomId);
        return tq.getResultList();
    }

    public static byte[] getByteArrayImage(String invNumber, String itemId, Integer rowId){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT a FROM ItemsEntityTemp a where a.invoiceNumber = :invNumber and a.itemId =:itemId and a.rowId = :rowId";
        TypedQuery<ItemsEntityTemp> tq = em.createQuery(query,ItemsEntityTemp.class);
        tq.setParameter("invNumber",invNumber);
        tq.setParameter("itemId",itemId);
        tq.setParameter("rowId",rowId);
        ItemsEntityTemp itemsEntityTemp = null;
        byte[] image = null;
        try {
            itemsEntityTemp=tq.getSingleResult();
            image=itemsEntityTemp.getItemImage();
            return image;
        }catch (NoResultException ex){
            return image;
        }finally {
            em.close();
        }

    }

    public static void update (String newId, String itemName, Float netValue, Float grossValue, byte[] itemImage, String invNumber, LocationsEntity locationsEntity, RoomEntity roomEntity, byte[] qCode, Integer rowId){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("UPDATE ItemsEntityTemp set itemId = :newId, itemName =:itemName, locationID=:locId,roomId =:roomId , netValue=:netValue, grossValue=:grossValue, itemImage =:itemImage, qrCode = :qCode" +
                " WHERE invoiceNumber = :invoiceNumber and rowId = :rowId");
        query.setParameter("newId", newId);
        query.setParameter("itemName", itemName);
        query.setParameter("netValue", netValue);
        query.setParameter("grossValue", grossValue);
        query.setParameter("itemImage",itemImage);
        query.setParameter("locId", locationsEntity.getIdLocation());
        query.setParameter("roomId",roomEntity.getIdRoom());
        query.setParameter("invoiceNumber",invNumber);
        query.setParameter("qCode",qCode);
        query.setParameter("rowId",rowId);
       // query.setParameter("qr",Qr.qrCodeMatrix(newId));
        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        /*
        if(executeUpdate>0)
            System.out.println("Employee email is updated..");
        */
        session.close();


    }

    public static void deleteFromItemsTemp(Integer rowId){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("delete from ItemsEntityTemp where rowId=:rowId");
        query.setParameter("rowId",rowId);

        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        /*
        if(executeUpdate>0)
            System.out.println("Employee email is updated..");
        */
        session.close();

    }

    public static void updateGroup(Integer rowId, GroupsEntity groupsEntity, String invoiceNumber){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("UPDATE ItemsEntityTemp set groupId =:groupId WHERE invoiceNumber = :invoiceNumber and rowId = :rowId");
        query.setParameter("groupId",groupsEntity.getGroupId());
        query.setParameter("invoiceNumber",invoiceNumber);
        query.setParameter("rowId",rowId);
        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }


}
