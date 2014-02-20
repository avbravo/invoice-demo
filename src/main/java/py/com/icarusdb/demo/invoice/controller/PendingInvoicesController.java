/**
 * 
 */
package py.com.icarusdb.demo.invoice.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

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
    
    
    private final static String ejbql = "select o from Invoice o";
    
    private Date fromDate = null;
    private Date tillDate = null;



    private List<Invoice> resultList = null;
    
    
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

    @Override
    public void search()
    {
        String qry = ejbql + "where o.processed is false";
        
        if(fromDate != null && tillDate != null) 
        {
            String fromdates = CalendarHelper.getPostgreSQL_toDate(fromDate);
            String tilldates = CalendarHelper.getPostgreSQL_toDate(tillDate);
            qry = "and o.invoiceDate between " + fromdates + " and " + tilldates;
        }
        else if (fromDate != null)
        {
            String fromdates = CalendarHelper.getPostgreSQL_toDate(fromDate);
            qry = "and o.invoiceDate >" + fromdates;
        }
        
        resultList = em.createQuery(qry, Invoice.class).getResultList();
        
        MessageUtil.showResults(resultList);
    }

    @Override
    public void print()
    {
        // TODO Auto-generated method stub
        
    }
    
    

    
    
    
}
