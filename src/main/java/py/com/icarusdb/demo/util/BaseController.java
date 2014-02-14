/**
 * 
 */
package py.com.icarusdb.demo.util;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * @author mcrose
 *
 */
public abstract class BaseController implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -7339017014434212601L;

    @Inject
    protected boolean loggedIn; 
    
    @Inject
    protected transient Logger logger;

    @PersistenceUnit
    protected EntityManagerFactory emf;

    
    protected EntityManager em = null;

    protected String actionSubTitle = null;

    protected String action = null;


    public String getActionSubTitle()
    {
        return actionSubTitle;
    }

    public boolean isView()
    {
        return (action != null && action.contains("view"));
    }

    public void log(String name)
    {
        if(logger.isLoggable(Level.ALL) || logger.isLoggable(Level.INFO))
        {
            logger.log(Level.INFO, "init::" + this.getClass().getName());
            logger.log(Level.INFO, "action: " + action);
        }
        
//        logger.log(Level.INFO, "INFO debug enable: " + );
//        logger.log(Level.INFO, "ALL debug enable: " + logger.isLoggable(Level.ALL));
//        logger.log(Level.INFO, "WARNING debug enable: " + logger.isLoggable(Level.WARNING));
//        logger.log(Level.INFO, "SEVERE debug enable: " + logger.isLoggable(Level.SEVERE));
        
    }
    
    
}
