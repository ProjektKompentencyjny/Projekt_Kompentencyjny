package database.itemsTableUsual;

import org.hibernate.Session;

import javax.persistence.*;
import java.util.List;

public class ItemsUsual {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");


    public static List<ItemsUsualEntity> getAllFromItems() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        return session.createQuery("SELECT a FROM ItemsUsualEntity a", ItemsUsualEntity.class).getResultList();
    }


    public static void insert(ItemsUsualEntity itemsUsualEntity){

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createNativeQuery("Insert INTO items_usual_user(Item_ID,Item_Name,Invoice_Number,NetValue,GrossValue,Item_Image) VALUES (:a,:b,:c,:d,:e,:f)")
                .setParameter("a", itemsUsualEntity.getId())
                .setParameter("b", itemsUsualEntity.getItemName())
                .setParameter("c", itemsUsualEntity.getInvoiceNumber())
                .setParameter("d", itemsUsualEntity.getNetValue())
                .setParameter("e", itemsUsualEntity.getGrossValue())
                .setParameter("f", itemsUsualEntity.getItemImage())
                .executeUpdate();
        et.commit();
        em.close();

    }

    public static List<ItemsUsualEntity> getAllByInvNumber(String invNumber) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM ItemsUsualEntity a where a.invoiceNumber = :invNumber";
        TypedQuery<ItemsUsualEntity> tq = session.createQuery(query, ItemsUsualEntity.class);
        tq.setParameter("invNumber",invNumber);
        return tq.getResultList();
    }

    public static byte[] getByteArrayImage(String invNumber, String itemId){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT a FROM ItemsUsualEntity a where a.invoiceNumber = :invNumber and a.id =:itemId";
        TypedQuery<ItemsUsualEntity> tq = em.createQuery(query, ItemsUsualEntity.class);
        tq.setParameter("invNumber",invNumber);
        tq.setParameter("itemId",itemId);
        ItemsUsualEntity itemsUsualEntity = null;
        byte[] image = null;
        try {
            itemsUsualEntity =tq.getSingleResult();
            image= itemsUsualEntity.getItemImage();
            return image;
        }catch (NoResultException ex){
            return image;
        }finally {
            em.close();
        }

    }


    public static void update (String newId,String itemName,Float netValue, Float grossValue, byte[] itemImage, String invNumber,String id){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("UPDATE ItemsUsualEntity set id = :newId, itemName =:itemName, netValue=:netValue, grossValue=:grossValue, itemImage =:itemImage " +
                " WHERE id = :id and invoiceNumber = :invoiceNumber");
        query.setParameter("newId", newId);
        query.setParameter("itemName", itemName);
        query.setParameter("netValue", netValue);
        query.setParameter("grossValue", grossValue);
        query.setParameter("itemImage",itemImage);
        query.setParameter("invoiceNumber",invNumber);
        query.setParameter("id",id);

        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        /*
        if(executeUpdate>0)
            System.out.println("Employee email is updated..");
        */
        session.close();


    }

    public static void deletefromInvoice(String id, String invNumber){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("delete from ItemsUsualEntity where id=:id AND invoiceNumber = :invNumber");
        query.setParameter("id",id);
        query.setParameter("invNumber",invNumber);

        session.beginTransaction();
        int executeUpdate = query.executeUpdate();
        session.getTransaction().commit();
        /*
        if(executeUpdate>0)
            System.out.println("Employee email is updated..");
        */
        session.close();

    }



}
