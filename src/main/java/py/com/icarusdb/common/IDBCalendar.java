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
import java.util.Locale;

/**
 * @author rgamarra
 * 
 */
public abstract class IDBCalendar extends Calendar
{
    /**
     * 
     */
    private static final long serialVersionUID = 7324356143845969796L;
    
    private static Calendar instance = null;

    public static Calendar getCalendar()
    {
        if (instance == null)
        {
            instance = new GregorianCalendar();
        }
        return instance;
    }

    public static Date getNow()
    {
        return new Date(System.currentTimeMillis());
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

    public static Date getToday()
    {
        return stripTimeInfo(getNow());
    }

    public static Date getTomorow()
    {
        return getNextDay(getToday());
    }

    public static Date getYesterday()
    {
        return getPreviousDay(getToday());
    }

    public static Date getNextDay(Date currentday)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(currentday);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date getNextMonth(Date currentday)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(currentday);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public static Date getNextYear(Date curretndate)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(curretndate);
        calendar.add(Calendar.MONTH, 12);
        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth(Date currentday)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(currentday);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getPreviousDay(Date currentday)
    {
        return getPreviousDay(currentday, 1);
    }

    public static Date getPreviousDay(Date currentday, int amountOfDays)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(currentday);
        calendar.add(Calendar.DATE, -amountOfDays);
        return calendar.getTime();
    }

    public static Date getPreviousMonth(Date currentday)
    {
        return getPreviousMonth(currentday, 1);
    }

    public static Date getPreviousMonth(Date currentday, int amountOfMonths)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(currentday);
        calendar.add(Calendar.MONTH, -amountOfMonths);
        return calendar.getTime();
    }

    public static Date getFirstDayOfTheYear(Date date)
    {
        Calendar currentdate = new GregorianCalendar();
        currentdate.setTime(date);

        Calendar calendar = new GregorianCalendar();
        calendar.set(currentdate.get(Calendar.YEAR), Calendar.JANUARY, 1);
        return calendar.getTime();
    }

    public static int getLastDayOfCurrentMonth()
    {
        Calendar currentdate = new GregorianCalendar();
        currentdate.setTime(getToday());
        return currentdate.getActualMaximum(DAY_OF_MONTH);
    }

    public static int getLastDayOfMonth(Calendar calendar)
    {
        return calendar.getActualMaximum(DAY_OF_MONTH);
    }

    public static Date getLastDayOfMonth(Date date)
    {
        Calendar currentdate = new GregorianCalendar();
        currentdate.setTime(date);
        int day = currentdate.getActualMaximum(DAY_OF_MONTH);

        currentdate.set(Calendar.DAY_OF_MONTH, day);
        return currentdate.getTime();
    }

    public static Date getLastDayOfTheYear(Date date)
    {
        Calendar currentdate = new GregorianCalendar();
        currentdate.setTime(date);

        Calendar calendar = new GregorianCalendar();
        calendar.set(currentdate.get(Calendar.YEAR), Calendar.DECEMBER, 31);
        return calendar.getTime();
    }

    public static Date addMonthsToDate(Date date, int months)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    public static Date addDaysToDate(Date date, int days)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        int day = calendar.get(Calendar.DATE);
        calendar.add(Calendar.DATE, day + days);

        return calendar.getTime();
    }

    public static Date addYearsToDate(Date date, int years)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, year + years);

        return calendar.getTime();
    }

    /**
     * add time: hours, minutes or seconds to the given date<br>
     * <br>
     * keyTime values:<br>
     * - Calendar.HOUR (12 hs AM/PM)<br>
     * - Calendar.HOUR_OF_DAY (24 hs)<br>
     * - Calendar.MINUTE<br>
     * - Calendar.SECOND<br>
     * - Calendar.MILLISECOND<br>
     * <br>
     * 
     * @param date
     * @param keyTime
     * @param valueToAdd
     * @return
     */
    public static Date addTime(Date date, int keyTime, int valueToAdd)
    {
        switch (keyTime)
        {
            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
            case Calendar.MINUTE:
            case Calendar.SECOND:
            case Calendar.MILLISECOND:
                break;

            default:
                System.out.println("el parametro enviado no es va�lido para la adicion de tiempo");
                return null;
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        int time = calendar.get(keyTime);
        calendar.set(keyTime, time + valueToAdd);

        return calendar.getTime();
    }

    /**
     * set the time: hours, minutes or seconds to the given date<br>
     * <br>
     * keyTime values:<br>
     * - Calendar.HOUR (12 hs AM/PM)<br>
     * - Calendar.HOUR_OF_DAY (24 hs)<br>
     * - Calendar.MINUTE<br>
     * - Calendar.SECOND<br>
     * - Calendar.MILLISECOND<br>
     * <br>
     * 
     * @param date
     * @param keyTime
     * @param newValue
     * @return
     */
    public static Date setTime(Date date, int keyTime, int newValue)
    {
        switch (keyTime)
        {
            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
            case Calendar.MINUTE:
            case Calendar.SECOND:
            case Calendar.MILLISECOND:
                break;

            default:
                System.out.println("el parametro enviado no es va�lido para fijar la hora");
                return null;
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(keyTime, newValue);

        return calendar.getTime();
    }

    /**
     * add time: hours, minutes and seconds to the given date<br>
     * <b>this method is enhanced for 24 hs only!</b><br>
     * if seconds are not send, the value is set to 0<br>
     * milliseconds go 00 always<br>
     * <br>
     * keyTime values:<br>
     * - Calendar.HOUR_OF_DAY (24 hs)<br>
     * - Calendar.MINUTE<br>
     * - Calendar.SECOND<br>
     * - Calendar.MILLISECOND<br>
     * 
     * @param date
     * @param hour
     * @param min
     * @param seg
     * @return
     */
    public static Date addTime(Date date, Integer hour, Integer min, Integer seg)
    {
        if (date == null)
        {
            return null;
        }

        if (hour != null)
        {
            date = addTime(date, Calendar.HOUR_OF_DAY, hour);
        }
        if (min != null)
        {
            date = addTime(date, Calendar.MINUTE, min);
        }
        if (seg == null)
        {
            seg = 0;
        }
        date = addTime(date, Calendar.MINUTE, seg);

        return addTime(date, Calendar.MILLISECOND, 0);

    }

    public static Integer getDay(Date date)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getMonth(Date date)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return (calendar.get(Calendar.MONTH) + 1);
    }

    public static Integer getYear(Date date)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static Integer getCurrentYear()
    {
        return getCalendar().get(Calendar.YEAR);
    }
    
    /**
     * retrieves the given date in the given format</br> </br> examples</br>
     * "EEE MMM dd HH:mm:ss zzz yyyy"</br> "yyyyMMdd HH:mm:ss" -> "20080227
     * 12:02:37"</br> </br>
     * 
     * @param date
     * @param format
     * @return
     */
    public static String getSpecialDateFormat(Date date, String format)
    {
        try
        {
            SimpleDateFormat dateformat = new SimpleDateFormat(format);
            return dateformat.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDate(String stringdate, String format)
    {
        try
        {
            SimpleDateFormat dateformat = new SimpleDateFormat(format);
            return dateformat.parse(stringdate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Date createDate(int day, int month, int year)
    {
        Calendar calendar = getCalendar();
        calendar.set(YEAR, year);
        calendar.set(MONTH, month - 1);

        try
        {
            calendar.set(DAY_OF_MONTH, day);
        }
        catch (Exception e)
        {
            /* try to get last day */
            day = getLastDayOfMonth(calendar);
            calendar.set(DAY_OF_MONTH, day);
        }
        return calendar.getTime();

    }

    public static boolean isInBetweenInclusive(Date compareAgainstDate, Date start, Date end)
    {
        if (compareAgainstDate != null && start != null && end != null)
        {
            if (start.before(end))
            {
                if (compareAgainstDate.getTime() >= start.getTime() && compareAgainstDate.getTime() <= end.getTime())
                {
                    return true;
                }
            }
        }
        else if (compareAgainstDate != null && start != null && end == null)
        {
            if (compareAgainstDate.getTime() >= start.getTime())
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isInBetweenExclusive(Date compareAgainstDate, Date start, Date end)
    {
        if (compareAgainstDate != null && start != null && end != null)
        {
            if (start.before(end))
            {
                if (compareAgainstDate.getTime() >= start.getTime() && compareAgainstDate.getTime() < end.getTime())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static Integer getHour12(Date date)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR);
    }

    public static String getHour12_AMPM(Date date)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        switch (calendar.get(Calendar.AM_PM))
        {
            case Calendar.AM:
                return "AM";
            case Calendar.PM:
                return "PM";
        }
        return null;
    }

    public static Integer getHour24(Date date)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static Integer getMinutes(Date date)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static Integer getSeconds(Date date)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    public static Date timestampToDate(Timestamp date)
    {
        if (date == null)
        {
            return null;
        }
        return new Date(date.getTime());
    }

    public static Timestamp dateToTimestamp(Date date)
    {
        if (date == null)
        {
            return null;
        }
        return (new Timestamp(date.getTime()));
    }

    public static Date setHourMinSecond(Date date, Integer hour, Integer min, Integer seg)
    {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, seg);
        return calendar.getTime();
    }

    public static String getMonthName(Integer month, Locale locale)
    {
        Calendar calendar = getCalendar();
        calendar.set(Calendar.MONTH, month - 1);
        String montName = calendar.getDisplayName(Calendar.MONTH, SimpleDateFormat.MONTH_FIELD, locale);
        return montName.substring(0, 1).toUpperCase() + montName.substring(1);
    }

    /**
     * retrieves the amount of days between the given dates
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static int getNumberOfDays(Date date1, Date date2)
    {
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        double numberOfDays = Math.floor((time2 - time1) / (1000 * 60 * 60 * 24));
        return ((int) numberOfDays);
    }

    /**
     * retrieves the amount of days between the given dates plus 1
     * 
     * @param fromdate
     * @param tilldate
     * @return
     */
    public static int getNumberOfDaysIncl(Date fromdate, Date tilldate)
    {
        long time1 = fromdate.getTime();
        long time2 = tilldate.getTime();
        double numberOfDays = Math.floor((time2 - time1) / (1000 * 60 * 60 * 24)) + 1;
        return ((int) numberOfDays);
    }

    public static int calculateAge(Date borndate)
    {
        return calculateAge(borndate, getToday());
    }

    public static int calculateAge(Date borndate, Date today)
    {
        GregorianCalendar cal1 = new GregorianCalendar();
        cal1.setTimeInMillis(borndate.getTime());
        GregorianCalendar cal2 = new GregorianCalendar();
        cal2.setTimeInMillis(today.getTime());

        return cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
    }

    /**
     * @param beginDate
     * @return int[] [years, months, days}
     * @return
     */
    public static int[] calculateCompleteAge(Date beginDate)
    {
        return calculateElapsedTime(beginDate, getToday());
    }

    /**
     * 
     * @param beginDate
     * @param today
     * @return int[] [years, months, days}
     */
    public static int[] calculateElapsedTime(Date beginDate, Date today)
    {
        GregorianCalendar cal1 = new GregorianCalendar();
        cal1.setTimeInMillis(beginDate.getTime());
        GregorianCalendar cal2 = new GregorianCalendar();
        cal2.setTimeInMillis(today.getTime());

        int years = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
        int months = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
        int days = cal2.get(Calendar.DAY_OF_MONTH) - cal1.get(Calendar.DAY_OF_MONTH);

        if (days < 0)
        {
            months--;
            days += cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (months < 0)
        {
            years--;
            months += 12;
        }

        int values[] = { years, months, days };

        return values;
    }

    public static String getLongPyDateFormat(Date date)
    {
        return getLongPyDateFormat(date, false);
    }

    public static String getLongPyDateFormat(Date date, boolean includeTime)
    {
        String format = "EEEEEE d 'de' MMMMM 'de' yyyy";
        if (includeTime)
        {
            format += " '-' HH:mm";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("es"));
        String dateString = sdf.format(date);
        dateString = dateString.substring(0, 1).toUpperCase() + dateString.substring(1);

        return dateString;
    }

    public static String getStringDateFormat(Date date)
    {
        return getLongPyDateFormat(date, false);
    }

    public static String getStringDateFormat(Date date, boolean includeTime)
    {
        String format = "d 'de' MMMMM 'de' yyyy";
        if (includeTime)
        {
            format += " '-' HH:mm";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("es"));
        String dateString = sdf.format(date);
        dateString = dateString.substring(0, 1).toUpperCase() + dateString.substring(1);

        return dateString;
    }

    /**
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isFromTillDatesOK(Date startDate, Date endDate)
    {
        if(endDate==null) return true;
        
        return (startDate.compareTo(endDate)>0);
    }

}
