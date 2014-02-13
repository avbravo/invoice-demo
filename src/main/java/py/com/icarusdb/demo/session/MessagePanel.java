/**
 * 
 */
package py.com.icarusdb.demo.session;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.CloseEvent;

import py.com.icarusdb.demo.util.AppHelper;

/**
 * @author mcrose
 *
 */
@Named
@SessionScoped
public class MessagePanel implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 750339376783099556L;
    
    private String closed = null;
    private String systemFailureMessage = null;
    
    private String message = null;

    @PostConstruct
    public void init()
    {
        closed = AppHelper.getBundleMessage("label.closed");
        systemFailureMessage = AppHelper.getBundleMessage("error.system.wtf");
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public boolean isDisplay()
    {
        return message != null;
    }
    
    public void handleClose(CloseEvent event)
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,   
            event.getComponent().getId() + " " + closed, systemFailureMessage);  
          
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        message = null;
    }

}
