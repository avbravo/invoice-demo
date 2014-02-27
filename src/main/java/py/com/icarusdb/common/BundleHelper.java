/**
 * 
 */
package py.com.icarusdb.common;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author mcrose
 *
 */
public class BundleHelper
{
    /**
     * <p>
     * default message bundle in IcarusDB's common-lib jar<br>
     * "py.com.icarusdb.messages.CommonMessages"<br>
     * </p>
     * @param key
     * @return message
     */
    public static String getBundleMessage(String key)
    {
        return getBundleMessage("py.com.icarusdb.messages.CommonMessages", key);
    }
    
    public static String getBundleMessage(String bundleName, String key)
    {
        return getBundleMessage(bundleName, key, Locale.getDefault());
    }
    
    /**
     * returns the requested <b>key</b> message with the given <b>bundleName</b>
     *  
     * @param bundleName
     * @param key
     * @param locale 
     * @return message
     */
    public static String getBundleMessage(String bundleName, String key, Locale locale)
    {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
            if(bundle.containsKey(key))
            {
                return bundle.getString(key);
                
            }
            /* 
             * may be is a "common" pre-defined property bundle 
             * look up for it in base common messages
             */
            if(!bundleName.contains("CommonMessages")) {
                return getBundleMessage(key);
            }
        }
        catch (Exception e)
        {
            /*nothing*/
        }
        return null;
    }
    

}
