/**
 * 
 */
package py.com.icarusdb.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 * @author rgamarra
 *
 */
public abstract class ReportHelper
{

    protected byte[] exportReportToXls(JasperPrint jp) throws JRException
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        JRXlsExporter exporterXLS = new JRXlsExporter();
        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
        exporterXLS.exportReport();
        
        return byteArrayOutputStream.toByteArray();
    }

    protected JRBeanCollectionDataSource wrapDS(Collection<?extends EntityInterface> dataSource)
    {
        return new JRBeanCollectionDataSource(dataSource);
    }
    
    public static String addSlash(String string)
    {
        if(!string.endsWith(File.separator)) { 
            string += File.separator;
        }
        return string;
    }


}
