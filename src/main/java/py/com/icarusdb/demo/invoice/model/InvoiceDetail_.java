package py.com.icarusdb.demo.invoice.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-17T15:58:44.180-0600")
@StaticMetamodel(InvoiceDetail.class)
public class InvoiceDetail_ {
	public static volatile SingularAttribute<InvoiceDetail, Long> id;
	public static volatile SingularAttribute<InvoiceDetail, Invoice> invoice;
	public static volatile SingularAttribute<InvoiceDetail, Item> item;
	public static volatile SingularAttribute<InvoiceDetail, Integer> amount;
	public static volatile SingularAttribute<InvoiceDetail, BigDecimal> unitPrice;
	public static volatile SingularAttribute<InvoiceDetail, BigDecimal> iva05;
	public static volatile SingularAttribute<InvoiceDetail, BigDecimal> iva10;
	public static volatile SingularAttribute<InvoiceDetail, BigDecimal> exempt;
}
