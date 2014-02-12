/**
 * 
 */
package py.com.icarusdb.demo.session;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import py.com.icarusdb.demo.util.AppHelper;
import py.com.icarusdb.demo.util.NavigationRulezHelper;

/**
 * @author rgamarra
 *
 */
@Named
@RequestScoped
public class NavigationRulez
{
    public String getModuleUri()
    {
        return AppHelper.getDomainUrl();
    }
    
    public String goRoot()
    {
        return NavigationRulezHelper.ROOT;
    }
    
    public String goHome()
    {
        return NavigationRulezHelper.HOME;
    }
    
    public String goDepositoEdit()
    {
        return "/admin/deposito.xhtml"+NavigationRulezHelper.FACES_REDIRECT;
    }
    
    
}
