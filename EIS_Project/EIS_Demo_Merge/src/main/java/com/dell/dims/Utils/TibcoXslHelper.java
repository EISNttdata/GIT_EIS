package com.dell.dims.Utils;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TibcoXslHelper
{
    public static Date parseDateTime(String format, String inputDate) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String strDate = sdf.format(inputDate);
        return new Date(strDate);
        //return Date.parseExact(inputDate, format, null);
    }

    public static String formatDateTime(String format, Date inputDate) throws Exception {
       return inputDate.toString();
        // return String.Format(format, inputDate);
    }

    public static Date addToDate(Date inputDate, int yearToAdd, int monthToAdd, int dayToAdd) throws Exception {

        //Date d1 = new Date();
        Calendar cl = Calendar. getInstance();
        cl.setTime(inputDate);

        cl. add(Calendar.DAY_OF_MONTH, monthToAdd);
        cl. add(Calendar.MONTH, monthToAdd);
        cl. add(Calendar.YEAR, yearToAdd);

        return cl.getTime();

       // inputDate.AddYears(yearToAdd);
       // inputDate.AddMonths(monthToAdd);
        //return inputDate.AddDays(dayToAdd);

    }

    public static Double parseNumber(String numberInString) throws Exception {

        return Double.parseDouble(numberInString);
        //return double.parse(numberInString);
    }

    public static String concat(Object... list) throws Exception {

        String concatedStr="";
        for(Object obj : list)
        {
            concatedStr=concatedStr.concat(obj.toString());
        }

        return concatedStr;
    }

    public static int round(int nb) throws Exception {
        return nb;
    }

    public static int count(List myCollection) throws Exception {
        return myCollection.size();
    }

    public static boolean startsWith(String myString, String prefixToCheck) throws Exception {
        return myString.startsWith(prefixToCheck);
    }

    public static int round(double nb) throws Exception {

        return Double.valueOf(nb).intValue();
        //return convert.ToInt32(Math.round(nb));
    }

    public static boolean contains(String value, String inputString) throws Exception {
        return inputString.contains(value);
    }

    public static int indexOf(String inputString, String stringToFind) throws Exception {
        return inputString.indexOf(stringToFind);
    }

    // usage of exists : exists ('this string', mycollection)
    public static <T>boolean exist(T value, List<T> collection) throws Exception {
        return collection.contains(value);
    }

    // usage of translate : translate (myvaraible/value, '&#xA;', '')
    public static String translate(String inputString, String oldstring, String newstring) throws Exception {
        return inputString.replace(oldstring, newstring);
    }

    // usage of string-length : string-length (myvariable)
    public static int stringLength(String inputString) throws Exception {
        return inputString.length();
    }

    // usage of Left : left(myvariable,3)
    public static String left(String inputString, int length) throws Exception {
        return inputString.substring(0, length);
    }

    // usage of substring : substring(myvariable,3,5)
    public static String substring(String inputString, int startindex, int endindex) throws Exception {
        return inputString.substring(startindex - 1, endindex - 1);
    }

    public static String renderXml(Object dataToSerialize) throws Exception {
        return renderXml(dataToSerialize,true);
    }

    //usage a string, usage sample : tib:render-xml(myvariable, true())
    public static String renderXml(Object dataToSerialize, boolean isSomething) throws Exception {
        if (dataToSerialize == null)
        {
            return null;
        }
        // WRITE JAVA SPECIFIC CODE TO Serialise to XML

        /*using (StringWriter stringwriter = new System.IO.StringWriter())
        {
            var serializer = new XmlSerializer(dataToSerialize.GetType());
            serializer.Serialize(stringwriter, dataToSerialize);
            return stringwriter.ToString();
        }*/

        return dataToSerialize.toString();
    }

    // usage tib:trim : tib:trim(myvariable)
    public static String trim(String inputString) throws Exception {
        return inputString.trim();
    }

    // usage tib:tokenize : tib:tokenize("tot1;tot2", ";")
    public static String[] tokenize(String inputString, String delimiter) throws Exception {
        return inputString.split(delimiter);
    }

    // usage tib:translate-timezone( : tib:translate-timezone(date, "UTC")
    // TODO find usage exemple
    public static Date translateTimezone(Date date, String timezone) throws Exception {

        //creating DateFormat for converting time from local timezone to GMT
        DateFormat converter = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");

        if(timezone.equalsIgnoreCase("UTC"))
        {
            converter.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        else
        {
            System.out.println("Not implemented.");
           // throw new NotImplementedException();
        }

        System.out.println("local time : " + date);
        System.out.println("time in GMT : " + converter.format(date));


        return new Date(converter.format(date).toString());

        /*TimeZoneInfo destinationTimeZone = new TimeZoneInfo();
        if (StringSupport.equals(timezone, "UTC"))
        {
            destinationTimeZone = TimeZoneInfo.Utc;
        }
        else
        {
            throw new NotImplementedException();
        }
        return TimeZoneInfo.ConvertTime(date, destinationTimeZone);*/
    }

    // usage tib:compare-date( : tib:compare-date(date1, date2) , return 0 if equals
    //expression = expression.Replace("tib:compare-date(","TibcoXslHelper.CompareDate(");
    public static int compareDate(Date date1, Date date2) throws Exception {
        return date1.compareTo(date2);
    }

    // usage upper-case : upper-case (mystring)
    public static String upperCase(String inputString) throws Exception {
        return inputString.toUpperCase();
    }

    public static String lowerCase(String inputString) throws Exception {
        return inputString.toLowerCase();
    }

    // tib:validate-dateTime(<<format>>, <<string>>) return bool
    public static boolean validateDateTime(String format, String inputDate) throws Exception {
        try
        {

            SimpleDateFormat sdf = new SimpleDateFormat(format);

            String strDate = sdf.format(inputDate);


            //Date.parseExact(inputDate, format, null);
        }
        catch (Exception ex)
        {
            return false;
        }

        return true;
    }

    // return a string, usage sample : tib:string-round-fraction($Start/root/inputdata, 2)
    // for exemple tib:string-round-fraction(round(1.100), 2) Output as 1.00

    public static String stringRoundFraction(String myNumber, int nbDecimal) throws Exception {

    BigDecimal bigDecimal = new BigDecimal(myNumber);
    bigDecimal = bigDecimal.setScale(nbDecimal,BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString();

        //        return bigDecimal.doubleValue();
        //return Math.round(Double.parse(myNumber), nbDecimal).ToString(CultureInfo.InvariantCulture);
    }

}


