/**
 * 
 */
package py.com.icarusdb.common;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author mcrose
 *
 */
public interface EntityInterface extends Serializable
{
    public Serializable getId();
    public Properties getProperties();
//    public void setProperties(Properties properties);

    
}
