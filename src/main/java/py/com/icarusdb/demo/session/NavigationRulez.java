/**
 * 
 */
package py.com.icarusdb.demo.session;

import py.com.icarusdb.demo.util.AppHelper;
import py.com.icarusdb.demo.util.NavigationRulezHelper;

/**
 * @author rgamarra
 *
 */
public abstract class NavigationRulez
{
    public String getModuleUri()
    {
        return AppHelper.getDomainUrl();
    }
    
    public String goRoot()
    {
        return NavigationRulezHelper.ROOT;
    }
    
    /**
     * for redirecting use
     * @return
     */
    public String goIndex()
    {
        return AppHelper.getDomainUrl() + NavigationRulezHelper.INDEX_JSF;
    }
    
//    public String goHome()
//    {
//        return NavigationRulezHelper.HOME;
//    }
    
}
