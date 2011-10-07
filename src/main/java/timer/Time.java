package timer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Класс, описывающий время
 * 
 * @author Mironov
 * 
 */
public class Time implements Cloneable, Comparable<Time> {
	public final static int MILLISECONDS_IN_SECOND = 1000;
	public final static int SECONDS_IN_MINUTE = 60;
	public final static int MINUTES_IN_HOUR = 60;
	public final static int HOURS_IN_DAY = 24;

	private int seconds;
	private int minutes;
	private int hours;

	private static String timeUnitToString(int value) {
		String tmp = "";
		if (value >= 0 && value <= 9) {
			tmp = "0";
		}
		return tmp + String.valueOf(value);
	}

	public static Time parse(String str) throws ParseException {
		DateFormat df = new SimpleDateFormat("hh:mm:ss");
		Time time = new Time();
		time.fromDate(df.parse(str));
		return time;
	}

	public static Time interval(Time begin, Time end) {
		Time result = new Time();
		result.converFromSeconds(end.convertToSeconds()
				- begin.convertToSeconds());
		return result;
	}

	public Time() {
		this(0, 0, 0);
	}

	public Time(int hours, int minutes, int seconds) {
		setHours(hours);
		setMinutes(minutes);
		setSeconds(seconds);
	}

	public int getSeconds() {
		return this.seconds;
	}

	public void setSeconds(int seconds) {
		if (seconds >= 0 && seconds < SECONDS_IN_MINUTE) {
			this.seconds = seconds;
		}
	}

	public int getMinutes() {
		return this.minutes;
	}

	public void setMinutes(int minutes) {
		if (minutes >= 0 && minutes < MINUTES_IN_HOUR) {
			this.minutes = minutes;
		}
	}

	public int getHours() {
		return this.hours;
	}

	public void setHours(int hours) {
		if (hours >= 0 && hours < HOURS_IN_DAY) {
			this.hours = hours;
		}
	}

	public void addHours(int hours) {
		this.hours = (this.hours + hours) % HOURS_IN_DAY;
	}

	public void addMinutes(int minutes) {
		addHours((this.minutes + minutes) / MINUTES_IN_HOUR);
		this.minutes = (this.minutes + minutes) % MINUTES_IN_HOUR;
	}

	public void addSeconds(int seconds) {
		addMinutes((this.seconds + seconds) / SECONDS_IN_MINUTE);
		this.seconds = (this.seconds + seconds) % SECONDS_IN_MINUTE;
	}

	public int convertToSeconds() {
		return this.hours * MINUTES_IN_HOUR * SECONDS_IN_MINUTE + this.minutes
				* SECONDS_IN_MINUTE + this.seconds;
	}

	public void converFromSeconds(int seconds) {
		this.seconds = seconds % SECONDS_IN_MINUTE;
		this.hours = seconds / (MINUTES_IN_HOUR * SECONDS_IN_MINUTE);
		this.minutes =
				seconds / SECONDS_IN_MINUTE - this.hours * MINUTES_IN_HOUR;
	}

	public String toString() {
		return timeUnitToString(this.hours) + ":"
				+ timeUnitToString(this.minutes) + ":"
				+ timeUnitToString(this.seconds);
	}

	public Date toDate() {
		return new GregorianCalendar(0, 0, 0, hours, minutes, seconds)
				.getTime();
	}

	public void fromDate(Date time) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(time);
		setHours(cal.get(Calendar.HOUR_OF_DAY));
		setMinutes(cal.get(Calendar.MINUTE));
		setSeconds(cal.get(Calendar.SECOND));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Time)) {
			return false;
		}
		Time time = (Time) obj;
		if (this.hours == time.hours && this.minutes == time.minutes
				&& this.seconds == time.seconds) {
			return true;
		}
		return false;
	}

	@Override
	public Object clone() {
		Time time = new Time(getHours(), getMinutes(), getSeconds());
		return time;
	}

	public int compareTo(Time time) {
		if (equals(time)) {
			return 0;
		}
		if (this.hours < time.hours || this.minutes < time.minutes
				|| this.seconds < time.seconds) {
			return -1;
		}
		return 1;
	}
}
