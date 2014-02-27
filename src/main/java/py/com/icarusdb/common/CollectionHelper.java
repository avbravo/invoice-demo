/**
 * 
 */
package py.com.icarusdb.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author rgamarra
 * 
 */
public class CollectionHelper
{
    public static final int CHECK_FROM_START = 0;
    public static final int CHECK_FROM_END = 1;
    public static final int CHECK_ANY = 2;

    public enum CheckFrom
    {
        CHECK_FROM_START(0), CHECK_FROM_END(1), CHECK_ANY(2);

        private int value;

        private CheckFrom(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }

    }

    /**
     * returns an ordered collection
     * 
     * @param collection
     * @return List ordered
     */
    private static List<?> orderCollection(Collection<?> collection, String key, boolean order)
    {
        Comparator<Object> comparator = new IDBComparator(key, order);

        List<?> list = new ArrayList<Object>(collection);

        Collections.sort(list, comparator);

        return list;
    }

    /* collections */

    public static Collection<?> order(Collection<?> collection)
    {
        return orderCollection(collection, null, true);
    }

    public static Collection<?> order(Collection<?> collection, boolean order)
    {
        return orderCollection(collection, null, order);
    }

    public static Collection<?> order(Collection<?> collection, String key)
    {
        return orderCollection(collection, key, true);
    }

    public static Collection<?> order(Collection<?> collection, String key, boolean order)
    {
        return orderCollection(collection, key, order);
    }

    /* sets */

    public static List<?> order(Set<?> set)
    {
        return orderCollection(new ArrayList<Object>(set), null, true);
    }

    public static List<?> order(Set<?> set, String key, boolean order)
    {
        return orderCollection(new ArrayList<Object>(set), key, order);
    }

    public static List<?> order(Set<?> set, String key)
    {
        return orderCollection(new ArrayList<Object>(set), key, true);
    }

    public static List<?> order(Set<?> set, boolean order)
    {
        return orderCollection(new ArrayList<Object>(set), null, order);
    }

    /* lists */

    public static List<?> order(List<?> list)
    {
        return orderCollection(new ArrayList<Object>(list), null, true);
    }

    public static List<?> order(List<?> list, String key)
    {
        return orderCollection(new ArrayList<Object>(list), key, true);
    }

    public static List<?> order(List<?> list, boolean order)
    {
        return orderCollection(new ArrayList<Object>(list), null, order);
    }

    public static List<?> order(List<?> list, String key, boolean order)
    {
        return orderCollection(new ArrayList<Object>(list), key, order);
    }

    /**
     * not tested yet
     * 
     * @param map
     * @return
     */
    public static List<Object> getMapValues(Map<Object, Object> map)
    {
        List<Object> valuemaplist = new ArrayList<Object>();

        Iterator<Object> iterator = map.keySet().iterator();
        while (iterator.hasNext())
        {
            valuemaplist.add(map.get(iterator.next()));
        }
        return valuemaplist;
    }

    /**
     * retrieves an entity bean from a collection of it with the given key
     * (entity's property) and value<br>
     * <br>
     * hint1: the entity must implements {@link EntityInterface}<br>
     * hint2: always check the returned values in getProperties()<br>
     * <br>
     * 
     * @param collection
     * @param value
     * @param propertykeyname
     * @return
     */
    public static Object getElementByPropertyName(List<?> collection, Object value, Object propertykeyname)
    {
        if ((value != null) && (collection != null)) 
        {
            String paramValue = DataConverter.getStringValue(value, false);

            for (Object current : collection)
            {
                Object objectvalue = null;
                if (current instanceof EntityInterface)
                {
                    EntityInterface beaninterface = (EntityInterface) current;
                    objectvalue = beaninterface.getProperties().get(propertykeyname);
                }
                else if (current instanceof Map<?, ?>)
                {
                    Map<?, ?> hashmap = (Map<?, ?>) current;
                    objectvalue = hashmap.get(propertykeyname);
                }

                String beanValue = DataConverter.getStringValue(objectvalue, false);
                if (paramValue.equals(beanValue))
                {
                    return current;
                }
            }
        }
        return null;
    }

    public static Object getElementByPropertyName(Set<?> collection, Object value, String key)
    {
        return getElementByPropertyName(new ArrayList<Object>(collection), value, key);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<EntityInterface> getElementsByPropertyName(Set<?> collection, Object value, String propertykeyname)
    {
        return getElementsByPropertyName(new ArrayList(collection), value, propertykeyname);
    }

    /**
     * retrieves a collection entity beans from a collection of it with the
     * given key (entity's property) and value<br>
     * <br>
     * hint1: the entity must implements {@link EntityInterface}<br>
     * hint2: always check the returned values in getProperties()<br>
     * <br>
     * 
     * @param collection
     * @param value
     * @param propertykeyname
     * @return
     */
    public static List<EntityInterface> getElementsByPropertyName(List<?> collection, Object value, String propertykeyname)
    {
        if ((value != null) && (collection != null))
        {
            List<EntityInterface> elementsfound = new ArrayList<EntityInterface>();

            String paramValue = DataConverter.getStringValue(value, false);
            for (Object current : collection)
            {
                if (current instanceof EntityInterface)
                {
                    EntityInterface beaninterface = (EntityInterface) current;
                    Object objectvalue = beaninterface.getProperties().get(propertykeyname);
                    String beanValue = DataConverter.getStringValue(objectvalue, false);
                    if (paramValue.equals(beanValue))
                    {
                        elementsfound.add(beaninterface);
                    }
                }
            }
            return elementsfound;
        }
        return null;
    }

    /**
     * 
     * retrieves a collection entity beans from a collection of it with the
     * given key (entity's property) and collection of values hint1: the entity
     * must implements {@link EntityInterface} hint2: always check the returned
     * values in getProperties()
     * 
     * @param collection
     * @param values
     * @param propertykeyname
     * @return
     */
    public static List<EntityInterface> getElementsByPropertyNames(List<?> collection, List<?> values, String propertykeyname)
    {
        if ((values != null) && (collection != null))
        {
            List<EntityInterface> elementsfound = new ArrayList<EntityInterface>();

            for (Object value : values)
            {
                String paramValue = DataConverter.getStringValue(value, false);
                for (Object current : collection)
                {
                    if (current instanceof EntityInterface)
                    {
                        EntityInterface beaninterface = (EntityInterface) current;
                        Object objectvalue = beaninterface.getProperties().get(propertykeyname);
                        String beanValue = DataConverter.getStringValue(objectvalue, false);
                        if (paramValue.equals(beanValue))
                        {
                            elementsfound.add(beaninterface);
                        }
                    }
                }
            }
            return elementsfound;
        }
        return null;
    }

    /**
     * exclude from the given collection the objects with the given key/value
     * 
     * @param collection
     * @param key
     * @param value
     * @return
     */
    public static List<EntityInterface> excludeFromList(List<?> collection, String key, String value)
    {
        List<EntityInterface> filtedred = new ArrayList<EntityInterface>();

        for (Object current : collection)
        {
            if (current instanceof EntityInterface)
            {
                EntityInterface beaninterface = (EntityInterface) current;
                if (!beaninterface.getProperties().get(key).equals(value))
                {
                    filtedred.add((EntityInterface) current);
                }
            }
        }
        return filtedred;
    }

    /**
     * filters the given collection checkFrom values: -
     * CollectionHelper.CHECK_FROM_START - CollectionHelper.CHECK_FROM_END -
     * CollectionHelper.CHECK_ANY
     * 
     * @param collection
     * @param value
     * @param key
     * @param checkFrom
     * @param caseSensitive
     * @return
     */
    public static List<?> filter(List<?> collection, String value, String key, int checkFrom, boolean caseSensitive)
    {
        List<Object> filtedred = new ArrayList<Object>();

        if (!caseSensitive)
        {
            value = value.toLowerCase();
        }

        for (Object current : collection)
        {
            String data = null;
            if (current instanceof EntityInterface)
            {
                EntityInterface beaninterface = (EntityInterface) current;
                data = DataConverter.getStringValue(beaninterface.getProperties().get(key), false);
            }
            else
            {
                data = DataConverter.getStringValue(current, false);
            }

            if (!caseSensitive)
            {
                data = data.toLowerCase();
            }

            boolean isEqual = false;
            switch (checkFrom)
            {
                case CHECK_FROM_START:
                    isEqual = data.startsWith(value);
                    break;
                case CHECK_FROM_END:
                    isEqual = data.endsWith(value);
                    break;
                case CHECK_ANY:
                    isEqual = data.contains(value);

                default:
                    break;
            }

            if (isEqual)
            {
                filtedred.add(current);
            }

        }
        return filtedred;
    }

    /**
     * entity related control<br>
     * the comparison is made on entities' IDs<br>
     * <br>
     * TODO comparison given key
     * 
     * 
     * @param collection
     * @param entity
     * @return
     */
    public static boolean contains(List<?> collection, EntityInterface entity)
    {
        if ((entity != null) && (collection != null))
        {
            Serializable entityCompanyId = entity.getId();
            for (Object current : collection)
            {
                if (current instanceof EntityInterface)
                {
                    EntityInterface beaninterface = (EntityInterface) current;
                    Serializable currentId = beaninterface.getId();
                    if (entityCompanyId.equals(currentId))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean containsPropertyName(List<?> collection, Object value, String propertykeyname)
    {
        if ((value != null) && (collection != null))
        {
            String paramValue = DataConverter.getStringValue(value, false);
            for (Object current : collection)
            {
                if (current instanceof EntityInterface)
                {
                    EntityInterface beaninterface = (EntityInterface) current;
                    Object objectvalue = beaninterface.getProperties().get(propertykeyname);
                    String beanValue = DataConverter.getStringValue(objectvalue, false);
                    if (paramValue.equals(beanValue))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isEmpty(Collection<?> collection)
    {
        return (collection == null || collection.isEmpty());
    }

    public static Collection<?> removeDoubles(Collection<?> collection)
    {
        return removeDoubles(collection, null);
    }

    public static Collection<?> removeDoubles(Collection<?> collection, String key)
    {
        String previousId = null;

        Collection<?> ordered = order(collection, key);

        Iterator<?> iterator = ordered.iterator();
        while (iterator.hasNext())
        {
            Object currentObj = iterator.next();

            String currentId = null;
            if (currentObj instanceof EntityInterface)
            {
                currentId = DataConverter.getStringValue(((EntityInterface) currentObj).getId(), false);
            }
            else
            {
                currentId = DataConverter.getStringValue(currentObj, false);
            }

            if (previousId == null)
            {
                previousId = currentId;
            }
            else if (previousId.equalsIgnoreCase(currentId))
            {
                iterator.remove();
            }

            previousId = currentId;
        }

        return ordered;
    }

    // public static List<?> getElementsByPropertiesName(List<?> collection,
    // Properties paramters)
    // {
    // if((paramters != null)&&(collection != null))
    // {
    // List<EntityInterface> elementsfound = new ArrayList<EntityInterface>();
    //
    // for(Object current : collection)
    // {
    // if(current instanceof EntityInterface)
    // {
    // EntityInterface beaninterface = (EntityInterface)current;
    //
    // Iterator<Object> iterator = paramters.keySet().iterator();
    // while (iterator.hasNext())
    // {
    // String keyname = DataConverter.getStringValue(iterator.next(), false);
    // Object objectvalue = beaninterface.getProperties().get(keyname);
    // String beanValue = DataConverter.getStringValue(objectvalue, false);
    // if(paramValue.equals(beanValue))
    // {
    // elementsfound.add(beaninterface);
    // }
    // }
    // }
    // }
    // return elementsfound;
    // }
    // return null;
    // }

    /**
     * not tested yet
     * 
     * @param map
     * @return
     * 
     *         public static List<String[]> getMapValues1(Map<Integer, String[]>
     *         map) { List<String[]> valuemaplist = new ArrayList<String[]>();
     * 
     *         Iterator<Integer> iterator = map.keySet().iterator(); while
     *         (iterator.hasNext()) {
     *         valuemaplist.add(map.get(iterator.next())); } return
     *         valuemaplist; }
     */

}
