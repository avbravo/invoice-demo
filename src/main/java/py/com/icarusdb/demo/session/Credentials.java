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
    private static final long serialVersionUID = -2514296222946074932L;

    @PostConstruct
    public void init()
    {
        ipAddr = null;
        username = null;
        password = null;
        parameters = new Properties();
        rols = new LinkedList<String>();
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

}