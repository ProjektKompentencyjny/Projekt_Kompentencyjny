package database.itemsTable;

import database.itemsTableTemp.ItemsEntityTemp;
import database.itemsTableUsual.ItemsUsualEntity;
import database.locationsTable.LocationsEntity;
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





}
