package se.inera.fmu.application.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
/**
 * Utility Class for handling business days calculations considering if days in period is weekend or holiday
 * */
public class BusinessDaysUtil {
	
	//Set up fixed day holidays
	private static final Set<Holiday> fixedDayHolidays;
	static {
        Set<Holiday> aSet = new HashSet();
        aSet.add(new Holiday(1, 1)); //Nyårsdagen
        //aSet.add(new Holiday(1, 5)); //Trettondagsafton, not a public holiday but many workplaces have shortened workday, -4 hours. TODO: investigate if it should be included 
        aSet.add(new Holiday(1, 6)); //Trettondagen
        //aSet.add(new Holiday(4, 30)); //Valborgsmässoafton, not a public holiday but many workplaces have shortened workday, -4 hours. TODO: investigate if it should be included
        aSet.add(new Holiday(5, 1)); //Första maj 
        aSet.add(new Holiday(6, 6)); //Sveriges nationaldag
        aSet.add(new Holiday(12, 24)); //Julafton 
        aSet.add(new Holiday(12, 25));//Juldagen
        aSet.add(new Holiday(12, 26)); //Annadag jul
        aSet.add(new Holiday(12, 31)); //Nyårsafton
        fixedDayHolidays = Collections.unmodifiableSet(aSet);
    }
	
	//Set up floating day holidays when first needed. i.e. when the floting holidays or a specific year is needed a set is created and added to the map for further use.
	private static final Map<Integer,Set<Holiday>> floadtingDayHolidaysPerYearMap = new HashMap<Integer,Set<Holiday>>();
	private static Set<Holiday> createFloatingHolidaysForYear(Integer year) {
		Set<Holiday> floatingHolidaysForYear = new HashSet<Holiday>(); 
		
		//Påskdagen
		LocalDate easterSunday = getEasterSunday(year);
		floatingHolidaysForYear.add(new Holiday(easterSunday));
		
		//Skärtorsdagen - Thursday preceding Easter Sunday, not a public holiday but many workplaces have shortened workday, -4 hours. TODO: investigate if it should be included
		//floatingHolidaysForYear.add(new Holiday(easterSunday.minusDays(DateTimeConstants.SUNDAY - DateTimeConstants.THURSDAY)));

		//Långfredagen - Friday preceding Easter Sunday
		floatingHolidaysForYear.add(new Holiday(easterSunday.minusDays(DateTimeConstants.SUNDAY - DateTimeConstants.FRIDAY)));
		
		//Påskafton - Saturday preceding Easter Sunday
		floatingHolidaysForYear.add(new Holiday(easterSunday.minusDays(DateTimeConstants.SUNDAY - DateTimeConstants.SATURDAY)));
		
		//Annandagen - Monday following Easter Sunday
		floatingHolidaysForYear.add(new Holiday(easterSunday.plusDays(DateTimeConstants.MONDAY)));
		
		//Kristi himmelsfärdsdag - Sixth Thursday following Easter Sunday
		floatingHolidaysForYear.add(new Holiday(easterSunday.plusDays(DateTimeConstants.THURSDAY).plusWeeks(5)));
		
		//Pingstdagen - Seventh Sunday following Easter Sunday 
		LocalDate pentecost = easterSunday.plusWeeks(7);
		floatingHolidaysForYear.add(new Holiday(pentecost));
		
		//Pingstafton - Saturday preciding Pentecost, will be excluded as businessDay since its always a saturday, and not added since its not at public holiday, but many workplaces have shortened workday, -4 hours. TODO: investigate if it should be included
		//floatingHolidaysForYear.add(new Holiday(pentecost.minusDays(DateTimeConstants.SUNDAY - DateTimeConstants.SATURDAY)));
		
		//Midsommardagen - Saturday between 20/6 - 26/6, will be excluded as businessDay since its always a saturday, but added anyway since its a public holiday
		LocalDate midSummerDay = calcNextSaturday(new LocalDate(year,6,20));
		floatingHolidaysForYear.add(new Holiday(midSummerDay));
		
		//Midsommarafton - Friday preceding MidsummersDay,  is not a public holiday in Sweden, but in the Banking Act and other legislative texts equated it (as well as Christmas Eve, New Year's Eve and Easter Eve) with a public holiday. 
		floatingHolidaysForYear.add(new Holiday(midSummerDay.minusDays(DateTimeConstants.SATURDAY - DateTimeConstants.FRIDAY)));
		
		//Alla helgons dag - Saturday between 31/10 - 6/11
		LocalDate allSaintsDay = calcNextSaturday(new LocalDate(year,10,31));
		floatingHolidaysForYear.add(new Holiday(allSaintsDay));
		
		//Allhelgonaafton - Friday preceding all 19/6 - 25/6, No holiday but less workinghours during day. TODO: investigate if it should be included
		//floatingHolidaysForYear.add(new Holiday(allSaintsDay.minusDays(DateTimeConstants.SATURDAY - DateTimeConstants.FRIDAY)));

		floadtingDayHolidaysPerYearMap.put(year, Collections.unmodifiableSet(floatingHolidaysForYear));
		
		return floadtingDayHolidaysPerYearMap.get(year);
	}
	
	
	/**
	 * Method for calculating the date for easter sunday for a specific year
	 * The method uses the Carl Friedrich Gauss algorithm http://en.wikipedia.org/wiki/Computus#Gauss_algorithm
	 * 
	 */
	private static LocalDate getEasterSunday(int year) {

        int initialYear = year;

        if (year < 1900) {
            year += 1900;
        }

        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int j = c % 4;
        int k = (32 + 2 * e + 2 * i - h - j) % 7;
        int l = (a + 11 * h + 22 * k) / 451;
        int m = (h + k - 7 * l + 114) % 31;

        int month = (h + k - 7 * l + 114) / 31 - 1;
        int day = m + 1;
        
        return new LocalDate(initialYear, month+1, day);

    }

	private static LocalDate calcNextSaturday(LocalDate date){
		
		if (date.getDayOfWeek() > DateTimeConstants.SATURDAY) {
	        date = date.plusWeeks(1);
	    }
	    return date.withDayOfWeek(DateTimeConstants.SATURDAY);

	}

	/**
	 * Utility method for calculating number of business days between two dates considering weekends and swedish holidays
	 * The to date is exclusive
	 * */
	public static int numberOfBusinessDays(@NotNull LocalDate from, @NotNull LocalDate to){
		int numberOfBusinessDays = 0;
		
		//Iterate over all dates in the period, exluding the 'to' date, and check if is a business day 
		for (LocalDate date = from; date.isBefore(to); date = date.plusDays(1))
		{
		    if(isBusinessDay(date)){
		    	numberOfBusinessDays++;
		    }
		}
		
		return numberOfBusinessDays; 
	}
	
	
	/**
	 * Utility method for checking if a specific date is considered a business day considering weekends and swedish holidays
	 * The to date is exclusive
	 * */
	public static boolean isBusinessDay(LocalDate date){
	
		if(isWeekend(date)){
			return false;
		}
		
		if(isHoliday(date)){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Utility method for checking if a specific date is a swedish holiday
	 * The to date is exclusive
	 * */
	public static boolean isHoliday(LocalDate date ){
		Holiday day = new Holiday(date);
		
		if(fixedDayHolidays.contains(day)){
			return true;
		}
		
		Set<Holiday> floatingHolidaysForYear = getFloatingHolidaysForYear(date.getYear());

		if(floatingHolidaysForYear.contains(day)){
			return true;
		}
		
		return false;
	}


	private static boolean isWeekend(LocalDate date) {
		return (date.getDayOfWeek() == DateTimeConstants.SATURDAY || date.getDayOfWeek() == DateTimeConstants.SUNDAY);
	}

	
	private static Set<Holiday> getFloatingHolidaysForYear(int year) {
		Set<Holiday> floatingHolidaysForYear = floadtingDayHolidaysPerYearMap.get(Integer.valueOf(year));
		
		if(floatingHolidaysForYear == null){
			floatingHolidaysForYear = createFloatingHolidaysForYear(Integer.valueOf(year));
		}
		return floatingHolidaysForYear;
	}

	
	private static class Holiday{
        private int month;
        private int day;

        protected Holiday(int month, int day) {
            this.month = month;
            this.day = day;
        }

        protected Holiday(LocalDate date) {
            this.month = date.getMonthOfYear();
            this.day = date.getDayOfMonth();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Holiday && obj.hashCode() == hashCode();
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new Object[] { month, day });
        }
        
        @Override
        public String toString(){
        	
        	return "["+month+"/"+day+"]";
        }
    }
}
