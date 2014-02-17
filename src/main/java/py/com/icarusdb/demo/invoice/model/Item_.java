package py.com.icarusdb.demo.invoice.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-17T15:22:51.906-0600")
@StaticMetamodel(Item.class)
public class Item_ {
	public static volatile SingularAttribute<Item, Long> id;
	public static volatile SingularAttribute<Item, String> description;
	public static volatile SingularAttribute<Item, String> code;
	public static volatile SingularAttribute<Item, String> barcode;
	public static volatile SingularAttribute<Item, Integer> minimunStock;
	public static volatile SetAttribute<Item, InvoiceDetail> invoiceDetails;
}
