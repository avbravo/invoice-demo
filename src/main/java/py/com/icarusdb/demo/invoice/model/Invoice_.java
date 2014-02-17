package py.com.icarusdb.demo.invoice.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-17T15:22:51.454-0600")
@StaticMetamodel(Invoice.class)
public class Invoice_ {
	public static volatile SingularAttribute<Invoice, Long> id;
	public static volatile SingularAttribute<Invoice, Supplier> supplier;
	public static volatile SingularAttribute<Invoice, Date> invoiceDate;
	public static volatile SingularAttribute<Invoice, String> number;
	public static volatile SingularAttribute<Invoice, BigDecimal> totalAmount;
	public static volatile SingularAttribute<Invoice, BigDecimal> totalIva05;
	public static volatile SingularAttribute<Invoice, BigDecimal> totalIva10;
	public static volatile SingularAttribute<Invoice, BigDecimal> totalExempt;
	public static volatile SingularAttribute<Invoice, Boolean> processed;
	public static volatile SetAttribute<Invoice, InvoiceDetail> invoiceDetails;
}
