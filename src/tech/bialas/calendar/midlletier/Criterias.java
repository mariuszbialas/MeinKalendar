package tech.bialas.calendar.midlletier;

import java.time.LocalDate;
import java.util.function.Predicate;

import tech.bialas.calendar.backend.MySchedule;

/**
 * @author mariuszbialas
 *
 */
public class Criterias {
  
  private Criterias() {}
  
  
  public static final Predicate<MySchedule> ALL_BIRTHDAYS = event -> event.getType().ordinal() == 1;
  public static final Predicate<MySchedule> ALL_ANNUALS = event -> event.isAnnual();
  public static final Predicate<MySchedule> TODAY = event -> event.getDate().equals(LocalDate.now());
  public static final Predicate<MySchedule> TODAY_WITHOUT_YEAR = event -> 
            event.getDate().getDayOfMonth() == (LocalDate.now().getDayOfMonth()) &&
            event.getDate().getMonth().equals(LocalDate.now().getMonth()) &&
            event.isAnnual();
  
  public static final Predicate<MySchedule> selectEventByName(String name) {
    return event -> event
                      .getName().toLowerCase()
                      .equals(name.toLowerCase());
  }
  
  public static final Predicate<MySchedule> selectEventByType(int typeIndex) {
    return event -> event.getType().ordinal() == typeIndex;
  }
  
  public static final Predicate<MySchedule> selectEventById(int number) {
    return event -> event.getId() == number;
  }
  
  public static final Predicate<MySchedule> selectEventByYear(int number) {
    return event -> event.getDate().getYear() == number;
  }
  
  public static final Predicate<MySchedule> selectEventByMonth(int number) {
    return event -> event.getDate().getMonth().getValue() == number;
  }
  
  public static final Predicate<MySchedule> selectEventByDay(int number) {
    return event -> event.getDate().getDayOfMonth() == number;
  }
  
  public static final Predicate<MySchedule> selectEventByMonthDay(int month, int day) {
    return event -> event.getDate().getMonth().getValue() == month && 
                    event.getDate().getDayOfMonth() == day;
  }
  
  public static final Predicate<MySchedule> selectEventByMonthAnnual(int month) {
    return event -> event.getDate().getMonth().getValue() == month && 
                    event.isAnnual();
  }
  
  public static final Predicate<MySchedule> selectEventByMonthDayAnnual(int month, int day) {
    return event -> event.getDate().getMonth().getValue() == month && 
                    event.getDate().getDayOfMonth() == day &&
                    event.isAnnual();
  }
  
  
  public static final Predicate<MySchedule> selectEventByYearMonthDay(int year, int month, int day) {
    return event -> event.getDate().getYear() == year &&
                    event.getDate().getMonth().getValue() == month &&
                    event.getDate().getDayOfMonth() == day;
  }
  
  public static final Predicate<MySchedule> selectEventByYearMonth(int year, int month) {
    return event -> event.getDate().getYear() == year &&
                    event.getDate().getMonth().getValue() == month;
  }
  
  public static final Predicate<MySchedule> selectHolidaysByYearMonth(int year, int month) {
    return event -> event.getDate().getYear() == year &&
                    event.getDate().getMonth().getValue() == month &&
                    event.getType().ordinal() == 1;
  }
  
  public static final Predicate<MySchedule> selectAllHolidays(int year, int month) {
    return event -> event.getDate().getYear() == year &&
                    event.getDate().getMonth().getValue() == month &&
                    event.getType().ordinal() == 1 ||
                    event.getDate().getMonth().getValue() == month &&
                    event.getType().ordinal() == 1 &&
                    event.isAnnual();
  }
  
  public static final Predicate<MySchedule> selectAllBirthdays(int month) {
    return event -> event.getDate().getMonth().getValue() == month &&
                    event.getType().ordinal() == 3;
  }

}
