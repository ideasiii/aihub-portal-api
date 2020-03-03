package iii.aihub.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class DateTimeUtils {

	public static Logger logger = LoggerFactory.getLogger(DateTimeUtils.class);

	public static void main(String[] args){
		// 過去24hr與前一個24hr
		DateTime todayEnd = new DateTime();
		System.out.println("todayEnd: "+todayEnd);
		DateTime todayBegin = todayEnd.minusHours(24);
		System.out.println("todayBegin: "+todayBegin);
		DateTime lastDay = todayBegin.minusHours(24);
		System.out.println("lastDay: "+lastDay);
	}

	public static String TimeZoneID = "Asia/Taipei";

	public static String DateFormatString = "yyyy-MM-dd";

	public static DateTime getDateTime(Timestamp ts){
		return new DateTime(ts, DateTimeZone.forID(TimeZoneID));
	}

	public static long datePeriodByDay(Date firstDate, Date secondDate){
		long diff = Math.abs( secondDate.getTime() - firstDate.getTime());
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	public static Date parseToyyyyMMddDate(String dateString) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormatString);

		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			logger.error(e.getLocalizedMessage(),e);
			throw new Exception(e.getLocalizedMessage());
		}
	}

	public static DateTime parseToyyyyMMddDateTime(String dateString) throws Exception {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(DateFormatString);
		DateTime dt = DateTime.parse(dateString, formatter);
		return dt;
	}

	public static String formatToyyyyMMddDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormatString);
		return sdf.format(date);

	}

	public static java.sql.Date toSQLDate(Date date){
		return new java.sql.Date(date.getTime());
	}

	public static java.sql.Date toSQLDate(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormatString);
		return new java.sql.Date(sdf.parse(dateString).getTime());
	}

	/**
	 * 比較時間格式為 yyyy-MM-dd 的兩個日期是否一樣
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static Boolean isSameDay(Date day1, Date day2){
		SimpleDateFormat sdf = new SimpleDateFormat(DateFormatString);
		String d1 = sdf.format(day1);
		String d2 = sdf.format(day2);
		return d1.equalsIgnoreCase(d2);
	}
}
