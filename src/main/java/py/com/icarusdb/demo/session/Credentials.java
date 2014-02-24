package py.com.icarusdb.demo.session;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import py.com.icarusdb.dto.BaseCredentials;

@SessionScoped
@Named
public class Credentials extends BaseCredentials implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 7569366513123476994L;

    private String companyName = null;
    
    @PostConstruct
    public void init()
    {
        ipAddr = null;
        username = null;
        password = null;
        companyName = null;
        parameters = new Properties();
        rols = new LinkedList<String>();
        
        rols.add("supermaster");
        rols.add("admin");
        rols.add("create");
        rols.add("edit");
        rols.add("delete");
    }

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
    
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

}