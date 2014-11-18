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

		LocalDate day = new LocalDate(2014,7,7);
		LocalDate backToWork = new LocalDate(2014,7,28);
		
		while(day.isBefore(backToWork)){
			assertFalse(BusinessDaysUtil.isBusinessDay(day)); //Industrisemester
			day = day.plusDays(1);
		}
		assertFalse(BusinessDaysUtil.isHoliday(day));	//Test that following and preceding days are not included
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,7,6))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,7,4))); 
		
		
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

		day = new LocalDate(2015,7,6);
		backToWork = new LocalDate(2015,7,27);
		
		while(day.isBefore(backToWork)){
			assertFalse(BusinessDaysUtil.isBusinessDay(day)); //Industrisemester
			day = day.plusDays(1);
		}
		assertFalse(BusinessDaysUtil.isHoliday(day));	//Test that following and preceding days are not included
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,7,5))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2014,7,3))); 

		
		
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,18))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,19))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,20))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,4,21))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,5,29))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,6,8))); 
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,6,21)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,11,1)));
	}

	@Test
	public void testSkippableWeeks() {
		//Floating dates in 2015
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,1,1))); 
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,1,2)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,1,5)));
		
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,7,13)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,7,14)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,7,15)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,7,16)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,7,17)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,7,20)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,7,21)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,7,22)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,7,23)));
		assertTrue(BusinessDaysUtil.isHoliday(new LocalDate(2015,7,24)));
		assertFalse(BusinessDaysUtil.isHoliday(new LocalDate(2015,7,27)));

		

	}

	
	/**
	 * Test number of business days between two dates.
	 */
	@Test
	public void testNoOfBusinessDays() {
		//Week without holidays
		assertEquals(5, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,1,27), new LocalDate(2014,02,03)));
		
		//Easter week 2014 4 working days
		assertEquals(4, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,4,14), new LocalDate(2014,04,21)));
		
		//Easter week 2015 4 working days
		assertEquals(4, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2015,3,30), new LocalDate(2015,04,6)));
		
		//Christmas week 2014
		assertEquals(2, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,12,22), new LocalDate(2014,12,29)));

		//Christmas week 2015
		assertEquals(3, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2015,12,21), new LocalDate(2015,12,28)));
		
		//Christmas holiday 2014, 9 working days
		assertEquals(9, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,12,22), new LocalDate(2015,1,12)));

		//Christmas holiday 2015
		assertEquals(10, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2015,12,21), new LocalDate(2016,1,11)));

	}

	/**
	 * Test number of business days between two dates.
	 */
	@Test
	public void testCalculateLastBusinessDayDate() {
		
		assertEquals(new LocalDate(2014,10,17),BusinessDaysUtil.calculateBusinessDayDate(new LocalDate(2014,10,13),5));

		assertEquals(new LocalDate(2014,10,20),BusinessDaysUtil.calculateBusinessDayDate(new LocalDate(2014,10,13),6));

		assertEquals(new LocalDate(2014,11,14),BusinessDaysUtil.calculateBusinessDayDate(new LocalDate(2014,10,13),25));

		
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
		
		assertEquals(0, BusinessDaysUtil.numberOfBusinessDays(new LocalDate(2014,10,16), new LocalDate(2014,10,16)));
	}

	@Test
	public void testEasterSunday(){
		
		assertEquals(new LocalDate(1900,4,15), BusinessDaysUtil.getEasterSunday(1900));
		assertEquals(new LocalDate(1901,4,7), BusinessDaysUtil.getEasterSunday(1901));
		assertEquals(new LocalDate(1902,3,30), BusinessDaysUtil.getEasterSunday(1902));
		assertEquals(new LocalDate(1903,4,12), BusinessDaysUtil.getEasterSunday(1903));
		assertEquals(new LocalDate(1904,4,3), BusinessDaysUtil.getEasterSunday(1904));
		assertEquals(new LocalDate(1905,4,23), BusinessDaysUtil.getEasterSunday(1905));
		assertEquals(new LocalDate(1906,4,15), BusinessDaysUtil.getEasterSunday(1906));
		assertEquals(new LocalDate(1907,3,31), BusinessDaysUtil.getEasterSunday(1907));
		assertEquals(new LocalDate(1908,4,19), BusinessDaysUtil.getEasterSunday(1908));
		assertEquals(new LocalDate(1909,4,11), BusinessDaysUtil.getEasterSunday(1909));
		assertEquals(new LocalDate(1910,3,27), BusinessDaysUtil.getEasterSunday(1910));
		assertEquals(new LocalDate(1911,4,16), BusinessDaysUtil.getEasterSunday(1911));
		assertEquals(new LocalDate(1912,4,7), BusinessDaysUtil.getEasterSunday(1912));
		assertEquals(new LocalDate(1913,3,23), BusinessDaysUtil.getEasterSunday(1913));
		assertEquals(new LocalDate(1914,4,12), BusinessDaysUtil.getEasterSunday(1914));
		assertEquals(new LocalDate(1915,4,4), BusinessDaysUtil.getEasterSunday(1915));
		assertEquals(new LocalDate(1916,4,23), BusinessDaysUtil.getEasterSunday(1916));
		assertEquals(new LocalDate(1917,4,8), BusinessDaysUtil.getEasterSunday(1917));
		assertEquals(new LocalDate(1918,3,31), BusinessDaysUtil.getEasterSunday(1918));
		assertEquals(new LocalDate(1919,4,20), BusinessDaysUtil.getEasterSunday(1919));
		assertEquals(new LocalDate(1920,4,4), BusinessDaysUtil.getEasterSunday(1920));
		assertEquals(new LocalDate(1921,3,27), BusinessDaysUtil.getEasterSunday(1921));
		assertEquals(new LocalDate(1922,4,16), BusinessDaysUtil.getEasterSunday(1922));
		assertEquals(new LocalDate(1923,4,1), BusinessDaysUtil.getEasterSunday(1923));
		assertEquals(new LocalDate(1924,4,20), BusinessDaysUtil.getEasterSunday(1924));
		assertEquals(new LocalDate(1925,4,12), BusinessDaysUtil.getEasterSunday(1925));
		assertEquals(new LocalDate(1926,4,4), BusinessDaysUtil.getEasterSunday(1926));
		assertEquals(new LocalDate(1927,4,17), BusinessDaysUtil.getEasterSunday(1927));
		assertEquals(new LocalDate(1928,4,8), BusinessDaysUtil.getEasterSunday(1928));
		assertEquals(new LocalDate(1929,3,31), BusinessDaysUtil.getEasterSunday(1929));
		assertEquals(new LocalDate(1930,4,20), BusinessDaysUtil.getEasterSunday(1930));
		assertEquals(new LocalDate(1931,4,5), BusinessDaysUtil.getEasterSunday(1931));
		assertEquals(new LocalDate(1932,3,27), BusinessDaysUtil.getEasterSunday(1932));
		assertEquals(new LocalDate(1933,4,16), BusinessDaysUtil.getEasterSunday(1933));
		assertEquals(new LocalDate(1934,4,1), BusinessDaysUtil.getEasterSunday(1934));
		assertEquals(new LocalDate(1935,4,21), BusinessDaysUtil.getEasterSunday(1935));
		assertEquals(new LocalDate(1936,4,12), BusinessDaysUtil.getEasterSunday(1936));
		assertEquals(new LocalDate(1937,3,28), BusinessDaysUtil.getEasterSunday(1937));
		assertEquals(new LocalDate(1938,4,17), BusinessDaysUtil.getEasterSunday(1938));
		assertEquals(new LocalDate(1939,4,9), BusinessDaysUtil.getEasterSunday(1939));
		assertEquals(new LocalDate(1940,3,24), BusinessDaysUtil.getEasterSunday(1940));
		assertEquals(new LocalDate(1941,4,13), BusinessDaysUtil.getEasterSunday(1941));
		assertEquals(new LocalDate(1942,4,5), BusinessDaysUtil.getEasterSunday(1942));
		assertEquals(new LocalDate(1943,4,25), BusinessDaysUtil.getEasterSunday(1943));
		assertEquals(new LocalDate(1944,4,9), BusinessDaysUtil.getEasterSunday(1944));
		assertEquals(new LocalDate(1945,4,1), BusinessDaysUtil.getEasterSunday(1945));
		assertEquals(new LocalDate(1946,4,21), BusinessDaysUtil.getEasterSunday(1946));
		assertEquals(new LocalDate(1947,4,6), BusinessDaysUtil.getEasterSunday(1947));
		assertEquals(new LocalDate(1948,3,28), BusinessDaysUtil.getEasterSunday(1948));
		assertEquals(new LocalDate(1949,4,17), BusinessDaysUtil.getEasterSunday(1949));
		assertEquals(new LocalDate(1950,4,9), BusinessDaysUtil.getEasterSunday(1950));
		assertEquals(new LocalDate(1951,3,25), BusinessDaysUtil.getEasterSunday(1951));
		assertEquals(new LocalDate(1952,4,13), BusinessDaysUtil.getEasterSunday(1952));
		assertEquals(new LocalDate(1953,4,5), BusinessDaysUtil.getEasterSunday(1953));
		assertEquals(new LocalDate(1954,4,18), BusinessDaysUtil.getEasterSunday(1954));
		assertEquals(new LocalDate(1955,4,10), BusinessDaysUtil.getEasterSunday(1955));
		assertEquals(new LocalDate(1956,4,1), BusinessDaysUtil.getEasterSunday(1956));
		assertEquals(new LocalDate(1957,4,21), BusinessDaysUtil.getEasterSunday(1957));
		assertEquals(new LocalDate(1958,4,6), BusinessDaysUtil.getEasterSunday(1958));
		assertEquals(new LocalDate(1959,3,29), BusinessDaysUtil.getEasterSunday(1959));
		assertEquals(new LocalDate(1960,4,17), BusinessDaysUtil.getEasterSunday(1960));
		assertEquals(new LocalDate(1961,4,2), BusinessDaysUtil.getEasterSunday(1961));
		assertEquals(new LocalDate(1962,4,22), BusinessDaysUtil.getEasterSunday(1962));
		assertEquals(new LocalDate(1963,4,14), BusinessDaysUtil.getEasterSunday(1963));
		assertEquals(new LocalDate(1964,3,29), BusinessDaysUtil.getEasterSunday(1964));
		assertEquals(new LocalDate(1965,4,18), BusinessDaysUtil.getEasterSunday(1965));
		assertEquals(new LocalDate(1966,4,10), BusinessDaysUtil.getEasterSunday(1966));
		assertEquals(new LocalDate(1967,3,26), BusinessDaysUtil.getEasterSunday(1967));
		assertEquals(new LocalDate(1968,4,14), BusinessDaysUtil.getEasterSunday(1968));
		assertEquals(new LocalDate(1969,4,6), BusinessDaysUtil.getEasterSunday(1969));
		assertEquals(new LocalDate(1970,3,29), BusinessDaysUtil.getEasterSunday(1970));
		assertEquals(new LocalDate(1971,4,11), BusinessDaysUtil.getEasterSunday(1971));
		assertEquals(new LocalDate(1972,4,2), BusinessDaysUtil.getEasterSunday(1972));
		assertEquals(new LocalDate(1973,4,22), BusinessDaysUtil.getEasterSunday(1973));
		assertEquals(new LocalDate(1974,4,14), BusinessDaysUtil.getEasterSunday(1974));
		assertEquals(new LocalDate(1975,3,30), BusinessDaysUtil.getEasterSunday(1975));
		assertEquals(new LocalDate(1976,4,18), BusinessDaysUtil.getEasterSunday(1976));
		assertEquals(new LocalDate(1977,4,10), BusinessDaysUtil.getEasterSunday(1977));
		assertEquals(new LocalDate(1978,3,26), BusinessDaysUtil.getEasterSunday(1978));
		assertEquals(new LocalDate(1979,4,15), BusinessDaysUtil.getEasterSunday(1979));
		assertEquals(new LocalDate(1980,4,6), BusinessDaysUtil.getEasterSunday(1980));
		assertEquals(new LocalDate(1981,4,19), BusinessDaysUtil.getEasterSunday(1981));
		assertEquals(new LocalDate(1982,4,11), BusinessDaysUtil.getEasterSunday(1982));
		assertEquals(new LocalDate(1983,4,3), BusinessDaysUtil.getEasterSunday(1983));
		assertEquals(new LocalDate(1984,4,22), BusinessDaysUtil.getEasterSunday(1984));
		assertEquals(new LocalDate(1985,4,7), BusinessDaysUtil.getEasterSunday(1985));
		assertEquals(new LocalDate(1986,3,30), BusinessDaysUtil.getEasterSunday(1986));
		assertEquals(new LocalDate(1987,4,19), BusinessDaysUtil.getEasterSunday(1987));
		assertEquals(new LocalDate(1988,4,3), BusinessDaysUtil.getEasterSunday(1988));
		assertEquals(new LocalDate(1989,3,26), BusinessDaysUtil.getEasterSunday(1989));
		assertEquals(new LocalDate(1990,4,15), BusinessDaysUtil.getEasterSunday(1990));
		assertEquals(new LocalDate(1991,3,31), BusinessDaysUtil.getEasterSunday(1991));
		assertEquals(new LocalDate(1992,4,19), BusinessDaysUtil.getEasterSunday(1992));
		assertEquals(new LocalDate(1993,4,11), BusinessDaysUtil.getEasterSunday(1993));
		assertEquals(new LocalDate(1994,4,3), BusinessDaysUtil.getEasterSunday(1994));
		assertEquals(new LocalDate(1995,4,16), BusinessDaysUtil.getEasterSunday(1995));
		assertEquals(new LocalDate(1996,4,7), BusinessDaysUtil.getEasterSunday(1996));
		assertEquals(new LocalDate(1997,3,30), BusinessDaysUtil.getEasterSunday(1997));
		assertEquals(new LocalDate(1998,4,12), BusinessDaysUtil.getEasterSunday(1998));
		assertEquals(new LocalDate(1999,4,4), BusinessDaysUtil.getEasterSunday(1999));
		assertEquals(new LocalDate(2000,4,23), BusinessDaysUtil.getEasterSunday(2000));
		assertEquals(new LocalDate(2001,4,15), BusinessDaysUtil.getEasterSunday(2001));
		assertEquals(new LocalDate(2002,3,31), BusinessDaysUtil.getEasterSunday(2002));
		assertEquals(new LocalDate(2003,4,20), BusinessDaysUtil.getEasterSunday(2003));
		assertEquals(new LocalDate(2004,4,11), BusinessDaysUtil.getEasterSunday(2004));
		assertEquals(new LocalDate(2005,3,27), BusinessDaysUtil.getEasterSunday(2005));
		assertEquals(new LocalDate(2006,4,16), BusinessDaysUtil.getEasterSunday(2006));
		assertEquals(new LocalDate(2007,4,8), BusinessDaysUtil.getEasterSunday(2007));
		assertEquals(new LocalDate(2008,3,23), BusinessDaysUtil.getEasterSunday(2008));
		assertEquals(new LocalDate(2009,4,12), BusinessDaysUtil.getEasterSunday(2009));
		assertEquals(new LocalDate(2010,4,4), BusinessDaysUtil.getEasterSunday(2010));
		assertEquals(new LocalDate(2011,4,24), BusinessDaysUtil.getEasterSunday(2011));
		assertEquals(new LocalDate(2012,4,8), BusinessDaysUtil.getEasterSunday(2012));
		assertEquals(new LocalDate(2013,3,31), BusinessDaysUtil.getEasterSunday(2013));
		assertEquals(new LocalDate(2014,4,20), BusinessDaysUtil.getEasterSunday(2014));
		assertEquals(new LocalDate(2015,4,5), BusinessDaysUtil.getEasterSunday(2015));
		assertEquals(new LocalDate(2016,3,27), BusinessDaysUtil.getEasterSunday(2016));
		assertEquals(new LocalDate(2017,4,16), BusinessDaysUtil.getEasterSunday(2017));
		assertEquals(new LocalDate(2018,4,1), BusinessDaysUtil.getEasterSunday(2018));
		assertEquals(new LocalDate(2019,4,21), BusinessDaysUtil.getEasterSunday(2019));
		assertEquals(new LocalDate(2020,4,12), BusinessDaysUtil.getEasterSunday(2020));
		assertEquals(new LocalDate(2021,4,4), BusinessDaysUtil.getEasterSunday(2021));
		assertEquals(new LocalDate(2022,4,17), BusinessDaysUtil.getEasterSunday(2022));
		assertEquals(new LocalDate(2023,4,9), BusinessDaysUtil.getEasterSunday(2023));
		assertEquals(new LocalDate(2024,3,31), BusinessDaysUtil.getEasterSunday(2024));
		assertEquals(new LocalDate(2025,4,20), BusinessDaysUtil.getEasterSunday(2025));
		assertEquals(new LocalDate(2026,4,5), BusinessDaysUtil.getEasterSunday(2026));
		assertEquals(new LocalDate(2027,3,28), BusinessDaysUtil.getEasterSunday(2027));
		assertEquals(new LocalDate(2028,4,16), BusinessDaysUtil.getEasterSunday(2028));
		assertEquals(new LocalDate(2029,4,1), BusinessDaysUtil.getEasterSunday(2029));
		assertEquals(new LocalDate(2030,4,21), BusinessDaysUtil.getEasterSunday(2030));
		assertEquals(new LocalDate(2031,4,13), BusinessDaysUtil.getEasterSunday(2031));
		assertEquals(new LocalDate(2032,3,28), BusinessDaysUtil.getEasterSunday(2032));
		assertEquals(new LocalDate(2033,4,17), BusinessDaysUtil.getEasterSunday(2033));
		assertEquals(new LocalDate(2034,4,9), BusinessDaysUtil.getEasterSunday(2034));
		assertEquals(new LocalDate(2035,3,25), BusinessDaysUtil.getEasterSunday(2035));
		assertEquals(new LocalDate(2036,4,13), BusinessDaysUtil.getEasterSunday(2036));
		assertEquals(new LocalDate(2037,4,5), BusinessDaysUtil.getEasterSunday(2037));
		assertEquals(new LocalDate(2038,4,25), BusinessDaysUtil.getEasterSunday(2038));
		assertEquals(new LocalDate(2039,4,10), BusinessDaysUtil.getEasterSunday(2039));
		assertEquals(new LocalDate(2040,4,1), BusinessDaysUtil.getEasterSunday(2040));
		assertEquals(new LocalDate(2041,4,21), BusinessDaysUtil.getEasterSunday(2041));
		assertEquals(new LocalDate(2042,4,6), BusinessDaysUtil.getEasterSunday(2042));
		assertEquals(new LocalDate(2043,3,29), BusinessDaysUtil.getEasterSunday(2043));
		assertEquals(new LocalDate(2044,4,17), BusinessDaysUtil.getEasterSunday(2044));
		assertEquals(new LocalDate(2045,4,9), BusinessDaysUtil.getEasterSunday(2045));
		assertEquals(new LocalDate(2046,3,25), BusinessDaysUtil.getEasterSunday(2046));
		assertEquals(new LocalDate(2047,4,14), BusinessDaysUtil.getEasterSunday(2047));
		assertEquals(new LocalDate(2048,4,5), BusinessDaysUtil.getEasterSunday(2048));
		assertEquals(new LocalDate(2049,4,18), BusinessDaysUtil.getEasterSunday(2049));
		assertEquals(new LocalDate(2050,4,10), BusinessDaysUtil.getEasterSunday(2050));
		assertEquals(new LocalDate(2051,4,2), BusinessDaysUtil.getEasterSunday(2051));
		assertEquals(new LocalDate(2052,4,21), BusinessDaysUtil.getEasterSunday(2052));
		assertEquals(new LocalDate(2053,4,6), BusinessDaysUtil.getEasterSunday(2053));
		assertEquals(new LocalDate(2054,3,29), BusinessDaysUtil.getEasterSunday(2054));
		assertEquals(new LocalDate(2055,4,18), BusinessDaysUtil.getEasterSunday(2055));
		assertEquals(new LocalDate(2056,4,2), BusinessDaysUtil.getEasterSunday(2056));
		assertEquals(new LocalDate(2057,4,22), BusinessDaysUtil.getEasterSunday(2057));
		assertEquals(new LocalDate(2058,4,14), BusinessDaysUtil.getEasterSunday(2058));
		assertEquals(new LocalDate(2059,3,30), BusinessDaysUtil.getEasterSunday(2059));
		assertEquals(new LocalDate(2060,4,18), BusinessDaysUtil.getEasterSunday(2060));
		assertEquals(new LocalDate(2061,4,10), BusinessDaysUtil.getEasterSunday(2061));
		assertEquals(new LocalDate(2062,3,26), BusinessDaysUtil.getEasterSunday(2062));
		assertEquals(new LocalDate(2063,4,15), BusinessDaysUtil.getEasterSunday(2063));
		assertEquals(new LocalDate(2064,4,6), BusinessDaysUtil.getEasterSunday(2064));
		assertEquals(new LocalDate(2065,3,29), BusinessDaysUtil.getEasterSunday(2065));
		assertEquals(new LocalDate(2066,4,11), BusinessDaysUtil.getEasterSunday(2066));
		assertEquals(new LocalDate(2067,4,3), BusinessDaysUtil.getEasterSunday(2067));
		assertEquals(new LocalDate(2068,4,22), BusinessDaysUtil.getEasterSunday(2068));
		assertEquals(new LocalDate(2069,4,14), BusinessDaysUtil.getEasterSunday(2069));
		assertEquals(new LocalDate(2070,3,30), BusinessDaysUtil.getEasterSunday(2070));
		assertEquals(new LocalDate(2071,4,19), BusinessDaysUtil.getEasterSunday(2071));
		assertEquals(new LocalDate(2072,4,10), BusinessDaysUtil.getEasterSunday(2072));
		assertEquals(new LocalDate(2073,3,26), BusinessDaysUtil.getEasterSunday(2073));
		assertEquals(new LocalDate(2074,4,15), BusinessDaysUtil.getEasterSunday(2074));
		assertEquals(new LocalDate(2075,4,7), BusinessDaysUtil.getEasterSunday(2075));
		assertEquals(new LocalDate(2076,4,19), BusinessDaysUtil.getEasterSunday(2076));
		assertEquals(new LocalDate(2077,4,11), BusinessDaysUtil.getEasterSunday(2077));
		assertEquals(new LocalDate(2078,4,3), BusinessDaysUtil.getEasterSunday(2078));
		assertEquals(new LocalDate(2079,4,23), BusinessDaysUtil.getEasterSunday(2079));
		assertEquals(new LocalDate(2080,4,7), BusinessDaysUtil.getEasterSunday(2080));
		assertEquals(new LocalDate(2081,3,30), BusinessDaysUtil.getEasterSunday(2081));
		assertEquals(new LocalDate(2082,4,19), BusinessDaysUtil.getEasterSunday(2082));
		assertEquals(new LocalDate(2083,4,4), BusinessDaysUtil.getEasterSunday(2083));
		assertEquals(new LocalDate(2084,3,26), BusinessDaysUtil.getEasterSunday(2084));
		assertEquals(new LocalDate(2085,4,15), BusinessDaysUtil.getEasterSunday(2085));
		assertEquals(new LocalDate(2086,3,31), BusinessDaysUtil.getEasterSunday(2086));
		assertEquals(new LocalDate(2087,4,20), BusinessDaysUtil.getEasterSunday(2087));
		assertEquals(new LocalDate(2088,4,11), BusinessDaysUtil.getEasterSunday(2088));
		assertEquals(new LocalDate(2089,4,3), BusinessDaysUtil.getEasterSunday(2089));
		assertEquals(new LocalDate(2090,4,16), BusinessDaysUtil.getEasterSunday(2090));
		assertEquals(new LocalDate(2091,4,8), BusinessDaysUtil.getEasterSunday(2091));
		assertEquals(new LocalDate(2092,3,30), BusinessDaysUtil.getEasterSunday(2092));
		assertEquals(new LocalDate(2093,4,12), BusinessDaysUtil.getEasterSunday(2093));
		assertEquals(new LocalDate(2094,4,4), BusinessDaysUtil.getEasterSunday(2094));
		assertEquals(new LocalDate(2095,4,24), BusinessDaysUtil.getEasterSunday(2095));
		assertEquals(new LocalDate(2096,4,15), BusinessDaysUtil.getEasterSunday(2096));
		assertEquals(new LocalDate(2097,3,31), BusinessDaysUtil.getEasterSunday(2097));
		assertEquals(new LocalDate(2098,4,20), BusinessDaysUtil.getEasterSunday(2098));
		assertEquals(new LocalDate(2099,4,12), BusinessDaysUtil.getEasterSunday(2099));
		assertEquals(new LocalDate(2100,3,28), BusinessDaysUtil.getEasterSunday(2100));
		
	}

	
	
	

}
