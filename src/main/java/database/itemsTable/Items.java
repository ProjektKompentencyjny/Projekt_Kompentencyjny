package database.itemsTable;

import database.groupsTable.GroupsEntity;
import database.itemsTableTemp.ItemsEntityTemp;
import database.itemsTableUsual.ItemsUsualEntity;
import database.locationsTable.LocationsEntity;
import database.roomTable.RoomEntity;
import org.hibernate.Session;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

public class Items {


    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");

    public static void insertFromItemsTableTemp(ItemsEntityTemp itemsEntityTemp){

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createNativeQuery("Insert INTO items(Item_ID,Item_Name,Invoice_Number,Location_ID,Group_ID,NetValue,GrossValue,Room_ID,Item_Image,Qr_Code) VALUES (:a,:b,:c,:d,:e,:f,:g,:h,:i,:j)")
                .setParameter("a", itemsEntityTemp.getItemId())
                .setParameter("b", itemsEntityTemp.getItemName())
                .setParameter("c", itemsEntityTemp.getInvoiceNumber())
                .setParameter("d", itemsEntityTemp.getLocationID())
                .setParameter("e", itemsEntityTemp.getGroupId())
                .setParameter("f", itemsEntityTemp.getNetValue())
                .setParameter("g",itemsEntityTemp.getGrossValue())
                .setParameter("h",itemsEntityTemp.getRoomId())
                .setParameter("i",itemsEntityTemp.getItemImage())
                .setParameter("j",itemsEntityTemp.getQrCode())
                .executeUpdate();
        et.commit();
        em.close();

    }

    public static List<ItemsEntity> getAllByRoomId(LocationsEntity locationsEntity, Date date) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM ItemsEntity a where a.locationsEntityId = :locationsEntity and a.invoiceNumber.dateOfInvoicing < : date";
        TypedQuery<ItemsEntity> tq = session.createQuery(query,ItemsEntity.class);
        tq.setParameter("locationsEntity",locationsEntity);
        tq.setParameter("date",date);
        return tq.getResultList();
    }

    public static List<ItemsEntity> getAllFromItems() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        return session.createQuery("SELECT a FROM ItemsEntity a", ItemsEntity.class).getResultList();
    }

    public static void update (String newId, String itemName, Float netValue, Float grossValue, byte[] itemImage, String invNumber, LocationsEntity locationsEntity, RoomEntity roomEntity, byte[] qCode, Integer rowId){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("UPDATE ItemsEntity set itemId = :newId, itemName =:itemName, locationsEntityId =:locId, roomEntity =:roomId , netValue=:netValue, grossValue=:grossValue, itemImage =:itemImage, qrCode = :qCode" +
                " WHERE invoiceNumber.invoiceNumber = :invoiceNumber and rowId = :rowId");
        query.setParameter("newId", newId);
        query.setParameter("itemName", itemName);
        query.setParameter("netValue", netValue);
        query.setParameter("grossValue", grossValue);
        query.setParameter("itemImage",itemImage);
        query.setParameter("locId", locationsEntity);
        query.setParameter("roomId",roomEntity);
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

    public static byte[] getByteArrayImage(String invNumber, String itemId, Integer rowId){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT a FROM ItemsEntity a where a.invoiceNumber.invoiceNumber = :invNumber and a.itemId =:itemId and a.rowId = :rowId";
        TypedQuery<ItemsEntity> tq = em.createQuery(query,ItemsEntity.class);
        tq.setParameter("invNumber",invNumber);
        tq.setParameter("itemId",itemId);
        tq.setParameter("rowId",rowId);
        ItemsEntity itemsEntity = null;
        byte[] image = null;
        try {
            itemsEntity=tq.getSingleResult();
            image=itemsEntity.getItemImage();
            return image;
        }catch (NoResultException ex){
            return image;
        }finally {
            em.close();
        }

    }

    public static void deleteFromItemsTemp(Integer rowId){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("delete from ItemsEntity where rowId=:rowId");
        query.setParameter("rowId",rowId);

        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        /*
        if(executeUpdate>0)
            System.out.println("Employee email is updated..");
        */

        session.close();
        em.close();
    }

    public static List<ItemsEntity> getAllByLocId(Integer locId) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM ItemsEntity a where a.locationsEntityId.idLocation = :locId";
        TypedQuery<ItemsEntity> tq = session.createQuery(query,ItemsEntity.class);
        tq.setParameter("locId",locId);
        return tq.getResultList();
    }


    public static List<ItemsEntity> getAllByRoomId(Integer roomId) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM ItemsEntity a where a.roomEntity.idRoom = :roomId";
        TypedQuery<ItemsEntity> tq = session.createQuery(query,ItemsEntity.class);
        tq.setParameter("roomId",roomId);
        return tq.getResultList();
    }

    public static void updateGroup(Integer rowId, GroupsEntity groupsEntity){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("UPDATE ItemsEntity set groupsEntity =:groupId WHERE rowId = :rowId");
        query.setParameter("groupId",groupsEntity);
        query.setParameter("rowId",rowId);
        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    public static void updateAmortization(Integer rowId, Float amortization){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("UPDATE ItemsEntity set amortyzationValue =:amortization WHERE rowId = :rowId");
        query.setParameter("amortization",amortization);
        query.setParameter("rowId",rowId);
        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }






}
