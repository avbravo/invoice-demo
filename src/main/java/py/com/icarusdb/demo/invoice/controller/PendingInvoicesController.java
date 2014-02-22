/**
 * 
 */
package py.com.icarusdb.demo.invoice.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.event.SelectEvent;

import py.com.icarusdb.demo.invoice.data.DatabaseManager;
import py.com.icarusdb.demo.invoice.model.Invoice;
import py.com.icarusdb.demo.session.ContextHelper;
import py.com.icarusdb.demo.session.InvoiceDemoNavigationRulez;
import py.com.icarusdb.demo.util.AppHelper;
import py.com.icarusdb.demo.util.BaseController;
import py.com.icarusdb.demo.util.ListController;
import py.com.icarusdb.demo.util.MessageUtil;
import py.com.icarusdb.demo.util.NavigationRulezHelper;
import py.com.icarusdb.entity.EntityInterface;
import py.com.icarusdb.util.CalendarHelper;

/**
 * @author mcrose
 * 
 */
@ViewScoped
@ManagedBean
@RolesAllowed({"supermaster", "icarus", "luchobenitez"})
public class PendingInvoicesController extends BaseController implements ListController, Serializable
{
    
    @Inject 
    private ContextHelper contextHelper;
    
    @Inject 
    private InvoiceDemoNavigationRulez navigationRulez;
    
    @Inject 
    private DatabaseManager manager;
    
    
    
    
    private final static String ejbql = "select o from Invoice o";
    
    private Date fromDate = null;
    private Date tillDate = null;



    private List<Invoice> resultList = null;

    private boolean processable = false;
    
    
    @Override
    @PostConstruct
    public void init()
    {
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
        fromDate = null;
        tillDate = null;
        
        resultList = new LinkedList<Invoice>();
        
        processable = false;
    }

    public void clear()
    {
        contextHelper.clearAction();
    }

    
    public void handleFromDateSelect(SelectEvent event)
    {
        tillDate = (Date) event.getObject();
    }
    
    public void handleTillDateSelect(SelectEvent event)
    {
        Date date = (Date) event.getObject();
        
        if(fromDate==null) 
        {
            MessageUtil.addFacesMessageWarm("error.date.from.mustBeDefined");
        }
        else if(fromDate.compareTo(date) > 0)
        {
            MessageUtil.addFacesMessageWarm("error.date.from.gt.till");
        }
        
    }

    public Date getFromDate()
    {
        return fromDate;
    }
    
    public void setFromDate(Date fromDate)
    {
        this.fromDate = fromDate;
    }
    
    public Date getTillDate()
    {
        return tillDate;
    }
    
    public void setTillDate(Date tillDate)
    {
        this.tillDate = tillDate;
    }

    @Override
    public EntityInterface getSelectedRow()
    {
        return null;
    }
    
    public List<Invoice> getResultList()
    {
        return resultList;
    }
    
    public boolean isProcessable()
    {
        return processable;
    }
    
    
    

    @Override
    public void search()
    {
        String qry = ejbql + " where o.processed is false";
        
        if(fromDate != null && tillDate != null) 
        {
            String fromdates = CalendarHelper.getPostgreSQL_toDate(fromDate);
            String tilldates = CalendarHelper.getPostgreSQL_toDate(tillDate);
            qry += " and o.invoiceDate between " + fromdates + " and " + tilldates;
        }
        else if (fromDate != null)
        {
            String fromdates = CalendarHelper.getPostgreSQL_toDate(fromDate);
            qry = " and o.invoiceDate >" + fromdates;
        }
        
        resultList = em.createQuery(qry, Invoice.class).getResultList();
        
        MessageUtil.showResults(resultList);
        
        processable  = false;
    }

    @Override
    public void print()
    {
        processable = true;
    }
    
    public void selectListener(AjaxBehaviorEvent event)
    {
        System.out.println("source attrs");

        SelectBooleanCheckbox source = (SelectBooleanCheckbox) event.getSource();
        Iterator<String> iter = source.getAttributes().keySet().iterator();
        while (iter.hasNext())
        {
            String key = iter.next();
            Object object = source.getAttributes().get(key);
            
            System.out.println("key: " + key + " value: " + object);
        }
        
        System.out.println(".");
        System.out.println("component attrs");
        Iterator<String> iterator = event.getComponent().getAttributes().keySet().iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            Object object = event.getComponent().getAttributes().get(key);
            
            System.out.println("key: " + key + " value: " + object);
        }
    }
    
    public void processInvoices()
    {
        try
        {
            for(Invoice invoice : resultList)
            {
                invoice.setProcessed(true);
                manager.update(invoice);
            }

            MessageUtil.addFacesMessageInfo("action.result.invoices.processed");
            
            initVarz();
            
        }
        catch (Exception e)
        {
            AppHelper.printException(e);
        }
        
    }

    
    
    
}
