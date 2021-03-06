package py.com.icarusdb.demo.invoice.model;

// Generated Feb 8, 2014 3:17:30 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import py.com.icarusdb.common.EntityInterface;
import py.com.icarusdb.common.IDBProperties;

/**
 * InvoiceDetail generated by hbm2java
 */
@Entity
@Table(name = "invoice_detail", schema = "public")
public class InvoiceDetail implements EntityInterface
{
    /**
     * 
     */
    private static final long serialVersionUID = -8113514289951928392L;

    private static final String GENERATOR = "INVOICEDETAILID_ID_GENERATOR";

    private Long id;
    private Invoice invoice;
    private Item item;
    private int amount;
    private BigDecimal unitPrice;
    private BigDecimal iva05;
    private BigDecimal iva10;
    private BigDecimal exempt;

    public InvoiceDetail() 
    {
        this.amount = 1;
    }
    
    public InvoiceDetail(Invoice invoice) 
    {
        this.invoice = invoice;
        this.amount = 1;
    }
    

    @Id
    @SequenceGenerator(name = GENERATOR, sequenceName = "public.invoice_detail_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice", nullable = false, insertable = true, updatable = true)
    public Invoice getInvoice()
    {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice)
    {
        this.invoice = invoice;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item", nullable = false)
    public Item getItem()
    {
        return this.item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    @Column(name = "amount", nullable = false)
    public int getAmount()
    {
        return this.amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    @Column(name = "unit_price", nullable = false, precision = 8)
    public BigDecimal getUnitPrice()
    {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    @Column(name = "iva_05", precision = 8)
    public BigDecimal getIva05()
    {
        return this.iva05;
    }

    public void setIva05(BigDecimal iva05)
    {
        this.iva05 = iva05;
    }

    @Column(name = "iva_10", precision = 8)
    public BigDecimal getIva10()
    {
        return this.iva10;
    }

    public void setIva10(BigDecimal iva10)
    {
        this.iva10 = iva10;
    }

    @Column(name = "exempt", precision = 8)
    public BigDecimal getExempt()
    {
        return this.exempt;
    }

    public void setExempt(BigDecimal exempt)
    {
        this.exempt = exempt;
    }

    @Transient
    @Override
    public Properties getProperties()
    {
        Properties properties = new IDBProperties();
        properties.put("id", id);
        
        return properties;
    }

}
