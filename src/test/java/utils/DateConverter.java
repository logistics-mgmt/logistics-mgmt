package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by owen on 26/03/16.
 */
public abstract class DateConverter {

	public static Date createDate(String date){
		/**
		 * Creates java.util.Date object from string in yyyy-MM-dd format.
		 */
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			return dateFormat.parse(date);
		}
		catch (ParseException e) {
			return null;
		}
	}

	public static Date createDate(String date, String format){
		/**
		 * Creates java.util.Date object from string in given format.
		 */
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		try {
			return dateFormat.parse(date);
		}
		catch (ParseException e) {
			return null;
		}
	}
}
