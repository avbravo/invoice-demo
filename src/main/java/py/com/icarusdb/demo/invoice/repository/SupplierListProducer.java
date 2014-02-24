package py.com.icarusdb.demo.invoice.repository;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import py.com.icarusdb.demo.invoice.model.Supplier;

@ApplicationScoped
public class SupplierListProducer
{
    @Inject
    private EntityManager em;


    private List<Supplier> suppliers = null;

    
    
    @Produces
    @Named
    public List<Supplier> getSuppliers()
    {
        return suppliers;
    }

    public void onSupplierListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Supplier supplier)
    {
        retrieveAllSuppliersOrderedByName();
    }
    

    @PostConstruct
    public void retrieveAllSuppliersOrderedByName()
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Supplier> criteria = cb.createQuery(Supplier.class);
        Root<Supplier> supplier = criteria.from(Supplier.class);

        criteria.select(supplier)
//                .where(
//                        cb.equal(supplier.get(Supplier_.name.getName()), true)
//                )
                .orderBy(
                        cb.asc(supplier.get("name"))
                );
        
        suppliers = em.createQuery(criteria).getResultList();
    }
    
    
}
