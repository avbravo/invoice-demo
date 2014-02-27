/**
 * 
 */
package py.com.icarusdb.common;

/**
 * @author rgamarra
 *
 * http://www.w3schools.com/media/media_mimeref.asp
 * 
 */
public enum MIMEType
{
      ANY("application/octet-stream", ".")
    , PDF("application/pdf", "pdf")
    , XLS("application/vnd.ms-excel", "xls")
    , TXT("application/text/plain", "txt")
    
    ;
      
      
    private String mimetype;
    private String extension;
      
    MIMEType(String mimetype, String extension)
    {
        this.mimetype = mimetype;
        this.extension = extension;
    }
    
    public String getMimetype()
    {
        return mimetype;
    }
    
    @Override
    public String toString()
    {
        return mimetype.toString();
    }
    
    public String getExtension()
    {
        return extension;
    }

}
