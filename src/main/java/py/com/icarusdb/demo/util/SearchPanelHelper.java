/**
 * 
 */
package py.com.icarusdb.demo.util;

import org.primefaces.context.RequestContext;

/**
 * @author rgamarra
 *
 */
public abstract class SearchPanelHelper
{
    protected boolean edition = false;
    protected String clientId = null;
    
    /**
     * example: :searchform:supplier or :editform:supplier
     */
    protected String tagId2update = null;
    
    
    public boolean isEdition()
    {
        return edition;
    }
    
    public void setEdition(boolean edition)
    {
        this.edition = edition;
    }
    
    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getTagId2update()
    {
        return tagId2update;
    }
    
    public void setTagId2update(String tagId2update)
    {
        this.tagId2update = tagId2update;
    }
    
    
    public void processUpdateClientId()
    {
        if(clientId != null)
        {
            RequestContext.getCurrentInstance().update(clientId);
        }
    }

}
