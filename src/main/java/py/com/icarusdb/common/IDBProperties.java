/**
 * 
 */
package py.com.icarusdb.common;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author rgamarra
 * 
 */
public class IDBProperties extends Properties
{
    /**
     * 
     */
    private static final long serialVersionUID = 7382300638515395533L;
    
    private final static String NULL = "null";

    public IDBProperties()
    {
    }

    public IDBProperties(IDBProperties properties)
    {
        if (properties != null)
        {
            fillProperties(properties);
        }
    }

    public IDBProperties(Map<?, ?> map)
    {
        if (map != null)
        {
            fillProperties(map);
        }
    }

    private void fillProperties(IDBProperties properties)
    {
        Iterator<Object> iterator = properties.keySet().iterator();
        while (iterator.hasNext())
        {
            Object key = iterator.next();
            Object value = properties.get(key);

            super.put(key, value == null ? NULL : value);
        }
    }

    private void fillProperties(Map<?, ?> map)
    {
        Iterator<?> iterator = map.keySet().iterator();
        while (iterator.hasNext())
        {
            Object key = iterator.next();
            Object value = map.get(key);

            super.put(key, value == null ? NULL : value);
        }

    }

    @Override
    public synchronized Object get(Object key)
    {
        Object value = super.get(key);
        return (value == null || NULL.equals(value)) ? null : value;
    }

    @Override
    public synchronized Object put(Object key, Object value)
    {
        return super.put(key, value == null ? NULL : value);
    }

}
