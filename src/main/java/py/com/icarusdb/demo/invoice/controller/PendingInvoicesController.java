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
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import py.com.icarusdb.demo.invoice.data.DatabaseManager;
import py.com.icarusdb.demo.invoice.model.Invoice;
import py.com.icarusdb.demo.session.ContextHelper;
import py.com.icarusdb.demo.session.Credentials;
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
    private Credentials credentials;

    @Inject 
    private ContextHelper contextHelper;
    
    @Inject 
    private InvoiceDemoNavigationRulez navigationRulez;
    
    @Inject 
    private DatabaseManager manager;
    
    @Inject 
    private ReportController reportController;
    
    
    
    
    private final static String ejbql = "select o from Invoice o";
    
    private Date fromDate = null;
    private Date tillDate = null;



    private List<Invoice> resultList = null;

    private boolean processable = false;
    public int selectedInvoices = 0;
    
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
    
    private void initVarz()
    {
        fromDate = null;
        tillDate = null;
        
        resultList = new LinkedList<Invoice>();
        
        processable = false;
        selectedInvoices = 0;
    }

    public void clear()
    {
        contextHelper.clearAction();
        em.clear();
        
        initVarz();
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
    
    public boolean isPrintable()
    {
        return (!resultList.isEmpty()) && (selectedInvoices > 0 );
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
        
        processable = false;
        selectedInvoices = 0;
    }

    @Override
    public void print()
    {
        processable = true;

        // not needed here
//        if(resultList == null || resultList.isEmpty())
//        {
//            MessageUtil.addFacesMessageWarm("error.report.emptyCollection");
//            return;
//        }
        
        reportController.init();
        
        reportController.setReportPath("/reports");
        reportController.setReportTemplateName("PendingInvoices");

        reportController.setReportName("Pending Invoices");
        reportController.addDataSourceEntityCollection(new LinkedList<EntityInterface>(resultList));
        
        reportController.addParameter("companyName" , credentials.getCompanyName());
        reportController.addParameter("fromDate" , fromDate);
        reportController.addParameter("tillDate" , tillDate);
        
        reportController.print();
        
    }
    
    public void selectListener(AjaxBehaviorEvent event)
    {
        Invoice invoice = (Invoice) event.getComponent().getAttributes().get("invoice");
        
        if(invoice.isSelected()) 
        {
            selectedInvoices++;
        }
        else
        {
            selectedInvoices--;
        }
        
    }
    
    public void processInvoices()
    {
        try
        {
            for(Invoice invoice : resultList)
            {
                if(invoice.isSelected())
                {
                    invoice.setProcessed(true);
                    manager.update(invoice);
                }
            }

            MessageUtil.addFacesMessageInfo("action.result.invoices.processed");
            
        }
        catch (Exception e)
        {
            AppHelper.printException(e);
        }
        finally
        {
            clear();
        }
        
    }

    
    
    
}
