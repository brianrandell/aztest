package com.microsoft.example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import com.microsoft.example.models.*;

import static org.junit.Assert.assertEquals;
import org.junit.*;

public class FaresTest {
  
  public static DateFormat defaultDF;

  @BeforeClass
  public static void setupDateFormat() {
    defaultDF = DateFormat.getDateTimeInstance();
  }

  public Date startTime;
  public Date endTime;
  
  @Before
  public void setupStartEndTimes() throws Exception {
    startTime = defaultDF.parse("Jun 3, 2009 7:03:47 AM");
    endTime = defaultDF.parse("Jun 3, 2009 7:35:10 AM");      
  }
  
  /**
   * Verify that the Fare getters/setters are working correctly.
   * Only real complexity here is in the pennies/dollars logic.
   */  
  @Test 
  public void faresProperties() {
    Fare f = new Fare(1, 1, 
      "1 Test Way",
      "10 Test Way",
      startTime,
      endTime,
      1000, // in pennies
      250, // in pennies
      4,
      5);
    
    assertEquals(1, f.getId());
    assertEquals(1, f.getEmployeeID());

    assertEquals(1000, f.getFare());
    assertEquals(10.00f, f.getFareInDollars(), 0.1f);
    
    assertEquals(250, f.getDriverFee());
    assertEquals(2.50f, f.getDriverFeeInDollars(), 0.1f);
  }
  
  @Test
  public void employeeTotalFees() 
    throws ParseException {

    // Set up Fred, with five fares at the same time on consecutive days
    // Start/end addresses don't matter
    Employee fred = new Employee(1, "Fred", "Flintstone");
    List<Fare> fredFares = Arrays.asList(
      new Fare(1, 1, "START", "END", 
        startTime, endTime, 
        1000, 250, 4, 5),
      new Fare(1, 1, "START", "END", 
        defaultDF.parse("Jun 4, 2009 7:00:00 AM"), defaultDF.parse("Jun 4, 2009 7:35:00 AM"), 
        1000, 250, 4, 5),
      new Fare(1, 1, "START", "END", 
        defaultDF.parse("Jun 5, 2009 7:00:00 AM"), defaultDF.parse("Jun 5, 2009 7:35:00 AM"), 
        1000, 250, 4, 5),
      new Fare(1, 1, "START", "END", 
        defaultDF.parse("Jun 6, 2009 7:00:00 AM"), defaultDF.parse("Jun 6, 2009 7:35:00 AM"), 
        1000, 250, 4, 5),
      new Fare(1, 1, "START", "END", 
        defaultDF.parse("Jun 7, 2009 7:00:00 AM"), defaultDF.parse("Jun 7, 2009 7:35:00 AM"), 
        1000, 250, 4, 5)
    );
    
    int totalFees = fred.getTotalFees(fredFares);
    assertEquals(250 * 5, totalFees);
    
    float totalFeesInDollars = fred.getTotalFeesInDollars(fredFares);
    assertEquals(2.50f * 5, totalFeesInDollars, 0.1f);
  }
  
}





