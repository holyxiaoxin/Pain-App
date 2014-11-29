package com.riandy.flexiblealertscheduling;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.net.Uri;

public class AlarmModel {

	public static final int SUNDAY = 0;
	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRDIAY = 5;
	public static final int SATURDAY = 6;
	
	public long id = -1;
	public int timeHour;
	public int timeMinute;
	private boolean repeatingDays[];
	public boolean repeatWeekly;
	public Uri alarmTone;
	public String name;
	public boolean isEnabled;
	//addition 
	public Date startDate;
	public Date endDate;
	
	public AlarmModel() {
		repeatingDays = new boolean[7];
		
		Calendar c1 = GregorianCalendar.getInstance();
		c1.set(2011, 10, 30);
		startDate = c1.getTime();
		Calendar c2 = GregorianCalendar.getInstance();
		c2.set(2015, 10, 30);		
		endDate = c2.getTime();
		//Log.d("SQL Alarm Model start",startDate.toString() + " " + endDate.toString());
	}
	
	public void setRepeatingDay(int dayOfWeek, boolean value) {
		repeatingDays[dayOfWeek] = value;
	}
	
	public boolean getRepeatingDay(int dayOfWeek) {
		return repeatingDays[dayOfWeek];
	}
	
}
