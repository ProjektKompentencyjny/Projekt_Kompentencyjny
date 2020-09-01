package database.invoicesTable;

import org.hibernate.type.BlobType;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

@Entity
@Table(name = "invoices")
public class InvoicesEntity implements Serializable {

    public InvoicesEntity(){}

    @Id
    @Column(name = "Invoice_Number")
    private String invoiceNumber;

    @Column(name = "DateOfInvoicing")
    private Date dateOfInvoicing;

    @Column(name ="NetValue")
    private float netValue;

    @Column(name = "GrossValue")
    private float grossValue;

    @Lob
    @Column(name = "Invoice_Image")
    private byte[] invoice_Image;

    public InvoicesEntity(String invoiceNumber, Date dateOfInvoicing, float netValue, float grossValue, byte[] invoice_Image) {
        this.invoiceNumber = invoiceNumber;
        this.dateOfInvoicing = dateOfInvoicing;
        this.netValue = netValue;
        this.grossValue = grossValue;
        this.invoice_Image = invoice_Image;
    }

    public byte[] getInvoice_Image() {
        return invoice_Image;
    }

    public void setInvoice_Image(byte[] invoice_Image) {
        this.invoice_Image = invoice_Image;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getDateOfInvoicing() {
        return dateOfInvoicing;
    }

    public void setDateOfInvoicing(Date dateOfInvoicing) {
        this.dateOfInvoicing = dateOfInvoicing;
    }

    public float getNetValue() {
        return netValue;
    }

    public void setNetValue(float netValue) {
        this.netValue = netValue;
    }

    public float getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(float grossValue) {
        this.grossValue = grossValue;
    }
}
