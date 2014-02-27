/**
 * 
 */
package py.com.icarusdb.common;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author rgamarra
 * 
 */
public class DataConverter
{

    public static String getStringValue(int value)
    {
        return getStringValue(new Integer(value), false);
    }

    public static String getStringValue(int value, boolean trim)
    {
        return getStringValue(new Integer(value), trim);
    }

    public static String getStringValue(long value)
    {
        return getStringValue(new Long(value), false);
    }

    public static String getStringValue(long value, boolean trim)
    {
        return getStringValue(new Long(value), trim);
    }

    public static String getStringValue(boolean value)
    {
        return getStringValue(new Boolean(value), false);
    }

    public static String getStringValue(boolean value, boolean trim)
    {
        return getStringValue(new Boolean(value), trim);
    }

    public static String getStringValue(Object objectvalue)
    {
        return getStringValue(objectvalue, false);
    }

    /**
     * 
     * @param objectvalue
     * @param Boolean
     *            trim
     * @return objectvalue.toString()
     */
    public static String getStringValue(Object objectvalue, boolean trim)
    {
        String stringvalue = null;

        if (objectvalue instanceof String)
        {
            stringvalue = (String) objectvalue;
        }

        if (objectvalue instanceof Integer)
        {
            Integer integervalue = (Integer) objectvalue;
            stringvalue = integervalue.toString();
        }

        if (objectvalue instanceof Long)
        {
            Long longvalue = (Long) objectvalue;
            stringvalue = longvalue.toString();
        }

        if (objectvalue instanceof Short)
        {
            Short shortvalue = (Short) objectvalue;
            stringvalue = shortvalue.toString();
        }

        if (objectvalue instanceof Double)
        {
            Double doublevalue = (Double) objectvalue;
            stringvalue = doublevalue.toString();
        }

        if (objectvalue instanceof BigDecimal)
        {
            BigDecimal bigdecimalvalue = (BigDecimal) objectvalue;
            stringvalue = bigdecimalvalue.toString();
        }

        if (objectvalue instanceof Boolean)
        {
            stringvalue = ((Boolean) objectvalue).toString();
        }

        if (trim)
        {
            stringvalue = stringvalue.trim();
        }

        return stringvalue;
    }

    /**
     * returns a string with number format including the miller point
     * 
     * example: "100000" returns "100.000" 520001 returns "520.001"
     * 
     * TODO - retrieve miller and decimal points from java.util.Locale config -
     * add additionalParameters for setting: - decimal point - number of
     * decimals - rounded ???
     * 
     * @param objectvalue
     * @return String
     */
    public static String getStringNumberFormat(Object objectvalue)
    {

        String stringvalue = null;

        if (objectvalue instanceof String)
        {
            stringvalue = ((String) objectvalue).trim();
        }

        if (objectvalue instanceof Integer)
        {
            Integer integervalue = (Integer) objectvalue;
            stringvalue = integervalue.toString();
        }

        if (objectvalue instanceof Long)
        {
            Long longvalue = (Long) objectvalue;
            stringvalue = longvalue.toString();
        }

        if (objectvalue instanceof Double)
        {
            Double doublevalue = (Double) objectvalue;
            stringvalue = doublevalue.toString();
        }

        if (objectvalue instanceof BigDecimal)
        {
            BigDecimal bigdecimalvalue = (BigDecimal) objectvalue;
            stringvalue = bigdecimalvalue.toString();
        }

        int c = 1;
        String formated = "";
        char[] charArray = stringvalue.toCharArray();
        for (int index = charArray.length - 1; index >= 0; index--)
        {
            if (c > 3)
            {
                c = 1;
                formated = "." + formated;
            }
            formated = charArray[index] + formated;
            c++;
        }

        return formated;
    }

    /**
     * 
     * @param objectvalue
     * @return new Integer(objectvalue)
     */
    public static Integer getIntegerValue(Object objectvalue)
    {
        if (objectvalue == null)
            return null;

        Integer integervalue = null;

        if (objectvalue instanceof String)
        {
            integervalue = new Integer(((String) objectvalue).trim());
        }
        else if (objectvalue instanceof Integer)
        {
            integervalue = (Integer) objectvalue;
        }
        else if (objectvalue instanceof BigInteger)
        {
            BigInteger bInt = (BigInteger) objectvalue;
            integervalue = bInt.intValue();
        }
        else if (objectvalue instanceof Long)
        {
            Long longvalue = (Long) objectvalue;
            integervalue = new Integer(longvalue.intValue());
        }
        else if (objectvalue instanceof Double)
        {
            Double doublevalue = (Double) objectvalue;
            integervalue = new Integer(doublevalue.intValue());
        }
        else if (objectvalue instanceof BigDecimal)
        {
            BigDecimal bigdecimalvalue = (BigDecimal) objectvalue;
            integervalue = new Integer(bigdecimalvalue.intValue());
        }

        return integervalue;
    }

    /**
     * 
     * @param objectvalue
     * @return new Long(objectvalue)
     */
    public static Long getLongValue(Object objectvalue)
    {
        if (objectvalue == null)
            return null;

        Long longvalue = null;

        if (objectvalue instanceof String)
        {
            longvalue = new Long(((String) objectvalue).trim());
        }

        if (objectvalue instanceof Integer)
        {
            longvalue = new Long((Integer) objectvalue);
        }

        if (objectvalue instanceof Long)
        {
            longvalue = (Long) objectvalue;
        }

        if (objectvalue instanceof Double)
        {
            longvalue = ((Double) objectvalue).longValue();
        }

        if (objectvalue instanceof BigDecimal)
        {
            longvalue = ((BigDecimal) objectvalue).longValue();
        }

        return longvalue;
    }

    public static Double getDoubleValue(Object objectvalue)
    {
        if (objectvalue == null)
            return null;

        Double doblevalue = null;

        if (objectvalue instanceof String)
        {
            doblevalue = Double.parseDouble((String) objectvalue);
        }

        if (objectvalue instanceof Integer)
        {
            doblevalue = new Double((Integer) objectvalue);
        }

        if (objectvalue instanceof Long)
        {
            doblevalue = new Double((Long) objectvalue);
        }

        if (objectvalue instanceof Double)
        {
            doblevalue = (Double) objectvalue;
        }

        if (objectvalue instanceof BigDecimal)
        {
            doblevalue = ((BigDecimal) objectvalue).doubleValue();
        }

        return doblevalue;
    }

    public static BigDecimal getBigDecimalValue(Object objectvalue)
    {
        if (objectvalue == null)
            return null;

        BigDecimal bigValue = null;

        if (objectvalue instanceof String)
        {
            bigValue = new BigDecimal((String) objectvalue);
        }

        if (objectvalue instanceof BigInteger)
        {
            bigValue = new BigDecimal((BigInteger) objectvalue);
        }

        if (objectvalue instanceof Integer)
        {
            bigValue = new BigDecimal((Integer) objectvalue);
        }

        if (objectvalue instanceof Long)
        {
            bigValue = BigDecimal.valueOf((Long) objectvalue);
        }

        if (objectvalue instanceof Double)
        {
            bigValue = BigDecimal.valueOf((Double) objectvalue);
        }

        if (objectvalue instanceof BigDecimal)
        {
            bigValue = (BigDecimal) objectvalue;
        }

        return bigValue;
    }

    /**
     * retrieves a wrapped string so it can be parsed with no problems
     * 
     * mainly used for xml parsing
     * 
     * @param xmlstring
     * @return InputSource wrappedxml
     */
    public static InputSource getInputSource(String xmlstring)
    {
        return new InputSource(new StringReader(xmlstring));
    }

    /**
     * retrieves the xml in string format from the given inputStream
     * 
     * @param in
     * @return xml.toString()
     */
    public static String getXMLString(InputStream in)
    {
        StringBuffer sb = new StringBuffer();
        int c;
        try
        {
            while ((c = in.read()) != -1)
            {
                sb.append((char) c);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * retrieves the xml in string format from the given document
     * 
     * @param doc
     * @return xml.toString()
     */
    public static String getXMLString(Document doc)
    {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        try
        {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(domSource, result);
        }
        catch (TransformerException ex)
        {
            ex.printStackTrace();
        }
        return writer.toString();
    }

    public static Object getCastedValue(Class<?> classToCast, Object valueToCast)
    {
        String simpleName = classToCast.getSimpleName();

        if ("String".equals(simpleName))
        {
            return getStringValue(valueToCast, false);
        }
        if ("Long".equals(simpleName))
        {
            return getLongValue(valueToCast);
        }
        if ("Ingeter".equals(simpleName))
        {
            return getIntegerValue(valueToCast);
        }
        if ("BigDecimal".equals(simpleName))
        {// LATER implement
            return null;
        }

        return null;
    }

    /**
     * @param java
     *            .util.Date date
     * @return java.sql.Timestamp timestamp
     */
    public static java.sql.Timestamp convertDate2SqlTimestamp(java.util.Date date)
    {
        if (date == null)
        {
            return null;
        }
        return (new Timestamp(date.getTime()));

    }

    /**
     * 
     * @param stringDate
     * @return
     * 
     *         public java.sql.Date convertString2Date(String stringDate) {
     *         SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
     * 
     *         java.util.Date date = null; try { date = df.parse(stringDate);
     *         return convertDate2Sql(date); } catch(ParseException e) {
     *         e.printStackTrace(); }
     * 
     *         return null;
     * 
     *         }
     */

    public static byte[] getByteArray(String filename)
    {
        byte[] byteArray = null;
        try
        {
            File fileToRead = new File(filename);
            byteArray = new byte[(int) fileToRead.length()];
            FileInputStream fip = new FileInputStream(fileToRead);
            DataInputStream dis = new DataInputStream(fip);
            dis.readFully(byteArray);
            dis.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }

    public static ByteArrayOutputStream convertInputStream2ByteArrayOutputStream(InputStream is)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int c;
        try
        {
            while ((c = is.read()) != -1)
            {
                baos.write(c);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return baos;
    }

    /**
     * 
     * @param xmlstring
     * @return InputSource xml
     */
    public static InputSource getXMLInputSource(String xmlstring)
    {
        return new InputSource(new StringReader(xmlstring));
    }

    /**
     * retrieves the xmlstring in Document format
     * 
     * @param string
     * @return Document doc
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static Document getXMLDocument(String string)
    {
        try
        {
            return getXMLDocument(getXMLInputSource(string));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO move this method to correct helper class
     * 
     * retrieves a DocumentBuilder
     * 
     * @return DocumentBuilder
     * @throws ParserConfigurationException
     */
    public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException
    {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    /**
     * 
     * @param date
     * @param format
     * @return
     */
    public static String getXMLDateTimeFormat(Date date, String format)
    {
        return getXMLDateTimeFormat(IDBCalendar.getSpecialDateFormat(date, format));
    }

    /**
     * special string date format for XML parsing
     * 
     * @param dateformated
     * @return
     */
    public static String getXMLDateTimeFormat(String dateformated)
    {
        if (dateformated == null)
        {
            return null;
        }

        String[] split = dateformated.split(" ");

        return split[0] + "T" + split[1];
    }

    /**
     * retrieves the xmlstring in Document format
     * 
     * @param inputsource
     * @return Document doc
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Document getXMLDocument(InputSource inputsource) throws ParserConfigurationException, SAXException, IOException
    {
        return getDocumentBuilder().parse(inputsource);
    }

}
