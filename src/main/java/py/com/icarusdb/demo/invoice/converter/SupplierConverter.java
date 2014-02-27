/**
 * 
 */
package py.com.icarusdb.demo.invoice.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import py.com.icarusdb.common.EntityConverter;
import py.com.icarusdb.demo.invoice.model.Supplier;
import py.com.icarusdb.demo.util.SessionParameters;

/**
 * @author mcrose
 *
 */
@FacesConverter(forClass=Supplier.class)
public class SupplierConverter extends EntityConverter implements Converter
{
    
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if(value == null || value.isEmpty())
        {
            return null;
        }
        
        return getViewMap(context).get(value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object object)
    {
        if(object == null) return null;
        
        try
        {
            Supplier entity = (Supplier) object;
            Long id = entity.getId();
            if (id != null)
            {
                getViewMap(context).put(id.toString(), object);
                
                return id.toString();
            }
        }
        catch (ClassCastException cce)
        {
            FacesMessage errMsg = new FacesMessage(SessionParameters.CONVERSION_ERROR_MESSAGE_ID);
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }        
        
        return null;
        
    }

}
