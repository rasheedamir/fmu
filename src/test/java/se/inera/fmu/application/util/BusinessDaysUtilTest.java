package se.inera.fmu.application.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.joda.time.LocalDate;
import org.junit.Test;

/**
 * <p>Test for BusinessDaysUtil.</p>
 *
 * @author Rickard Hjortling, R2M
 */
public class BusinessDaysUtilTest {
	
	/**
	 * Test if a date is a fixed date swedish holiday.
	 */
	@Test
	public void testIsHolidayFixed() {
		
		//Fixed dates in 2014
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,1,1))); //Nyårsdagen 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,1,2)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,1,5)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,1,6))); ////Trettondagen
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,1,7)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,5,1))); ////Första maj 
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,6,6))); //Sveriges nationaldag
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,11,15)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,12,24))); //Julafton 
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,12,25))); //Juldagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,12,24))); //Annadag jul
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,12,31))); //Nyårsafton

		//Fixed dates in 2015
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,1,1))); //Nyårsdagen 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,1,2)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,1,5)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,1,6))); ////Trettondagen
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,1,7)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,5,1))); ////Första maj 
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,6,6))); //Sveriges nationaldag
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,11,15)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,12,24))); //Julafton 
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,12,25))); //Juldagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,12,24))); //Annadag jul
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,12,31))); //Nyårsafton
		
	}
	@Test
	public void testIsHolidayFloating() {
		
		//Floating dates in 2014
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,4,18))); //Långfredagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,4,19))); //Påskafton
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,4,20))); //Påskdagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,4,21))); //Annandag påsk
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,5,29))); //Kristi himmelsfärdsdag
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,6,8))); //Pingstdagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,6,20))); //Midsommarafton
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,6,21))); //Midsommardagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,11,1))); //Alla helgons dag

		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,4,3))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,4,4))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,4,5))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,4,6))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,5,14)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,5,24)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,6,19)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,10,31)));

		//Floating dates in 2015
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,3))); //Långfredagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,4))); //Påskafton
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,5))); //Påskdagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,6))); //Annandag påsk
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,5,14))); //Kristi himmelsfärdsdag
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,5,24))); //Pingstdagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,6,19))); //Midsommarafton
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,6,20))); //Midsommardagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,10,31))); //Alla helgons dag

		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,18))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,19))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,20))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,21))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,5,29))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,6,8))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,6,21)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,11,1)));
	}
	
	/**
	 * Test number of business days between two dates.
	 */
	@Test
	public void testNoOfBusinessDays() {
		//Week without holidays
		assertEquals(5, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,1,27), new LocalDate(2014,02,03)));
		
		//Easter week 2014 4 workingdays
		assertEquals(4, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,4,14), new LocalDate(2014,04,21)));
		
		//Easter week 2015 4 workingdays
		assertEquals(4, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2015,3,30), new LocalDate(2015,04,6)));
		
		//Christmas week 2014
		assertEquals(2, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,12,22), new LocalDate(2014,12,29)));

		//Christmas week 2015
		assertEquals(3, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2015,12,21), new LocalDate(2015,12,28)));
		
		//Christmas holiday 2014, 9 workingdays
		assertEquals(9, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,12,22), new LocalDate(2015,1,12)));

		//Christmas holiday 2015
		assertEquals(10, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2015,12,21), new LocalDate(2016,1,11)));

	}


}
