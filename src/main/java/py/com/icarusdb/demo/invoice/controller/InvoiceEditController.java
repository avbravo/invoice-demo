/**
 * 
 */
package py.com.icarusdb.demo.invoice.controller;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import py.com.icarusdb.demo.invoice.data.DatabaseManager;
import py.com.icarusdb.demo.invoice.model.Invoice;
import py.com.icarusdb.demo.invoice.model.InvoiceDetail;
import py.com.icarusdb.demo.invoice.model.InvoiceDetailId;
import py.com.icarusdb.demo.invoice.model.Supplier;
import py.com.icarusdb.demo.session.ContextHelper;
import py.com.icarusdb.demo.session.InvoiceDemoNavigationRulez;
import py.com.icarusdb.demo.util.AppHelper;
import py.com.icarusdb.demo.util.BaseController;
import py.com.icarusdb.demo.util.EditController;
import py.com.icarusdb.demo.util.MessageUtil;
import py.com.icarusdb.demo.util.NavigationRulezHelper;
import py.com.icarusdb.demo.util.SessionParameters;

/**
 * @author mcrose
 * 
 */
@ViewScoped
@ManagedBean
@RolesAllowed({"supermaster", "icarus", "luchobenitez"})
public class InvoiceEditController extends BaseController implements EditController, Serializable
{
//    @Inject
//    private Credentials credentials;
    
    @Inject
    private DatabaseManager databaseManager;
    
    @Inject 
    private ContextHelper contextHelper;
    
    @Inject 
    private InvoiceDemoNavigationRulez navigationRulez;
    
    
    
    private Invoice selectedRow = null;
    
    private Supplier supplier = null;
    
    private List<InvoiceDetail> details = null;
    private InvoiceDetail detail = null;
    
    private String tax = null; // TODO make it enum
    
   
    
    
    @Override
    @PostConstruct
    public void init()
    {
        // TODO test cancel button
        if(!loggedIn)
        {
            MessageUtil.addFacesMessageError("logging.error.noLoggedUser");
            NavigationRulezHelper.redirect(navigationRulez.goIndex());
            return;
        }
        
        tax = null;
        
//        if(!contextHelper.containsMenuAction(SessionParameters.ACTION_MENU_INVOICE))
//        {
//            MessageUtil.addFacesMessageError("error.action.noActionDefined");
//            NavigationRulezHelper.redirect(navigationRulez.goIndex());
//            return;
//        }
        
        em = emf.createEntityManager();
        
//        action = contextHelper.getSelectedAction();
//        if(action.equalsIgnoreCase(SessionParameters.ACTION_ADD_INVOICE)) 
        {
            supplier = null;
            selectedRow = new Invoice();
            details = new LinkedList<InvoiceDetail>();
            
            actionSubTitle = AppHelper.getBundleMessage("button.label.add");
        }
//        else
//        {
//            Serializable entityId = (Serializable) contextHelper.getSelectedEntityId();
//            selectedRow = em.find(Invoice.class, entityId);
//            actionSubTitle = AppHelper.getBundleMessage("button.label.update");
//            supplier = selectedRow.getSupplier();
//        }
        
        //TODO later remove
        logger.log(Level.INFO, "init::" + this.getClass().getName());
//        logger.log(Level.INFO, "action: " + action);
        
    }
    
    public void clear()
    {
        contextHelper.clearAction();
    }

    @Override
    public Invoice getSelectedRow()
    {
        return selectedRow;
    }
    
    public InvoiceDetail getDetail()
    {
        return detail;
    }
    
    public List<InvoiceDetail> getDetails()
    {
        return details;
    }
    
    public Supplier getSupplier()
    {
        return supplier;
    }
    
    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }
    
    public String getTax()
    {
        return tax;
    }
    
    public void setTax(String impuesto)
    {
        this.tax = impuesto;
    }
    
    
    
    
    

    @Override
    public String save()
    {
        String result = null;
        
        try
        {
            if(!check()) return null;
            
            selectedRow.setNumber(selectedRow.getNumber().trim());
            selectedRow.setSupplier(supplier);
            
//            selectedRow.setUsuUltmod(credentials.getUsername());
//            selectedRow.setFecUltmod(CalendarHelper.getCurrentTimestamp());
            
            if(selectedRow.getId() == null)
            {
//                selectedRow.setUsuAlta(credentials.getUsername());
//                selectedRow.setFecAlta(CalendarHelper.getCurrentTimestamp());
//                selectedRow.setState(BasicState.ACTIVE.getValue());
                
                result = databaseManager.persist(selectedRow);
            }
            else
            {
                result = databaseManager.update(selectedRow);
            }

        }
        catch (Exception e) 
        {
            AppHelper.printException(e);
        }
        finally
        {
            if(result != null)
            {
                MessageUtil.addFacesMessageInfo("action.result.updated");
                
                return null; //stays in the same page with a fresh and clean form 
            }
        }
        
        return null;
    }

    private boolean check()
    {
        if(supplier == null)
        {
            MessageUtil.addFacesMessage("error.required.supplier");
            return false;
        }
        
        
        List<Invoice> resultList = null;
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Invoice> criteria = cb.createQuery(Invoice.class);
        Root<Invoice> entity = criteria.from(Invoice.class);

        criteria.select(entity)
                .where(
                        cb.equal(entity.get("number"), selectedRow.getNumber().trim())
                );
        
        resultList = em.createQuery(criteria).getResultList();
        if(!resultList.isEmpty())
        {
            if(selectedRow.getId() == null || 
               selectedRow.getId().intValue() != resultList.get(0).getId().intValue()) 
            {
                MessageUtil.addFacesMessage("record_already_exists");
                return false;
            }
        }
        
        return true;
    }
    
    
    public void addDetail()
    {
        detail = new InvoiceDetail(new InvoiceDetailId());
    }
    
    public void confirmAddDetail()
    {
        detail.setInvoice(selectedRow);
        details.add(detail);
        detail = null;
    }

    public void cancelAddDetail()
    {
        detail = null;
    }

    public void delete(int index)
    {
        details.remove(index);
    }
}
