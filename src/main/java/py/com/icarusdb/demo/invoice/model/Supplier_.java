package py.com.icarusdb.demo.invoice.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-17T15:22:51.927-0600")
@StaticMetamodel(Supplier.class)
public class Supplier_ {
	public static volatile SingularAttribute<Supplier, Long> id;
	public static volatile SingularAttribute<Supplier, String> name;
	public static volatile SingularAttribute<Supplier, String> ruc;
	public static volatile SingularAttribute<Supplier, String> address;
	public static volatile SingularAttribute<Supplier, String> telephone;
	public static volatile SingularAttribute<Supplier, String> contactName;
	public static volatile SetAttribute<Supplier, Invoice> invoices;
}
