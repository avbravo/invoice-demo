package py.com.icarusdb.demo.invoice.model;

// Generated Feb 8, 2014 3:17:30 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import py.com.icarusdb.entity.EntityInterface;
import py.com.icarusdb.util.IDBProperties;

/**
 * Invoice generated by hbm2java
 */
@Entity
@Table(name = "invoice", schema = "public")
public class Invoice implements EntityInterface
{
    /**
     * 
     */
    private static final long serialVersionUID = 7642729188313253804L;
    
    private Long id;
    private Supplier supplier;
    private Date invoiceDate;
    private String number;
    private BigDecimal totalAmount;
    private BigDecimal totalIva05;
    private BigDecimal totalIva10;
    private BigDecimal totalExempt;
    private Set<InvoiceDetail> invoiceDetails = new HashSet<InvoiceDetail>(0);

    public Invoice() {}
    
    @Id
    @GeneratedValue
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
    @JoinColumn(name = "supplier", nullable = false)
    public Supplier getSupplier()
    {
        return this.supplier;
    }

    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "invoice_date", nullable = false, length = 13)
    public Date getInvoiceDate()
    {
        return this.invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate)
    {
        this.invoiceDate = invoiceDate;
    }

    @Column(name = "number", nullable = false, length = 50)
    public String getNumber()
    {
        return this.number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    @Column(name = "total_amount", nullable = false, precision = 8)
    public BigDecimal getTotalAmount()
    {
        return this.totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    @Column(name = "total_iva05", nullable = false, precision = 8)
    public BigDecimal getTotalIva05()
    {
        return this.totalIva05;
    }

    public void setTotalIva05(BigDecimal totalIva05)
    {
        this.totalIva05 = totalIva05;
    }

    @Column(name = "total_iva10", nullable = false, precision = 8)
    public BigDecimal getTotalIva10()
    {
        return this.totalIva10;
    }

    public void setTotalIva10(BigDecimal totalIva10)
    {
        this.totalIva10 = totalIva10;
    }

    @Column(name = "total_exempt", nullable = false, precision = 8)
    public BigDecimal getTotalExempt()
    {
        return this.totalExempt;
    }

    public void setTotalExempt(BigDecimal totalExempt)
    {
        this.totalExempt = totalExempt;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
    public Set<InvoiceDetail> getInvoiceDetails()
    {
        return this.invoiceDetails;
    }

    public void setInvoiceDetails(Set<InvoiceDetail> invoiceDetails)
    {
        this.invoiceDetails = invoiceDetails;
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
