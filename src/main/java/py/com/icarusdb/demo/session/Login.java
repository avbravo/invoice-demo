package py.com.icarusdb.demo.session;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import py.com.icarusdb.demo.util.AppHelper;
import py.com.icarusdb.demo.util.NavigationRulezHelper;

@SessionScoped
@ManagedBean
public class Login implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 2603024995885046681L;
    
    @Inject
    private Credentials credentials;

    public String login()
    {
        credentials.setIpAddr(AppHelper.getServletRequest().getRemoteAddr());

        if (credentials.getUsername() == null ||
            credentials.getUsername().isEmpty() ||
            credentials.getPassword() == null ||
            credentials.getPassword().isEmpty()
        )
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error with login info"));
            credentials.init();
            return null;
        }

        return "loggedIn";
    }

    @Produces
    @Named
    public String loggedUserFullInfo()
    {
        return credentials.getUsername() + "::" + credentials.getIpAddr();
    }

    public String logout()
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Goodbye, " + credentials.getUsername()));

        credentials.init();

        return NavigationRulezHelper.ROOT;
    }

    @Produces
    @Named
    public boolean isLoggedIn()
    {
        return credentials != null && credentials.getUsername() != null;
    }

}