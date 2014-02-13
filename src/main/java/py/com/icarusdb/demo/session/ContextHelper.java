/**
 * 
 */
package py.com.icarusdb.demo.session;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author mcrose
 *
 */
@Named
@SessionScoped
public class ContextHelper implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -554165699620813056L;
    
    private String selectedMenu = null;
    private String selectedAction = null;
    private Serializable selectedEntityId = null;


    
    
    public String getSelectedMenu()
    {
        return selectedMenu;
    }
    
    public void setSelectedMenu(String selectedMenu)
    {
        this.selectedMenu = selectedMenu;
    }
    
    public String getSelectedAction()
    {
        return selectedAction;
    }
    
    public void setSelectedAction(String selectedAction)
    {
        this.selectedAction = selectedAction;
    }
    
    public Serializable getSelectedEntityId()
    {
        return selectedEntityId;
    }
    
    public void setSelectedEntityId(Serializable id)
    {
        this.selectedEntityId = id;
    }
    
    public boolean containsMenuAction(String key)
    {
        return key.equalsIgnoreCase(selectedMenu);
    }

    public void clearAction()
    {
        selectedMenu = null;
        selectedAction = null;
        selectedEntityId = null;
    }

    
    
}
