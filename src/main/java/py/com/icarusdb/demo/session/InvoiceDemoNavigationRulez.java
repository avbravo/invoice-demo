/**
 * 
 */
package py.com.icarusdb.demo.session;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import py.com.icarusdb.demo.util.NavigationRulezHelper;

/**
 * @author mcrose
 *
 */
@Named(value="navigationRulez")
@RequestScoped
public class InvoiceDemoNavigationRulez extends NavigationRulez
{
    
    public String goAddInvoice()
    {
        return "/invoice/add-invoice.xhtml"+NavigationRulezHelper.FACES_REDIRECT;
    }
    
    public String goPendingInvoices()
    {
        return "/invoice/pendingInvoices.xhtml"+NavigationRulezHelper.FACES_REDIRECT;
    }
    
}
