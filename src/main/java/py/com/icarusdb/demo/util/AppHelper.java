/**
 * 
 */
package py.com.icarusdb.demo.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.RollbackException;

import py.com.icarusdb.util.BundleHelper;



/**
 * @author RobertoGamarra
 *
 */
public class AppHelper
{
    
    public static String getDomainUrl()
    {
    	String host = getExternalContext().getRequestHeaderMap().get("host");
    	String appName = getExternalContext().getRequestContextPath();

    	return "http://" + host + appName;
    }

    public static ExternalContext getExternalContext()
    {
        return FacesContext.getCurrentInstance().getExternalContext();
    }
    
    public static ServletContext getServletContext()
    {
        return (ServletContext) getExternalContext().getContext();
    }
    
    public static HttpServletRequest getServletRequest()
    {
        return (HttpServletRequest) getExternalContext().getRequest();
    }
    
    public static HttpServletResponse getServletResponse()
    {
        return (HttpServletResponse) getExternalContext().getResponse();
    }
    
    public static Map<String, Object> getRequestContext()
    {
        return getExternalContext().getRequestMap();
    }
    
    public static Locale getLocale()
    {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    public static String getBundleMessage(String key)
    {
        return BundleHelper.getBundleMessage(SessionParameters.BUNDLE_URL, key);
    }
    /**
     * retrieves a String in (x, x, x) format to be used in a SQL IN statement
     * 
     * @param collection
     * @return
     */
    public static String getInList(List<?> collection)
    {
        if(collection == null || collection.isEmpty()) return null;
        
        String listIn = "(";
        for(Object element : collection)
        {
            listIn += "'"+element.toString() + "', ";
        }
        
        listIn = listIn.substring(0, listIn.length()-2);
        
        return listIn + ")";
    }
    
    /**
     * {@link java.util.Set} implementation of getInList()
     * 
     * @param collection
     * @return
     */
    public static String getInList(Set<?> collection)
    {
        List<Object> list = new LinkedList<Object>();
        
        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext())
        {
            list.add(iterator.next());
        }
        
        return getInList(list);
    }
    
    
    public static String prepareString(List<?> collection, String separator)
    {
        if(collection == null || collection.isEmpty()) return null;
        
        String list = "";
        for(Iterator<?> iter = collection.iterator(); iter.hasNext();)
        {
            list += iter.next().toString() + separator;
        }
        
        list = list.substring(0, list.length()-1);
        
        return list;
    }
    
    public static String stripMillar(Object value)
    {
        String stringval = "";
        
        String millarpoint = specialCharsStringBuilder(value);
        
        String[] split = value.toString().trim().split(millarpoint);
        for(int index=0; index < split.length; index++)
        {
            stringval += split[index];
        }
        
        return stringval;
    }
    
    public static String specialCharsStringBuilder(Object value)
    {
        String spacialchar = "";
        if(value.toString().contains("."))
        {
            spacialchar = "\\.";
        } 
        else if(value.toString().contains(","))
        {
            spacialchar = "\\,";
        }
        return spacialchar;
    }
    
	
    
    
    /**
     * FIXME add jar to facilitate exceptions' debug and return detailed errors
     * 
     * @param e
     */
    public static void printException(Exception e)
    {
        String message = e.getMessage();
        
        if(e instanceof RollbackException)
        {
            RollbackException rbe = (RollbackException) e;
            Throwable cause = rbe.getCause();

            if(cause instanceof PersistenceException)
            {
                PersistenceException pe = (PersistenceException) cause;
                if(pe.getCause().toString().contains("ConstraintViolationException"))
                {
//                    ConstraintViolationException cve = (ConstraintViolationException) pe.getCause();
                    message = pe.getCause().getLocalizedMessage();
//                    for(ConstraintViolation<?> ex : cve.getConstraintViolations())
//                    {
//                        ex.getConstraintDescriptor();
//                        ex.getInvalidValue();
//                        ex.getRootBean();
//                    }
//                    message = cve.getMessage();
                }
            }
            
            
//            for (InvalidValue invalidValue : ise.getInvalidValues()) 
//            {
//                String msg = invalidValue.getBeanClass().getSimpleName() +
//                             " has an invalid property: " + invalidValue.getPropertyName() +
//                             " with message: " + invalidValue.getMessage() + ".";
//                System.err.println(msg);
//            }
            
        }
        else
        {
            e.printStackTrace();
        }
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "system.errors"));
    }



	/**
	 * construct a Code base on Object Id separated with the given separatorChar<br>
	 * <br>
	 * Ex:<br>
	 * fatherId = 23<br>
	 * ChildId = 1<br>
	 * separator = -<br>
	 * <br>
	 * codeId = 23-01<br>
	 * <br>
	 * 
	 * @param parentId
	 * @param childId
	 * @param separatorChar
	 * @return
	 */
    public static String buildCodeId(Object childId, Object parentId, String separatorChar)
    {
        if(childId == null) { 
            return null;
        }
        
        String codeId = "";
        String schildId = childId.toString().trim();
        
        if(parentId != null && !parentId.toString().isEmpty())
        {
            if(parentId instanceof Number &&
              Integer.valueOf(parentId.toString()).intValue() < 10)
            {
                parentId = "0"+parentId;
            }
            codeId += parentId.toString().trim() + separatorChar; 
        }
        
        if(childId instanceof Number &&
           Integer.valueOf(schildId).intValue() < 10)
        {
            schildId = "0"+schildId;
        }
        
        if(!schildId.isEmpty())
        {
            codeId += schildId; 
        }
        
        return codeId;
    }
 
    public static String buildCodeId(String codeId)
    {
        String code = "";
        String[] split = codeId.toString().split("0");
        
        for(int index=0; index < split.length; index++)
        {
            code += split[index];
        }
        return code;
    }

    
    /**
     * checks for the given String if it has any values
     * 
     * @param string
     * @return
     */
    public static boolean nothing(String string)
    {
        return string==null||string.isEmpty();
    }

    public static String getClientIpAddr()
    {
        return getServletRequest().getRemoteAddr();
    }

//    public static String getRESTfullConfig(String cfgFileName) throws FileNotFoundException, IOException
//    {
//        return UriBuilder.buildLoginUri(FileHelper.loadConfigParams(cfgFileName));
//    }
    
    public static String formatNumber(Object value)
    {
        NumberFormat nf = NumberFormat.getNumberInstance(getLocale());
        
        String sval = new String(value.toString());
        if(sval.contains("(")) {
            String[] split = sval.split("\\(");
            sval = split[1];
        }
        if(sval.contains(")")) {
            String[] split = sval.split("\\)");
            sval = split[0];
        }
        
        return nf.format(new BigDecimal(sval));
    }


}
