/**
 * 
 */
package py.com.icarusdb.demo.invoice.controller;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import py.com.icarusdb.demo.session.Credentials;
import py.com.icarusdb.demo.util.AppHelper;
import py.com.icarusdb.demo.util.ReportAction;
import py.com.icarusdb.entity.EntityInterface;
import py.com.icarusdb.util.MIMEType;
import py.com.icarusdb.util.ReportParameters;


/**
 * @author rgamarra
 *
 */
@Named("reportController")
@RequestScoped
public class ReportController extends ReportAction implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -16707338676659598L;

    @Inject Credentials credentials;

    @PersistenceUnit
    private EntityManagerFactory emf;
    private EntityManager em = null;
    
    @PostConstruct
    public void onCreate() {
        em = emf.createEntityManager();
    }
    
    
    private String reportTemplateName = null;
    private String reportName = null;

    private List<EntityInterface> dataSource = null;

    private Map<String, Object> parameters = new HashMap<String, Object>();
    
    private String imageName = "logofpti.jpg";
    private String imagePath = "/resources/gfx/pti";
    
    
    
    public void init()
    {
        reportTemplateName = null;
        reportName = null;
        parameters = new HashMap<String, Object>();
        dataSource = new LinkedList<EntityInterface>();
    }

    @Override
    protected String getUserName() 
    {
        return credentials.getUsername();
    }
    
    @Override
    protected String getReportTemplateName()
    {
        return reportTemplateName;
    }
    
    public void setReportTemplateName(String reportTemplateName)
    {
        this.reportTemplateName = reportTemplateName;
    }

    @Override
    protected String getReportName()
    {
        return reportName;
    }
    
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    @Override
    protected List<?extends EntityInterface> getDataSource()
    {
        return dataSource;
    }

    public void addDataSourceEntity(EntityInterface entity)
    {
        dataSource.add(em.find(entity.getClass(), entity.getId()));
    }
    
    public void addDataSourceEntityCollection(List<?extends EntityInterface> entities)
    {
        for(EntityInterface ei : entities)
        {
            addDataSourceEntity(ei);
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public void addDataSourceEntityList(List<?extends EntityInterface> entities)
    {
        	this.dataSource = (List<EntityInterface>) entities;
    }
    
    @Override
    protected String getReportPath()
    {
        return super.reportPath;
    }
    
    public void setReportPath(String reportPath)
    {
        super.setReportPath(reportPath);
    }
    
    @Override
    protected String getImageName()
    {
        return imageName;
    }

    public void setImageName(String imageName)
    {
        this.imageName = imageName;
    }
    
    @Override
    protected String getImagePath()
    {
        return imagePath;
    }
    
    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    @Override
    protected Map<String, Object> getParameters()
    {
        return parameters;
    }
    
    @Override
    public boolean isUseEntityInterfaceDS()
    {
        return useEntityInterfaceDS;
    }
    
    @Override
    public void setUseEntityInterfaceDS(boolean value)
    {
        this.useEntityInterfaceDS = value;
    }

    @Override
    public void setConnection(Connection conn)
    {
        super.setConnection(conn);
    }
    
    public void print()
    {
        print(MIMEType.PDF);
    }
    
    public void printXls()
    {
        print(MIMEType.XLS);
    }
    
    public void print(MIMEType type)
    {
        super.generateReport(type);
    }

    @Override 
    public JRBeanCollectionDataSource wrapDS(Collection<?extends EntityInterface> dataSource)
    {
        return super.wrapDS(dataSource);
    }
    
    public void addSubReportDir()
    {
        String fullpath = null;
        
        if(getReportPath() == null) {        
            fullpath = AppHelper.getServletContext().getRealPath(ReportParameters.REPORT_PATH);
        }else{
            fullpath = AppHelper.getServletContext().getRealPath(getReportPath());
        }
        
        if(!fullpath.endsWith(File.separator.toString())) {
            fullpath += File.separator;
        }
        
        addParameter(ReportParameters.SUBREPORT_PATH, fullpath);
        
    }

    public void addParameters(Properties properties)
    {
        if (properties == null) return;
        
        Iterator<Object> iterator = properties.keySet().iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next().toString();
            addParameter(key, properties.get(key));
        }
        
    }

    
}
