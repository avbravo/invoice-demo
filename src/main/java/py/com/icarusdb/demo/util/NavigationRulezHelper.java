/**
 * 
 */
package py.com.icarusdb.demo.util;

import java.io.IOException;

import javax.faces.context.FacesContext;

/**
 * @author rgamarra
 *
 */
public class NavigationRulezHelper
{

    public static final String FACES_REDIRECT = "?faces-redirect=true";
    
    public static final String ROOT = "/index.xhtml";
    
    public static final String HOME = "/home.xhtml" + FACES_REDIRECT;

    
    
    public static void redirect(String url)
    {
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
