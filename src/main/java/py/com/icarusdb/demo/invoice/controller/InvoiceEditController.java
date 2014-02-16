/**
 * 
 */
package py.com.icarusdb.demo.invoice.controller;

import java.io.Serializable;
import java.math.BigDecimal;
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

import py.com.icarusdb.demo.invoice.controller.panel.ItemSearchPanel;
import py.com.icarusdb.demo.invoice.controller.panel.SupplierSearchPanel;
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
    
    @Inject 
    private SupplierSearchPanel supplierSearchPanel;
    
    @Inject
    private ItemSearchPanel itemSearchPanel;
    
    
    private Invoice selectedRow = null;
    
    private Supplier supplier = null;
    
    private List<InvoiceDetail> details = null;
    private InvoiceDetail detail = null;
    
    private String tax = null; // TODO make it enum
    
//    private Item item = null;
    
    
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
        
        supplierSearchPanel.clear();
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
    
    /**
     * added because we will use it to persist new suppliers
     * normally these panels don't have the setter and save functionally
     * maybe they will sometime soon
     * @param selectedRow
     */
    public void setSelectedRow(Invoice selectedRow)
    {
        this.selectedRow = selectedRow;
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
    
//    public Item getItem()
//    {
//        return item;
//    }
//    
//    public void setItem(Item item)
//    {
//        this.item = item;
//    }
    
    
    

    @Override
    public String save()
    {
        String result = null;
        
        try
        {
            if(!check()) return null;
            
            selectedRow.setNumber(selectedRow.getNumber().trim());
            selectedRow.setSupplier(supplierSearchPanel.getSelectedEntity());

            calculateTotals();
            
            selectedRow.getInvoiceDetails().clear();
            selectedRow.getInvoiceDetails().addAll(details);
            
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

    private void calculateTotals()
    {
        BigDecimal totalExempt = BigDecimal.ZERO;
        BigDecimal totalIva05 = BigDecimal.ZERO;
        BigDecimal totalIva10 = BigDecimal.ZERO;
        
        for(InvoiceDetail detail : details)
        {
            totalExempt = totalExempt.add(detail.getExempt()==null?BigDecimal.ZERO:detail.getExempt());
            totalIva05 = totalIva05.add(detail.getIva05()==null?BigDecimal.ZERO:detail.getIva05());
            totalIva10 = totalIva10.add(detail.getIva10()==null?BigDecimal.ZERO:detail.getIva10());
        }
        
        BigDecimal totalAmount = totalExempt.add(totalIva05).add(totalIva10);
        
        selectedRow.setTotalAmount(totalAmount);
        selectedRow.setTotalExempt(totalExempt);
        selectedRow.setTotalIva05(totalIva05);
        selectedRow.setTotalIva10(totalIva10);
        
    }

    private boolean check()
    {
        if(supplierSearchPanel.getSelectedEntity() == null)
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
        detail.setItem(itemSearchPanel.getSelectedEntity());
        
        BigDecimal totalprice = detail.getUnitPrice().multiply(new BigDecimal(detail.getAmount()));
        
        if("exempt".equalsIgnoreCase(tax))
        {
            detail.setExempt(totalprice);
        }
        if("iva05".equalsIgnoreCase(tax))
        {
            detail.setIva05(totalprice);
        }
        if("iva10".equalsIgnoreCase(tax))
        {
            detail.setIva10(totalprice);
        }
        
        details.add(detail);
        
        cancelAddDetail();
    }

    public void cancelAddDetail()
    {
        detail = null;
//        item = null;
        itemSearchPanel.clear();
    }

    public void delete(int index)
    {
        details.remove(index);
    }
    
    
    public void preparePanel(String panelname)
    {
        if("supplier".equalsIgnoreCase(panelname))
        {
            supplierSearchPanel.setTagId2update(":editform");
        }
        if("item".equalsIgnoreCase(panelname))
        {
            itemSearchPanel.setTagId2update(":editform");
        }
    }
    
    
    
    
    
    
}
