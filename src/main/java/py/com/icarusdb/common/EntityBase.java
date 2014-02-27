/**
 * 
 */
package py.com.icarusdb.common;

import javax.persistence.Transient;


/**
 * @author mcrose
 * 
 */
public abstract class EntityBase
{

    protected boolean newElement = false;
    protected boolean selected = false;
    protected boolean hidden = false;

    
    @Transient
    public boolean isNewElement()
    {
        return newElement;
    }
    
    public void setNewElement(boolean newElement)
    {
        this.newElement = newElement;
    }
    
    @Transient
    public boolean isSelected() 
    {
        return selected;
    }
    
    public void setSelected(boolean selected) 
    {
        this.selected = selected;
    }
    
    @Transient
    public boolean isHidden()
    {
        return hidden ;
    }
    
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    
    
}
