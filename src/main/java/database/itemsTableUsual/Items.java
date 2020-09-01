package database.itemsTableUsual;

import database.invoicesTable.InvoicesEntity;
import database.usersTable.UsersEntity;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.List;

public class Items {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");


    public static List<ItemsEntity> getAllFromItems() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        return session.createQuery("SELECT a FROM ItemsEntity a", ItemsEntity.class).getResultList();
    }


    public static void insert(ItemsEntity itemsEntity){

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createNativeQuery("Insert INTO items_usual_user(Item_ID,Item_Name,Invoice_Number,NetValue,GrossValue,Item_Image) VALUES (:a,:b,:c,:d,:e,:f)")
                .setParameter("a",itemsEntity.getId())
                .setParameter("b",itemsEntity.getItemName())
                .setParameter("c",itemsEntity.getInvoiceNumber())
                .setParameter("d",itemsEntity.getNetValue())
                .setParameter("e",itemsEntity.getGrossValue())
                .setParameter("f",itemsEntity.getItemImage())
                .executeUpdate();
        et.commit();
        em.close();

    }

    public static List<ItemsEntity> getAllByInvNumber(String invNumber) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "SELECT a FROM ItemsEntity a where a.invoiceNumber = :invNumber";
        TypedQuery<ItemsEntity> tq = session.createQuery(query,ItemsEntity.class);
        tq.setParameter("invNumber",invNumber);
        return tq.getResultList();
    }

    public static byte[] getByteArrayImage(String invNumber, String itemId){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT a FROM ItemsEntity a where a.invoiceNumber = :invNumber and a.id =:itemId";
        TypedQuery<ItemsEntity> tq = em.createQuery(query,ItemsEntity.class);
        tq.setParameter("invNumber",invNumber);
        tq.setParameter("itemId",itemId);
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


    public static void update (String newId,String itemName,Float netValue, Float grossValue, byte[] itemImage, String invNumber,String id){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LoginDatabase");
        EntityManager em=emf.createEntityManager();

        Session session = em.unwrap(Session.class);
        Query query = session.createQuery("UPDATE ItemsEntity set id = :newId, itemName =:itemName, netValue=:netValue, grossValue=:grossValue, itemImage =:itemImage " +
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
        Query query = session.createQuery("delete from ItemsEntity where id=:id AND invoiceNumber = :invNumber");
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
