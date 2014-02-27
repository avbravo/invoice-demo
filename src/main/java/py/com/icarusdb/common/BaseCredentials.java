package py.com.icarusdb.common;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

public class BaseCredentials implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 4678049249209801824L;
    
    protected String ipAddr;
    protected String username;
    protected String password;
//    protected Serializable companyId;
    
    protected Properties parameters = null;
    
    protected List<String> rols = null;
    
    public String getIpAddr()
    {
        return ipAddr;
    }
    
    public void setIpAddr(String ipAddr)
    {
        this.ipAddr = ipAddr;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
//    public Serializable getCompanyId()
//    {
//        return companyId;
//    }
//    
//    public void setCompanyId(Serializable companyId)
//    {
//        this.companyId = companyId;
//    }
    
    public Properties getParameters()
    {
        return parameters;
    }
    
    public void setParameters(Properties parameters)
    {
        this.parameters = parameters;
    }
    
    public void addParamenter(String key, Object value)
    {
        parameters.put(key, value);
    }

    public void addRole(String rol)
    {
        rols.add(rol);
    }
    
    public boolean hasRol(String rol)
    {
        return rols.contains(rol);
    }
 
    public List<String> getRols()
    {
        return rols;
    }
    
    public void setRols(List<String> rols)
    {
        this.rols = rols;
    }

}