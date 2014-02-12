/**
 * 
 */
package py.com.icarusdb.demo.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author rgamarra
 *
 */
public class MessageUtil
{
    
    
    public static String retrieveMessage(String message)
    {
        String msg =  AppHelper.getBundleMessage(message);
        if(msg == null) {
            msg = message;
        }
        return msg;
    }

    /**
     * <p>add a message to {@link FacesContext}'s message with the given message</p>
     * <p>if the message represents a key stored in bundle property, will show the corresponding
     *    value store in the file. 
     * </p>
     * @param message
     * 
     */
    public static void addFacesMessage(String message)
    {
        String msg = retrieveMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public static void addFacesMessageInfo(String message)
    {
        addFacesMessageInfo(message, null);
    }
    
    public static void addFacesMessageInfo(String message, String detail)
    {
        String msg = retrieveMessage(message);
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, detail);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public static void addFacesMessageWarm(String message)
    {
        addFacesMessageWarm(message, null);
    }
    
    public static void addFacesMessageWarm(String message, String detail)
    {
        String msg = retrieveMessage(message);
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, detail);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public static void addFacesMessageError(String message)
    {
        addFacesMessageError(message, null);
    }
    
    public static void addFacesMessageError(String message, String detail)
    {
        String msg = retrieveMessage(message);
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, detail);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public static void showResults(List<?> collection)
    {
        if(collection.isEmpty()) {
            addFacesMessageWarm("search.label.noRecordsFound");
        } else {
            String message = AppHelper.getBundleMessage("search.label.recordsFound");
            addFacesMessageInfo(message + ": " + collection.size());
        }
    }
    
    
}
