package py.com.icarusdb.demo.util;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import py.com.icarusdb.common.EntityInterface;
import py.com.icarusdb.common.MIMEType;
import py.com.icarusdb.common.ReportHelper;
import py.com.icarusdb.common.ReportParameters;

/**
 * @author rgamarra
 *
 * this helper class must be extended for the ReportController
 *  
 */
public abstract class ReportAction extends ReportHelper
{
    
    @Inject
    private transient Logger logger;

    
    private boolean printEmptyReport = true;
    protected void setPrintEmptyReport(boolean value)
    {
        printEmptyReport = value;
    }
    
    private boolean useConnection = false;
    protected void setUseConnection(boolean value)
    {
        useConnection = value;
    }
    
    private Connection conn = null;
    protected void setConnection(Connection conn)
    {
        this.conn = conn;
        useConnection = true;
    }
    
    
    private boolean sendToPrinter = false;
    public void setSendToPrinter(boolean sendToPrinter)
    {
        this.sendToPrinter = sendToPrinter;
    }
    
    protected boolean useEntityInterfaceDS = false;


    protected abstract boolean isUseEntityInterfaceDS();
    protected void setUseEntityInterfaceDS(boolean value)
    {
        this.useEntityInterfaceDS = value;
    }
    
    
    
    protected abstract String getUserName();
    //protected abstract UserInterface getUser();

    protected abstract String getReportTemplateName();
    
    /** this is the report's title */
    protected abstract String getReportName();
    
    protected abstract List<?extends EntityInterface> getDataSource();
    
    protected abstract String getImageName();

    /** if null, is used {@link ReportParameters}.IMAGE_FOLDER_NAME */
    protected abstract String getImagePath();
    
    /** if null, is used {@link ReportParameters}.REPORT_FOLDER_NAME */
    protected String reportPath = null;
    protected abstract String getReportPath();
    protected void setReportPath(String reportPath)
    {
        this.reportPath = reportPath;
    }
    
    protected abstract Map<String, Object> getParameters();
    
    public void addParameter(String key, Object value)
    {
        getParameters().put(key, value);
    }

    
    
    public void generateReport(MIMEType mimeType)
    {
        if(!printEmptyReport && !useConnection && getDataSource().isEmpty())
        {
            MessageUtil.addFacesMessageError("search.error.noDataFound");
            return;
        }
        
        if(useConnection && conn == null)
        {
            MessageUtil.addFacesMessageError("error.report.noConnectionDefined");
            return;
        }
        
        Map<String, Object> reportParams = getBasicParameters();
        reportParams.putAll(getParameters());

        try
        {
            String templateName = getReportTemplateFullName();
            
            JasperPrint jp = null;
            if(useConnection)
            {
                jp = JasperFillManager.fillReport(templateName, reportParams, conn );
            }
            else
            {
                if(useEntityInterfaceDS)
                {
//                    @SuppressWarnings("unchecked")
//                    List<EntityInterface> entities = new ArrayList<EntityInterface>((Collection<? extends EntityInterface>) getDataSource());
//                    JREntityInterfaceDataSource ds = new JREntityInterfaceDataSource(entities);
//                    jp = JasperFillManager.fillReport(templateName, reportParams, ds);
                }
                else
                {
                    jp = JasperFillManager.fillReport(templateName, reportParams, wrapDS(getDataSource()));
                }
            }
            
            byte[] exported = null;
            switch (mimeType)
            {
                case PDF:
                    exported = JasperExportManager.exportReportToPdf(jp);
                    break;

                case XLS:
                    exported = super.exportReportToXls(jp);
                    break;

                case TXT:
                  //TODO  implement!
//                    exported = super.exportReportToTxt(jp);       
                    break;

                default:
                    logger.log(Level.SEVERE, "report mime type not supported: " + mimeType);
                    break;
            }
            
            if(sendToPrinter)
            {
                //TODO add code to send to printer in at ReportHelper level
            }
            else
            {
                HttpServletResponse response = prepareResponse(getReportTemplateName(), mimeType);
                response.setContentLength(exported.length);
                response.getOutputStream().write(exported);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                
                FacesContext.getCurrentInstance().responseComplete();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.log(Level.SEVERE, e.getMessage());
        }
        
    }
    

    
    private String getReportTemplateFullName()
    {
        if(getReportPath()==null) 
        {
            return getReportTemplateFullName(getReportTemplateName());
        }
        return getReportTemplateFullName(getReportPath(), getReportTemplateName());
    }
    
    
    private Map<String, Object> getBasicParameters()
    {
        Map<String, Object> reportParams = new HashMap<String, Object>();
        reportParams.put(ReportParameters.USER_NAME, getUserName());
        reportParams.put(ReportParameters.REPORT_TITLE, getReportName());
        
        String imagePath = getImagePath();
        if (imagePath == null || imagePath.isEmpty())
        {
            imagePath = ReportParameters.IMAGE_FOLDER;
        }
        imagePath = AppHelper.getServletContext().getRealPath(imagePath);
        reportParams.put(ReportParameters.IMAGE_FOLDER, imagePath);
        reportParams.put(ReportParameters.IMAGE_NAME, "/"+getImageName());
        
        return reportParams;
    }
    
    protected String getFullPath(String url)
    {
        return AppHelper.getServletContext().getRealPath(url);
    }
    
    protected String getReportTemplateFullName(String templateName)
    {
        return getReportTemplateFullName(null, templateName);
    }
    
    protected String getReportTemplateFullName(String reportPath, String templateName)
    {
        if(reportPath == null || reportPath.isEmpty()) {
            reportPath = ReportParameters.REPORT_PATH;
        }
        return getFullPath(reportPath + "/" + templateName + ".jasper");
    }


    protected HttpServletResponse prepareResponse(String reportName, MIMEType mimeType)
    {
        String filename = reportName + "."+mimeType.getExtension();
        
        HttpServletResponse servletResponse = AppHelper.getServletResponse();
        servletResponse.setHeader("Expires", "0");
        servletResponse.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        servletResponse.setHeader("Content-Disposition", "attachament; filename=" + filename);
        
        switch (mimeType)
        {
            case PDF:
                servletResponse.setContentType("application/pdf");
                break;
            case XLS:
                servletResponse.setContentType("application/vnd.ms-excel");
                break;

            default:
                servletResponse.setContentType("application/octet-stream");
        }
        return servletResponse;
    }
    
    protected HttpServletResponse prepareResponse(String fileName, String contentType)
    {
        HttpServletResponse servletResponse = AppHelper.getServletResponse();
        
        servletResponse.setHeader("Expires", "0");
        servletResponse.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        servletResponse.setHeader("Content-Disposition", "attachament; filename=" + fileName);
        servletResponse.setContentType(contentType);
        
        return servletResponse;
    }
    
    
}
