/**
 * 
 */
package py.com.icarusdb.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author mcrose
 *
 */
public class CalendarHelper
{
    public static final String FULL_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String FULL_TIMEZONE_WITH_Z = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static final String DATETIME_FULL = "dd/MM/yyyy HH:mm:ss";

    public static final String DATE = "dd/MM/yyyy";

    public static final String DATE_v2 = "dd-MM-yyyy";

    public static Calendar getCalendar()
    {
        return GregorianCalendar.getInstance();
    }

    public static Date getCurrentTime()
    {
        return new Date(getCurrentSystemTime());
    }

    public static Timestamp getCurrentTimestamp()
    {
        return new Timestamp(getCurrentSystemTime());
    }

    public static long getCurrentSystemTime()
    {
        return System.currentTimeMillis();
    }

    public static int getDay(Date date)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);

        return calendar.get(Calendar.DATE);
    }

    public static int getMonth(Date date)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);

        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(Date date)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentYear()
    {
        Calendar calendar = getCalendar();
        calendar.setTime(getNow());

        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentMonth()
    {
        Calendar calendar = getCalendar();
        calendar.setTime(getNow());

        return calendar.get(Calendar.MONTH) + 1;

    }

    /**
     * return a date with stripped time
     * 
     * @return java.util.Date
     */
    public static Date getToday()
    {
        return stripTimeInfo(getNow());
    }

    /**
     * returns a date with time
     * 
     * @return java.util.Date
     */
    public static Date getNow()
    {
        return new Date(getCurrentSystemTime());
    }

    /**
     * 
     * @param from
     * @param till
     * @return boolean true/false
     */
    public static boolean fromTillError(Date from, Date till)
    {
        return (from.compareTo(till) > 0);
    }

    public static Date getDate(Object obj)
    {
        if (obj == null) return null;

        if (obj instanceof Date)
        {
            return (Date) obj;
        }

        Long longvalue = null;

        if (obj instanceof Long)
        {
            longvalue = (Long) obj;
        }

        if (obj instanceof String)
        {
            longvalue = Long.valueOf(obj.toString());
        }

        return new Date(longvalue);
    }

    /**
     * 
     * @param date
     * @param parseFormat
     * @return java.util.Date
     */
    public static Date parse(String date, String parseFormat)
    {
        if ((date.length() > 20) && (date.endsWith("Z")) && (!parseFormat.equals(FULL_TIMEZONE_WITH_Z)))
        {
            parseFormat = FULL_TIMEZONE_WITH_Z;
        }

        try
        {
            return new SimpleDateFormat(parseFormat).parse(date);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }

        return null;

    }

    public static String parse(Date date, String parseFormat)
    {
        return new SimpleDateFormat(parseFormat).format(date);
    }

    public static String parse(Timestamp date, String parseFormat)
    {
        return new SimpleDateFormat(parseFormat).format(date);
    }

    public static Date stripTimeInfo(Date datetime)
    {
        if (datetime == null)
        {
            return null;
        }

        Calendar calendar = getCalendar();
        calendar.setTime(datetime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getNextDay(Date currentday)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(currentday);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static int getDaysBetweenDates(Date date, Date date2)
    {
        Date dateD = stripTimeInfo(date);
        Date dateH = stripTimeInfo(date2);

        Long dif = (dateH.getTime() - dateD.getTime()) / 24 / 60 / 60 / 1000;
        return dif.intValue();
    }

    public static int getMinutesBetweenDates(Date date, Date date2)
    {
        Long dif = (date2.getTime() - date.getTime()) / 1000 / 60;
        return dif.intValue();
    }

    public static boolean isInBetween(Date date, Date fromDate, Date tillDate)
    {
        if (date == null) return false;

        if (fromDate != null && tillDate == null)
        {
            return date.after(fromDate);
        }

        if (fromDate == null && tillDate != null)
        {
            return date.before(tillDate);
        }

        return date.after(fromDate) && date.before(tillDate);
    }

    public static String getPostgreSQL_toDate(Date date)
    {
        return "to_date('" + parse(date, DATE_v2)+ "', '"+ DATE_v2.toString() +"')";
    }

}
