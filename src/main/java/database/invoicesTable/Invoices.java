package database.invoicesTable;

import database.usersTable.UsersEntity;
import org.hibernate.Session;

import javax.persistence.*;
import java.util.List;

public class Invoices {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("LoginDatabase");



    public static List<InvoicesEntity> getAllFromInvoices() {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        return session.createQuery("SELECT a FROM InvoicesEntity a", InvoicesEntity.class).getResultList();
    }

    public static void insert(InvoicesEntity invoicesEntity){

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createNativeQuery("Insert INTO invoices(Invoice_Number,DateOfInvoicing,NetValue,GrossValue,Invoice_Image) VALUES (:a,:b,:c,:d,:e)")
                .setParameter("a",invoicesEntity.getInvoiceNumber())
                .setParameter("b",invoicesEntity.getDateOfInvoicing())
                .setParameter("c",invoicesEntity.getNetValue())
                .setParameter("d",invoicesEntity.getGrossValue())
                .setParameter("e",invoicesEntity.getInvoice_Image())
                .executeUpdate();
        et.commit();
        em.close();

    }

    public static byte[] getPdf(String invNumber){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        Session session = em.unwrap(Session.class);
        String query = "Select a From InvoicesEntity a where a.invoiceNumber =:invNumber";
        TypedQuery<InvoicesEntity> tq  = session.createQuery(query,InvoicesEntity.class);
        tq.setParameter("invNumber", invNumber);
        return tq.getSingleResult().getInvoice_Image();
    }

}
