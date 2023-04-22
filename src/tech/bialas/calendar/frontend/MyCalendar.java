package tech.bialas.calendar.frontend;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Die Klasse MyCalendar erstellt einen Kalendar
 * Der Kalendar wird mit aktualler oder mit einegebebener Datum (Jahr und Monat) erstellt
 * 
 * 
 * @author mariuszbialas
 */
public class MyCalendar {
  
  /** Aktueller Jahr von  */ 
  private int currentYear;
  /** Aktuell Monatsname  */ 
  private Month currentMonth;
  /** Aktuell Tagsnummer  */ 
  private int currentDate;
  /** Name aktuelles Wohentags */ 
  private DayOfWeek currentWeekday;
  /** Anzahl der Tage im aktuellen Monat  */ 
  private int daysInMonth;
  /** Nummer des Wochentages, mit dem der aktuelle Monat beginnt */ 
  private int monthWeekdayStart;
  
  /**
   * Kontruktor mit aktuelle Datum von Methode .now() 
   */
  public MyCalendar() {
    this.currentYear = LocalDate.now().getYear();
    this.currentMonth = LocalDate.now().getMonth();
    this.currentDate = LocalDate.now().getDayOfMonth();
    this.currentWeekday = LocalDate.now().getDayOfWeek();
    this.daysInMonth = LocalDate.now().lengthOfMonth();
    this.monthWeekdayStart = LocalDate.of(currentYear, currentMonth, 1).getDayOfWeek().getValue();
  } 
  

  /**
   * Kontruktor mit einer eingegebenen Datum bzw. Jahr und Monat
   * 
   * @param year
   * @param month
   * @param currentDate
   * @param currentWeekday
   * @param monthDaysNumber
   * @param monthWeekdayStart
   */
  public MyCalendar(int year, Month month) {
    this.currentYear = year;
    this.currentMonth = month;
    this.currentDate = 1;
    this.currentWeekday = LocalDate.of(year, month, 1).getDayOfWeek();
    this.daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();
    this.monthWeekdayStart = LocalDate.of(currentYear, currentMonth, 1).getDayOfWeek().getValue();
  }
  
  
  public int getCurrentYear() {
    return currentYear;
  }

  public Month getCurrentMonth() {
    return currentMonth;
  }

  public int getCurrentDate() {
    return currentDate;
  }

  public DayOfWeek getCurrentWeekday() {
    return currentWeekday;
  }

  public int getDaysInMonth() {
    return daysInMonth;
  }

  public int getMonthWeekdayStart() {
    return monthWeekdayStart;
  }
  
  
  @Override
  public String toString() {
    return "CalendarTest [currentYear=" + currentYear + ", currentMonth="
        + currentMonth.getDisplayName(TextStyle.FULL, Locale.GERMAN) + ", currentDate=" + currentDate + ", currentWeekday="
        + currentWeekday.getDisplayName(TextStyle.FULL, Locale.GERMAN) + ", daysNumber=" + daysInMonth + ", monthWeekdayStart="
        + monthWeekdayStart + "]";
  }


}






