/**
 * 
 */
package py.com.icarusdb.demo.invoice.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

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
import py.com.icarusdb.demo.invoice.model.Supplier;
import py.com.icarusdb.demo.session.ContextHelper;
import py.com.icarusdb.demo.session.Credentials;
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
    /**
     * 
     */
    private static final long serialVersionUID = -334088012473988937L;

    @SuppressWarnings("unused") //TODO remove when using audit
    @Inject
    private Credentials credentials;
    
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
        
        em = emf.createEntityManager();
        
        actionSubTitle = AppHelper.getBundleMessage("button.label.add");

        initVarz();
    }
    
    public void initVarz()
    {
        tax = "exempt"; //TODO use enum
        supplier = null;
        selectedRow = new Invoice();
        details = new LinkedList<InvoiceDetail>();
        
        supplierSearchPanel.clear();
        itemSearchPanel.clear();
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
    
    public void setTax(String tax)
    {
        this.tax = tax;
    }
    
    

    @Override
    public String save()
    {
        String result = null;
        
        try
        {
            if(!check()) return null;
            
            selectedRow.setNumber(selectedRow.getNumber().trim());
            selectedRow.setSupplier(supplierSearchPanel.getSelectedEntity());

            updateTotals();
            
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
                
                initVarz();
                
                return null; //stays in the same page with a fresh and clean form 
            }
            
            //just in case if something happens, just because this is a demo and IT MUST NOT crush ;) -Icarus
            selectedRow.setId(null);
            for(InvoiceDetail detail : selectedRow.getInvoiceDetails())
            {
                detail.setId(null);
            }
            details.clear();
            details.addAll(selectedRow.getInvoiceDetails());
        }
        
        return null;
    }

    public void updateTotals()
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
        detail = new InvoiceDetail(selectedRow);
    }
    
    public void confirmAddDetail()
    {
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
        
        updateTotals();
        
        cancelAddDetail();
    }

    public void cancelAddDetail()
    {
        detail = null;
        itemSearchPanel.clear();
    }

    public void delete(int index)
    {
        details.remove(index);
        updateTotals();
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
