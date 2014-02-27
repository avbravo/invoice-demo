/**
 * 
 */
package py.com.icarusdb.common;

import java.util.Comparator;
import java.util.Map;

/**
 * @author rgamarra
 * 
 */
public class IDBComparator implements Comparator<Object>
{
  
  /**
   * sorting type
   */
  private boolean ascending = true;
  
  /**
   * entity's field name
   */
  private String propertyKeyName = null;
  

  public IDBComparator(String entityPropertyKeyName)
  {
    this.propertyKeyName = entityPropertyKeyName;
  }
  
  public IDBComparator(String entityPropertyKeyName, boolean ascending)
  {
    this.propertyKeyName = entityPropertyKeyName;
    this.ascending = ascending;
  }
  
  public void setAscending(boolean ascending)
  {
    this.ascending = ascending;
  }
  
  public void setPropertyKey(String sortPropertyKeyName)
  {
    this.propertyKeyName = sortPropertyKeyName;
  }
  
  @SuppressWarnings("unchecked")
  public int compare(Object obj1, Object obj2)
  {
    Object propertyone = null;
    Object propertytwo = null;
    
    if((propertyKeyName != null) && (obj1 instanceof EntityInterface) && (obj2 instanceof EntityInterface))
    {
      propertyone = getProperty((EntityInterface) obj1, propertyKeyName);
      propertytwo = getProperty((EntityInterface) obj2, propertyKeyName);
    }
    
    if((propertyKeyName != null) && (obj1 instanceof Map) && (obj2 instanceof Map))
    {
      propertyone = getProperty((Map<?,?>) obj1, propertyKeyName);
      propertytwo = getProperty((Map<?,?>) obj2, propertyKeyName);
    }
    
    
    if(propertyone == null && propertytwo == null)
    {
      if(obj1 instanceof EntityInterface && obj2 instanceof EntityInterface)
      {
        /* compare on ids */
        propertyone = ((EntityInterface) obj1).getId();
        propertytwo = ((EntityInterface) obj2).getId();
      }
      else
      {
        propertyone = obj1;
        propertytwo = obj2;
      }
    }
    
    if(propertyone != null && propertytwo != null && propertyone instanceof Comparable && propertytwo instanceof Comparable)
    {
      Comparable<Object> cOne = (Comparable<Object>) propertyone;
      Comparable<Object> cTwo = (Comparable<Object>) propertytwo;
      
      if(ascending)
      {
        return cOne.compareTo(cTwo);
      }
      return cTwo.compareTo(cOne);
    }
    
    if((propertyone == null) && (propertytwo != null))
    {
      /* goto top */
      return 1;
    }
    if((propertyone != null) && (propertytwo == null))
    {
      /* goto bottom */
      return -1;
    }
    /* equals */
    return 0;
  }
  
  private Object getProperty(EntityInterface entity, String propertyKeyName)
  {
    return entity.getProperties().get(propertyKeyName);
  }
  
  private Object getProperty(Map<?, ?> map, String propertyKeyName)
  {
    return map.get(propertyKeyName);
  }
  
}
