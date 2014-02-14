package py.com.icarusdb.demo.invoice.data;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import py.com.icarusdb.demo.util.SessionParameters;
import py.com.icarusdb.entity.EntityInterface;

@Named
@RequestScoped
public class DatabaseManager implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -966023395247001559L;

    @Inject
    protected transient Logger logger;

    @Inject
    protected EntityManager em;

    @Inject
    protected UserTransaction utx;

    
    public String getEntityInfo(EntityInterface entity)
    {
        String info = entity.getClass().getName() +"::"+  
                      entity.getId() +"::"+ 
                      entity.getProperties().get(SessionParameters.COMBOBOX_DESCRIPTOR);
        return info;
    }
    
    /**
     * <p>
     *   persists the given entity facade to the database
     *   </p>
     * <p>
     *    entities MUST implement {@link EntityInterface}<br>
     *    and have two properties defined in getProperties() method:<br>
     *    Properties properties = new PTIProperties();<br>
     *    properties.put("id", getId());<br>
     *    properties.put({@link SessionParameters}.COMBOBOX_DESCRIPTOR, field);<br>
     * </p>
     * @param entity
     * @return String "persisted"
     * @throws Exception
     */
    public String persist(EntityInterface entity) throws Exception
    {
        try
        {
            try
            {
                utx.begin();
                em.persist(entity);
                
                logger.info("Added " + getEntityInfo(entity));
            }
            finally
            {
                utx.commit();
            }
        }
        catch (Exception e)
        {
            try
            {
                utx.rollback();
            }
            catch (Exception ee)
            {
                ee.printStackTrace();
            }
            throw e;
        }
        
        return "persisted";
    }



    /**
     * <p>
     *   updates the given entity facade to the database
     *   </p>
     * <p>
     *    entities MUST implement {@link EntityInterface}<br>
     *    and have two properties defined in getProperties() method:<br>
     *    Properties properties = new PTIProperties();<br>
     *    properties.put("id", getId());<br>
     *    properties.put({@link SessionParameters}.COMBOBOX_DESCRIPTOR, field);<br>
     * </p>
     * @param entity
     * @return "persisted"
     * @throws Exception
     */
    public String update(EntityInterface entity) throws Exception
    {
        try
        {
            try
            {
                utx.begin();
                em.merge(entity);
                
                logger.info("Updated " + getEntityInfo(entity));
            }
            finally
            {
                utx.commit();
            }
        }
        catch (Exception e)
        {
            try
            {
                utx.rollback();
            }
            catch (Exception ee)
            {
                ee.printStackTrace();
            }
            throw e;
        }
        
        return "updated";
    }

    
    /**
     * marks as deleted the given entity instance
     * 
     * <p>
     *    entities MUST implement {@link EntityInterface}<br>
     *    and have two properties defined in getProperties() method:<br>
     *    Properties properties = new PTIProperties();<br>
     *    properties.put("id", getId());<br>
     *    properties.put({@link SessionParameters}.COMBOBOX_DESCRIPTOR, field);<br>
     * </p>
     * @param entity
     * @return
     * @throws Exception
     */
    public String delete(EntityInterface entity, String propertyKeyName) throws Exception
    {
        try
        {
            try
            {
                //TODO add code to update audit data
                //FIXME not finished
                utx.begin();
                if(!em.contains(entity)) {
                    em.merge(entity);
                }
                
                logger.info("Deleted " + getEntityInfo(entity));
            }
            finally
            {
                utx.commit();
            }
        }
        catch (Exception e)
        {
            try
            {
                utx.rollback();
            }
            catch (Exception ee)
            {
                ee.printStackTrace();
            }
            throw e;
        }
        
        return "deleted";
    }


    /**
     * removes physically (from the database) the given entity instance
     * 
     * <p>
     *    entities MUST implement {@link EntityInterface}<br>
     *    and have two properties defined in getProperties() method:<br>
     *    Properties properties = new PTIProperties();<br>
     *    properties.put("id", getId());<br>
     *    properties.put({@link SessionParameters}.COMBOBOX_DESCRIPTOR, field);<br>
     * </p>
     * @param entity
     * @return String "removed"
     * @throws Exception
     */
    public String remove(EntityInterface entity) throws Exception
    {
        try
        {
            try
            {
                utx.begin();
                if(!em.contains(entity)) {
                    entity = em.merge(entity);
                }
                
                em.remove(entity);
                
                logger.info("Removed " + getEntityInfo(entity));
            }
            finally
            {
                utx.commit();
            }
        }
        catch (Exception e)
        {
            try
            {
                utx.rollback();
            }
            catch (Exception ee)
            {
                ee.printStackTrace();
            }
            throw e;
        }
        
        return "removed";
    }
    
    public String remove(List<EntityInterface> deleteCollection) throws Exception
    {
        for(EntityInterface ei : deleteCollection) 
        {
            remove(ei);
        }
        return "removed";
    }

    

}
