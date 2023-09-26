package personalFinanceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * This class contains static method to handle dates.
 * @author Ajay
 */
public class DateUtil {
	public static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	/*
	 * This method converts string-date into Date object
	 * dateAsString string format date (ex. 26/09/2023 : DD/MM/YYYY)
	 * @return returns a Date objects
	 */
	public static Date stringToDate(String dateAsString) {
		try {
			SimpleDateFormat df=new SimpleDateFormat("dd/MM/YYYY");
			return df.parse(dateAsString);
		}catch(ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/*
	 * This method converts Date object to string
	 * @param date Date object to be converted to string
	 * @return String date in DD/MM/YYYY
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat df=new SimpleDateFormat("dd/MM/YYYY");
		return df.format(date);
	}
	public static String getYearAndMonth(Date date) {
		SimpleDateFormat df=new SimpleDateFormat("YYYY,MM");
		return df.format(date);
	}
	public static Integer getYear(Date date) {
		SimpleDateFormat df=new SimpleDateFormat("YYYY");
		return Integer.parseInt(df.format(date));
	}
	public static String getMonthName(Integer monthNo) {
		return MONTHS[monthNo-1];
	}
}
