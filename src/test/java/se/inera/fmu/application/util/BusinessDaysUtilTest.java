package se.inera.fmu.application.util;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.joda.time.LocalDate;
import static org.joda.time.DateTimeConstants.*;
import org.junit.Test;

/**
 * <p>Test for BusinessDaysUtil.</p>
 *
 * @author Rickard Hjortling, R2M
 */
@SuppressWarnings(value = { "checkstyle:magicnumber" })
public class BusinessDaysUtilTest {
	
	/**
	 * Test if a date is a fixed date swedish holiday.
	 */
	@Test
	public void testIsHolidayFixed() {
		
		//Fixed dates in 2014
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,JANUARY,1))); //Nyårsdagen 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,JANUARY,5)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,JANUARY,6))); ////Trettondagen
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,JANUARY,7)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,MAY,1))); ////Första maj 
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,JUNE,6))); //Sveriges nationaldag
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,NOVEMBER,15)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,DECEMBER,24))); //Julafton 
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,DECEMBER,25))); //Juldagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,DECEMBER,24))); //Annadag jul
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,DECEMBER,31))); //Nyårsafton

		//Fixed dates in 2015
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JANUARY,1))); //Nyårsdagen 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,JANUARY,5)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JANUARY,6))); ////Trettondagen
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,JANUARY,7)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,MAY,1))); ////Första maj 
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JUNE,6))); //Sveriges nationaldag
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,NOVEMBER,15)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,24))); //Julafton 
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,25))); //Juldagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,24))); //Annadag jul
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,31))); //Nyårsafton
		
	}
	@Test
	public void testIsHolidayFloating() {
		
		//Floating dates in 2014
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,APRIL,18))); //Långfredagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,APRIL,19))); //Påskafton
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,APRIL,20))); //Påskdagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,APRIL,21))); //Annandag påsk
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,MAY,29))); //Kristi himmelsfärdsdag
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,JUNE,8))); //Pingstdagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,JUNE,20))); //Midsommarafton
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,JUNE,21))); //Midsommardagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2014,NOVEMBER,1))); //Alla helgons dag

		LocalDate day = new LocalDate(2014,JULY,14);
		LocalDate backToWork = new LocalDate(2014,JULY,28);
		
		while(day.isBefore(backToWork)){
			assertFalse(BusinessDaysUtil.isBusinessDay(day)); //Industrisemester
			day = day.plusDays(1);
		}
		assertFalse(BusinessDaysUtil.isHoliday(day));	//Test that following and preceding days are not included
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,JULY,6))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,JULY,4))); 
		
		
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,APRIL,3))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,APRIL,4))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,APRIL,5))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,APRIL,6))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,MAY,14)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,MAY,24)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,JUNE,19)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,OCTOBER,31)));
		
		

		//Floating dates in 2015
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,APRIL,3))); //Långfredagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,APRIL,4))); //Påskafton
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,APRIL,5))); //Påskdagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,APRIL,6))); //Annandag påsk
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,MAY,14))); //Kristi himmelsfärdsdag
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,MAY,24))); //Pingstdagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JUNE,19))); //Midsommarafton
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JUNE,20))); //Midsommardagen
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,OCTOBER,31))); //Alla helgons dag

		day = new LocalDate(2015,JULY,13);
		backToWork = new LocalDate(2015,JULY,27);
		
		while(day.isBefore(backToWork)){
			assertFalse(BusinessDaysUtil.isBusinessDay(day)); //Industrisemester
			day = day.plusDays(1);
		}
		assertFalse(BusinessDaysUtil.isHoliday(day));	//Test that following and preceding days are not included
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,JULY,5))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,JULY,3))); 

		
		
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,APRIL,18))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,APRIL,19))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,APRIL,20))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,APRIL,21))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,MAY,29))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,JUNE,8))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,JUNE,21)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,NOVEMBER,1)));
	}

	@Test
	public void testSkippableWeeks() {
		//Floating dates in 2015
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JANUARY,1))); 
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JANUARY,2)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,JANUARY,5)));
		
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JULY,13)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JULY,14)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JULY,15)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JULY,16)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JULY,17)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JULY,20)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JULY,21)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JULY,22)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JULY,23)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,JULY,24)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,JULY,27)));
		
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,18)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,19)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,20)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,21)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,22)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,23)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,24)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,25)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,26)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,27)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,28)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,29)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,30)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,DECEMBER,31)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2016,JANUARY,1)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2016,JANUARY,2)));
	}

	
	/**
	 * Test number of business days between two dates.
	 */
	@Test
	public void testNoOfBusinessDays() {
		//Week without holidays
		assertEquals(5, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,JANUARY,27), new LocalDate(2014,FEBRUARY,3)));
		
		//Easter week 2014 4 working days
		assertEquals(4, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,APRIL,14), new LocalDate(2014,APRIL,21)));
		
		//Easter week 2015 4 working days
		assertEquals(4, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2015,MARCH,30), new LocalDate(2015,APRIL,6)));
		
		//Christmas week 2014
		assertEquals(0, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,DECEMBER,22), new LocalDate(2014,DECEMBER,29)));

		//Christmas week 2015
		assertEquals(0, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2015,DECEMBER,21), new LocalDate(2015,DECEMBER,28)));
		
		//Christmas holiday 2014
		assertEquals(4, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,DECEMBER,22), new LocalDate(2015,JANUARY,12)));

		//Christmas holiday 2015
		assertEquals(0, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2015,DECEMBER,21), new LocalDate(2016,JANUARY,11)));

	}

	/**
	 * Test number of business days between two dates.
	 */
	@Test
	public void testCalculateLastBusinessDayDate() {
		
		assertEquals(new LocalDate(2014,OCTOBER,17),BusinessDaysUtil.calculateBusinessDayDate(new LocalDate(2014,OCTOBER,13),5));

		assertEquals(new LocalDate(2014,OCTOBER,20),BusinessDaysUtil.calculateBusinessDayDate(new LocalDate(2014,OCTOBER,13),6));

		assertEquals(new LocalDate(2014,NOVEMBER,14),BusinessDaysUtil.calculateBusinessDayDate(new LocalDate(2014,OCTOBER,13),25));

		
	}


	
	
	@Test(expected=IllegalArgumentException.class)
	public void testIsBusinessDayNullValues() {
		BusinessDaysUtil.isBusinessDay(null);
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testIsHolidayNullValue() {
		BusinessDaysUtil.isHoliday(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNumberOfBusinessDaysNullValues() {
		BusinessDaysUtil.numberOfBusinessDays(null, null);
	}
	
	
	@Test
	public void testSameDay(){
		
		assertEquals(0, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,OCTOBER,16), new LocalDate(2014,OCTOBER,16)));
	}

	@Test
	public void testEasterSunday(){
		
		assertEquals(new LocalDate(1900,APRIL,15), BusinessDaysUtil.getEasterSunday(1900));
		assertEquals(new LocalDate(1901,APRIL,7), BusinessDaysUtil.getEasterSunday(1901));
		assertEquals(new LocalDate(1902,MARCH,30), BusinessDaysUtil.getEasterSunday(1902));
		assertEquals(new LocalDate(1903,APRIL,12), BusinessDaysUtil.getEasterSunday(1903));
		assertEquals(new LocalDate(1904,APRIL,3), BusinessDaysUtil.getEasterSunday(1904));
		assertEquals(new LocalDate(1905,APRIL,23), BusinessDaysUtil.getEasterSunday(1905));
		assertEquals(new LocalDate(1906,APRIL,15), BusinessDaysUtil.getEasterSunday(1906));
		assertEquals(new LocalDate(1907,MARCH,31), BusinessDaysUtil.getEasterSunday(1907));
		assertEquals(new LocalDate(1908,APRIL,19), BusinessDaysUtil.getEasterSunday(1908));
		assertEquals(new LocalDate(1909,APRIL,11), BusinessDaysUtil.getEasterSunday(1909));
		assertEquals(new LocalDate(1910,MARCH,27), BusinessDaysUtil.getEasterSunday(1910));
		assertEquals(new LocalDate(1911,APRIL,16), BusinessDaysUtil.getEasterSunday(1911));
		assertEquals(new LocalDate(1912,APRIL,7), BusinessDaysUtil.getEasterSunday(1912));
		assertEquals(new LocalDate(1913,MARCH,23), BusinessDaysUtil.getEasterSunday(1913));
		assertEquals(new LocalDate(1914,APRIL,12), BusinessDaysUtil.getEasterSunday(1914));
		assertEquals(new LocalDate(1915,APRIL,4), BusinessDaysUtil.getEasterSunday(1915));
		assertEquals(new LocalDate(1916,APRIL,23), BusinessDaysUtil.getEasterSunday(1916));
		assertEquals(new LocalDate(1917,APRIL,8), BusinessDaysUtil.getEasterSunday(1917));
		assertEquals(new LocalDate(1918,MARCH,31), BusinessDaysUtil.getEasterSunday(1918));
		assertEquals(new LocalDate(1919,APRIL,20), BusinessDaysUtil.getEasterSunday(1919));
		assertEquals(new LocalDate(1920,APRIL,4), BusinessDaysUtil.getEasterSunday(1920));
		assertEquals(new LocalDate(1921,MARCH,27), BusinessDaysUtil.getEasterSunday(1921));
		assertEquals(new LocalDate(1922,APRIL,16), BusinessDaysUtil.getEasterSunday(1922));
		assertEquals(new LocalDate(1923,APRIL,1), BusinessDaysUtil.getEasterSunday(1923));
		assertEquals(new LocalDate(1924,APRIL,20), BusinessDaysUtil.getEasterSunday(1924));
		assertEquals(new LocalDate(1925,APRIL,12), BusinessDaysUtil.getEasterSunday(1925));
		assertEquals(new LocalDate(1926,APRIL,4), BusinessDaysUtil.getEasterSunday(1926));
		assertEquals(new LocalDate(1927,APRIL,17), BusinessDaysUtil.getEasterSunday(1927));
		assertEquals(new LocalDate(1928,APRIL,8), BusinessDaysUtil.getEasterSunday(1928));
		assertEquals(new LocalDate(1929,MARCH,31), BusinessDaysUtil.getEasterSunday(1929));
		assertEquals(new LocalDate(1930,APRIL,20), BusinessDaysUtil.getEasterSunday(1930));
		assertEquals(new LocalDate(1931,APRIL,5), BusinessDaysUtil.getEasterSunday(1931));
		assertEquals(new LocalDate(1932,MARCH,27), BusinessDaysUtil.getEasterSunday(1932));
		assertEquals(new LocalDate(1933,APRIL,16), BusinessDaysUtil.getEasterSunday(1933));
		assertEquals(new LocalDate(1934,APRIL,1), BusinessDaysUtil.getEasterSunday(1934));
		assertEquals(new LocalDate(1935,APRIL,21), BusinessDaysUtil.getEasterSunday(1935));
		assertEquals(new LocalDate(1936,APRIL,12), BusinessDaysUtil.getEasterSunday(1936));
		assertEquals(new LocalDate(1937,MARCH,28), BusinessDaysUtil.getEasterSunday(1937));
		assertEquals(new LocalDate(1938,APRIL,17), BusinessDaysUtil.getEasterSunday(1938));
		assertEquals(new LocalDate(1939,APRIL,9), BusinessDaysUtil.getEasterSunday(1939));
		assertEquals(new LocalDate(1940,MARCH,24), BusinessDaysUtil.getEasterSunday(1940));
		assertEquals(new LocalDate(1941,APRIL,13), BusinessDaysUtil.getEasterSunday(1941));
		assertEquals(new LocalDate(1942,APRIL,5), BusinessDaysUtil.getEasterSunday(1942));
		assertEquals(new LocalDate(1943,APRIL,25), BusinessDaysUtil.getEasterSunday(1943));
		assertEquals(new LocalDate(1944,APRIL,9), BusinessDaysUtil.getEasterSunday(1944));
		assertEquals(new LocalDate(1945,APRIL,1), BusinessDaysUtil.getEasterSunday(1945));
		assertEquals(new LocalDate(1946,APRIL,21), BusinessDaysUtil.getEasterSunday(1946));
		assertEquals(new LocalDate(1947,APRIL,6), BusinessDaysUtil.getEasterSunday(1947));
		assertEquals(new LocalDate(1948,MARCH,28), BusinessDaysUtil.getEasterSunday(1948));
		assertEquals(new LocalDate(1949,APRIL,17), BusinessDaysUtil.getEasterSunday(1949));
		assertEquals(new LocalDate(1950,APRIL,9), BusinessDaysUtil.getEasterSunday(1950));
		assertEquals(new LocalDate(1951,MARCH,25), BusinessDaysUtil.getEasterSunday(1951));
		assertEquals(new LocalDate(1952,APRIL,13), BusinessDaysUtil.getEasterSunday(1952));
		assertEquals(new LocalDate(1953,APRIL,5), BusinessDaysUtil.getEasterSunday(1953));
		assertEquals(new LocalDate(1954,APRIL,18), BusinessDaysUtil.getEasterSunday(1954));
		assertEquals(new LocalDate(1955,APRIL,10), BusinessDaysUtil.getEasterSunday(1955));
		assertEquals(new LocalDate(1956,APRIL,1), BusinessDaysUtil.getEasterSunday(1956));
		assertEquals(new LocalDate(1957,APRIL,21), BusinessDaysUtil.getEasterSunday(1957));
		assertEquals(new LocalDate(1958,APRIL,6), BusinessDaysUtil.getEasterSunday(1958));
		assertEquals(new LocalDate(1959,MARCH,29), BusinessDaysUtil.getEasterSunday(1959));
		assertEquals(new LocalDate(1960,APRIL,17), BusinessDaysUtil.getEasterSunday(1960));
		assertEquals(new LocalDate(1961,APRIL,2), BusinessDaysUtil.getEasterSunday(1961));
		assertEquals(new LocalDate(1962,APRIL,22), BusinessDaysUtil.getEasterSunday(1962));
		assertEquals(new LocalDate(1963,APRIL,14), BusinessDaysUtil.getEasterSunday(1963));
		assertEquals(new LocalDate(1964,MARCH,29), BusinessDaysUtil.getEasterSunday(1964));
		assertEquals(new LocalDate(1965,APRIL,18), BusinessDaysUtil.getEasterSunday(1965));
		assertEquals(new LocalDate(1966,APRIL,10), BusinessDaysUtil.getEasterSunday(1966));
		assertEquals(new LocalDate(1967,MARCH,26), BusinessDaysUtil.getEasterSunday(1967));
		assertEquals(new LocalDate(1968,APRIL,14), BusinessDaysUtil.getEasterSunday(1968));
		assertEquals(new LocalDate(1969,APRIL,6), BusinessDaysUtil.getEasterSunday(1969));
		assertEquals(new LocalDate(1970,MARCH,29), BusinessDaysUtil.getEasterSunday(1970));
		assertEquals(new LocalDate(1971,APRIL,11), BusinessDaysUtil.getEasterSunday(1971));
		assertEquals(new LocalDate(1972,APRIL,2), BusinessDaysUtil.getEasterSunday(1972));
		assertEquals(new LocalDate(1973,APRIL,22), BusinessDaysUtil.getEasterSunday(1973));
		assertEquals(new LocalDate(1974,APRIL,14), BusinessDaysUtil.getEasterSunday(1974));
		assertEquals(new LocalDate(1975,MARCH,30), BusinessDaysUtil.getEasterSunday(1975));
		assertEquals(new LocalDate(1976,APRIL,18), BusinessDaysUtil.getEasterSunday(1976));
		assertEquals(new LocalDate(1977,APRIL,10), BusinessDaysUtil.getEasterSunday(1977));
		assertEquals(new LocalDate(1978,MARCH,26), BusinessDaysUtil.getEasterSunday(1978));
		assertEquals(new LocalDate(1979,APRIL,15), BusinessDaysUtil.getEasterSunday(1979));
		assertEquals(new LocalDate(1980,APRIL,6), BusinessDaysUtil.getEasterSunday(1980));
		assertEquals(new LocalDate(1981,APRIL,19), BusinessDaysUtil.getEasterSunday(1981));
		assertEquals(new LocalDate(1982,APRIL,11), BusinessDaysUtil.getEasterSunday(1982));
		assertEquals(new LocalDate(1983,APRIL,3), BusinessDaysUtil.getEasterSunday(1983));
		assertEquals(new LocalDate(1984,APRIL,22), BusinessDaysUtil.getEasterSunday(1984));
		assertEquals(new LocalDate(1985,APRIL,7), BusinessDaysUtil.getEasterSunday(1985));
		assertEquals(new LocalDate(1986,MARCH,30), BusinessDaysUtil.getEasterSunday(1986));
		assertEquals(new LocalDate(1987,APRIL,19), BusinessDaysUtil.getEasterSunday(1987));
		assertEquals(new LocalDate(1988,APRIL,3), BusinessDaysUtil.getEasterSunday(1988));
		assertEquals(new LocalDate(1989,MARCH,26), BusinessDaysUtil.getEasterSunday(1989));
		assertEquals(new LocalDate(1990,APRIL,15), BusinessDaysUtil.getEasterSunday(1990));
		assertEquals(new LocalDate(1991,MARCH,31), BusinessDaysUtil.getEasterSunday(1991));
		assertEquals(new LocalDate(1992,APRIL,19), BusinessDaysUtil.getEasterSunday(1992));
		assertEquals(new LocalDate(1993,APRIL,11), BusinessDaysUtil.getEasterSunday(1993));
		assertEquals(new LocalDate(1994,APRIL,3), BusinessDaysUtil.getEasterSunday(1994));
		assertEquals(new LocalDate(1995,APRIL,16), BusinessDaysUtil.getEasterSunday(1995));
		assertEquals(new LocalDate(1996,APRIL,7), BusinessDaysUtil.getEasterSunday(1996));
		assertEquals(new LocalDate(1997,MARCH,30), BusinessDaysUtil.getEasterSunday(1997));
		assertEquals(new LocalDate(1998,APRIL,12), BusinessDaysUtil.getEasterSunday(1998));
		assertEquals(new LocalDate(1999,APRIL,4), BusinessDaysUtil.getEasterSunday(1999));
		assertEquals(new LocalDate(2000,APRIL,23), BusinessDaysUtil.getEasterSunday(2000));
		assertEquals(new LocalDate(2001,APRIL,15), BusinessDaysUtil.getEasterSunday(2001));
		assertEquals(new LocalDate(2002,MARCH,31), BusinessDaysUtil.getEasterSunday(2002));
		assertEquals(new LocalDate(2003,APRIL,20), BusinessDaysUtil.getEasterSunday(2003));
		assertEquals(new LocalDate(2004,APRIL,11), BusinessDaysUtil.getEasterSunday(2004));
		assertEquals(new LocalDate(2005,MARCH,27), BusinessDaysUtil.getEasterSunday(2005));
		assertEquals(new LocalDate(2006,APRIL,16), BusinessDaysUtil.getEasterSunday(2006));
		assertEquals(new LocalDate(2007,APRIL,8), BusinessDaysUtil.getEasterSunday(2007));
		assertEquals(new LocalDate(2008,MARCH,23), BusinessDaysUtil.getEasterSunday(2008));
		assertEquals(new LocalDate(2009,APRIL,12), BusinessDaysUtil.getEasterSunday(2009));
		assertEquals(new LocalDate(2010,APRIL,4), BusinessDaysUtil.getEasterSunday(2010));
		assertEquals(new LocalDate(2011,APRIL,24), BusinessDaysUtil.getEasterSunday(2011));
		assertEquals(new LocalDate(2012,APRIL,8), BusinessDaysUtil.getEasterSunday(2012));
		assertEquals(new LocalDate(2013,MARCH,31), BusinessDaysUtil.getEasterSunday(2013));
		assertEquals(new LocalDate(2014,APRIL,20), BusinessDaysUtil.getEasterSunday(2014));
		assertEquals(new LocalDate(2015,APRIL,5), BusinessDaysUtil.getEasterSunday(2015));
		assertEquals(new LocalDate(2016,MARCH,27), BusinessDaysUtil.getEasterSunday(2016));
		assertEquals(new LocalDate(2017,APRIL,16), BusinessDaysUtil.getEasterSunday(2017));
		assertEquals(new LocalDate(2018,APRIL,1), BusinessDaysUtil.getEasterSunday(2018));
		assertEquals(new LocalDate(2019,APRIL,21), BusinessDaysUtil.getEasterSunday(2019));
		assertEquals(new LocalDate(2020,APRIL,12), BusinessDaysUtil.getEasterSunday(2020));
		assertEquals(new LocalDate(2021,APRIL,4), BusinessDaysUtil.getEasterSunday(2021));
		assertEquals(new LocalDate(2022,APRIL,17), BusinessDaysUtil.getEasterSunday(2022));
		assertEquals(new LocalDate(2023,APRIL,9), BusinessDaysUtil.getEasterSunday(2023));
		assertEquals(new LocalDate(2024,MARCH,31), BusinessDaysUtil.getEasterSunday(2024));
		assertEquals(new LocalDate(2025,APRIL,20), BusinessDaysUtil.getEasterSunday(2025));
		assertEquals(new LocalDate(2026,APRIL,5), BusinessDaysUtil.getEasterSunday(2026));
		assertEquals(new LocalDate(2027,MARCH,28), BusinessDaysUtil.getEasterSunday(2027));
		assertEquals(new LocalDate(2028,APRIL,16), BusinessDaysUtil.getEasterSunday(2028));
		assertEquals(new LocalDate(2029,APRIL,1), BusinessDaysUtil.getEasterSunday(2029));
		assertEquals(new LocalDate(2030,APRIL,21), BusinessDaysUtil.getEasterSunday(2030));
		assertEquals(new LocalDate(2031,APRIL,13), BusinessDaysUtil.getEasterSunday(2031));
		assertEquals(new LocalDate(2032,MARCH,28), BusinessDaysUtil.getEasterSunday(2032));
		assertEquals(new LocalDate(2033,APRIL,17), BusinessDaysUtil.getEasterSunday(2033));
		assertEquals(new LocalDate(2034,APRIL,9), BusinessDaysUtil.getEasterSunday(2034));
		assertEquals(new LocalDate(2035,MARCH,25), BusinessDaysUtil.getEasterSunday(2035));
		assertEquals(new LocalDate(2036,APRIL,13), BusinessDaysUtil.getEasterSunday(2036));
		assertEquals(new LocalDate(2037,APRIL,5), BusinessDaysUtil.getEasterSunday(2037));
		assertEquals(new LocalDate(2038,APRIL,25), BusinessDaysUtil.getEasterSunday(2038));
		assertEquals(new LocalDate(2039,APRIL,10), BusinessDaysUtil.getEasterSunday(2039));
		assertEquals(new LocalDate(2040,APRIL,1), BusinessDaysUtil.getEasterSunday(2040));
		assertEquals(new LocalDate(2041,APRIL,21), BusinessDaysUtil.getEasterSunday(2041));
		assertEquals(new LocalDate(2042,APRIL,6), BusinessDaysUtil.getEasterSunday(2042));
		assertEquals(new LocalDate(2043,MARCH,29), BusinessDaysUtil.getEasterSunday(2043));
		assertEquals(new LocalDate(2044,APRIL,17), BusinessDaysUtil.getEasterSunday(2044));
		assertEquals(new LocalDate(2045,APRIL,9), BusinessDaysUtil.getEasterSunday(2045));
		assertEquals(new LocalDate(2046,MARCH,25), BusinessDaysUtil.getEasterSunday(2046));
		assertEquals(new LocalDate(2047,APRIL,14), BusinessDaysUtil.getEasterSunday(2047));
		assertEquals(new LocalDate(2048,APRIL,5), BusinessDaysUtil.getEasterSunday(2048));
		assertEquals(new LocalDate(2049,APRIL,18), BusinessDaysUtil.getEasterSunday(2049));
		assertEquals(new LocalDate(2050,APRIL,10), BusinessDaysUtil.getEasterSunday(2050));
		assertEquals(new LocalDate(2051,APRIL,2), BusinessDaysUtil.getEasterSunday(2051));
		assertEquals(new LocalDate(2052,APRIL,21), BusinessDaysUtil.getEasterSunday(2052));
		assertEquals(new LocalDate(2053,APRIL,6), BusinessDaysUtil.getEasterSunday(2053));
		assertEquals(new LocalDate(2054,MARCH,29), BusinessDaysUtil.getEasterSunday(2054));
		assertEquals(new LocalDate(2055,APRIL,18), BusinessDaysUtil.getEasterSunday(2055));
		assertEquals(new LocalDate(2056,APRIL,2), BusinessDaysUtil.getEasterSunday(2056));
		assertEquals(new LocalDate(2057,APRIL,22), BusinessDaysUtil.getEasterSunday(2057));
		assertEquals(new LocalDate(2058,APRIL,14), BusinessDaysUtil.getEasterSunday(2058));
		assertEquals(new LocalDate(2059,MARCH,30), BusinessDaysUtil.getEasterSunday(2059));
		assertEquals(new LocalDate(2060,APRIL,18), BusinessDaysUtil.getEasterSunday(2060));
		assertEquals(new LocalDate(2061,APRIL,10), BusinessDaysUtil.getEasterSunday(2061));
		assertEquals(new LocalDate(2062,MARCH,26), BusinessDaysUtil.getEasterSunday(2062));
		assertEquals(new LocalDate(2063,APRIL,15), BusinessDaysUtil.getEasterSunday(2063));
		assertEquals(new LocalDate(2064,APRIL,6), BusinessDaysUtil.getEasterSunday(2064));
		assertEquals(new LocalDate(2065,MARCH,29), BusinessDaysUtil.getEasterSunday(2065));
		assertEquals(new LocalDate(2066,APRIL,11), BusinessDaysUtil.getEasterSunday(2066));
		assertEquals(new LocalDate(2067,APRIL,3), BusinessDaysUtil.getEasterSunday(2067));
		assertEquals(new LocalDate(2068,APRIL,22), BusinessDaysUtil.getEasterSunday(2068));
		assertEquals(new LocalDate(2069,APRIL,14), BusinessDaysUtil.getEasterSunday(2069));
		assertEquals(new LocalDate(2070,MARCH,30), BusinessDaysUtil.getEasterSunday(2070));
		assertEquals(new LocalDate(2071,APRIL,19), BusinessDaysUtil.getEasterSunday(2071));
		assertEquals(new LocalDate(2072,APRIL,10), BusinessDaysUtil.getEasterSunday(2072));
		assertEquals(new LocalDate(2073,MARCH,26), BusinessDaysUtil.getEasterSunday(2073));
		assertEquals(new LocalDate(2074,APRIL,15), BusinessDaysUtil.getEasterSunday(2074));
		assertEquals(new LocalDate(2075,APRIL,7), BusinessDaysUtil.getEasterSunday(2075));
		assertEquals(new LocalDate(2076,APRIL,19), BusinessDaysUtil.getEasterSunday(2076));
		assertEquals(new LocalDate(2077,APRIL,11), BusinessDaysUtil.getEasterSunday(2077));
		assertEquals(new LocalDate(2078,APRIL,3), BusinessDaysUtil.getEasterSunday(2078));
		assertEquals(new LocalDate(2079,APRIL,23), BusinessDaysUtil.getEasterSunday(2079));
		assertEquals(new LocalDate(2080,APRIL,7), BusinessDaysUtil.getEasterSunday(2080));
		assertEquals(new LocalDate(2081,MARCH,30), BusinessDaysUtil.getEasterSunday(2081));
		assertEquals(new LocalDate(2082,APRIL,19), BusinessDaysUtil.getEasterSunday(2082));
		assertEquals(new LocalDate(2083,APRIL,4), BusinessDaysUtil.getEasterSunday(2083));
		assertEquals(new LocalDate(2084,MARCH,26), BusinessDaysUtil.getEasterSunday(2084));
		assertEquals(new LocalDate(2085,APRIL,15), BusinessDaysUtil.getEasterSunday(2085));
		assertEquals(new LocalDate(2086,MARCH,31), BusinessDaysUtil.getEasterSunday(2086));
		assertEquals(new LocalDate(2087,APRIL,20), BusinessDaysUtil.getEasterSunday(2087));
		assertEquals(new LocalDate(2088,APRIL,11), BusinessDaysUtil.getEasterSunday(2088));
		assertEquals(new LocalDate(2089,APRIL,3), BusinessDaysUtil.getEasterSunday(2089));
		assertEquals(new LocalDate(2090,APRIL,16), BusinessDaysUtil.getEasterSunday(2090));
		assertEquals(new LocalDate(2091,APRIL,8), BusinessDaysUtil.getEasterSunday(2091));
		assertEquals(new LocalDate(2092,MARCH,30), BusinessDaysUtil.getEasterSunday(2092));
		assertEquals(new LocalDate(2093,APRIL,12), BusinessDaysUtil.getEasterSunday(2093));
		assertEquals(new LocalDate(2094,APRIL,4), BusinessDaysUtil.getEasterSunday(2094));
		assertEquals(new LocalDate(2095,APRIL,24), BusinessDaysUtil.getEasterSunday(2095));
		assertEquals(new LocalDate(2096,APRIL,15), BusinessDaysUtil.getEasterSunday(2096));
		assertEquals(new LocalDate(2097,MARCH,31), BusinessDaysUtil.getEasterSunday(2097));
		assertEquals(new LocalDate(2098,APRIL,20), BusinessDaysUtil.getEasterSunday(2098));
		assertEquals(new LocalDate(2099,APRIL,12), BusinessDaysUtil.getEasterSunday(2099));
		assertEquals(new LocalDate(2100,MARCH,28), BusinessDaysUtil.getEasterSunday(2100));
		
	}

	
	
	

}
