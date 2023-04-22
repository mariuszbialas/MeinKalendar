package tech.bialas.calendar.frontend;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import tech.bialas.calendar.backend.MySchedule;
import tech.bialas.calendar.midlletier.Criterias;
import tech.bialas.calendar.midlletier.MyScheduleService;

public class TerminalUIApp {

  /** Alle Ereignisse/Veranstaltungen von Datenspeicher */
  private MyScheduleService events;
  /** Erstellt wird Kalendar mit heutigen Datum */
  private MyCalendar calendar;

  /**
   * @param events
   * @param calendar
   */
  public TerminalUIApp(MyScheduleService events) {
    this.events = events;
    this.calendar = new MyCalendar();
  }

  public void launch() {
    navigation();
  }

  // Erstell Kalendar anzeige
  private void navigation() {
    System.out.print("\033[H\033[2J");
    System.out.flush();

    // Zeigt Monat und Jahr an
    System.out.println("\u001B[32m" + calendar.getCurrentMonth() + " "
        + calendar.getCurrentYear() + " \u001B[0m");
    System.out.println("---------------------------------");
    // Wann soll Kalendar beginnt, welscher Wochentag
    int emptyFieldNumber = calendar.getMonthWeekdayStart() - 1;
    int fieldsNumber = calendar.getDaysInMonth() + emptyFieldNumber;
    int startDay = calendar.getMonthWeekdayStart();

    showWeekdayNames();

    for (int i = 0; i < fieldsNumber; i++) {

      int day = i + 1;
      int date = day + 1 - startDay;

      if (day < startDay) {
        System.out.print("     ");

      } else {
        String dateTxt = showDay(date);
        showSundays(dateTxt, day);
      }
    }

    System.out.println("\n---------------------------------");
    showMonthsEvents();
    changeCalendar();

  }

  /**
   * Esrtellt neue anzeige von Kalendar mit einegegebene Werte
   */
  
  private void changeCalendar() {

    System.out.println("\u001B[36m"
        + "\n[1] Vorheriger Monat - [2] nächster Monat - [3] Aktueller Monat - [4] Datum anpassen - [0] BEENDEN"
        + " \u001B[0m");
    int monthInput = checkNumber();
    int year;
    Month month = null;

    switch (monthInput) {
    case 1:
      year = calendar.getCurrentMonth() == Month.JANUARY
          ? calendar.getCurrentYear() - 1
          : calendar.getCurrentYear();
      month = calendar.getCurrentMonth().minus(1);
      calendar = new MyCalendar(year, month);
      navigation();
      break;
    case 2:
      year = calendar.getCurrentMonth() == Month.DECEMBER
          ? calendar.getCurrentYear() + 1
          : calendar.getCurrentYear();
      month = calendar.getCurrentMonth().plus(1);
      calendar = new MyCalendar(year, month);
      navigation();
      break;
    case 3:
      calendar = new MyCalendar();
      navigation();
      break;
    case 4:
      System.out.println("Bitte Jahr [xxxx] angeben: ");
      year = checkNumber();
      System.out.println("Bitte Monat [xx] angeben: ");
      int number = checkNumber();
      month = (number < 1 || number > 12)  ? Month.of(1) : Month.of(number);
      calendar = new MyCalendar(year, month);
      navigation();
      break;
    case 0:
      System.exit(1);

    default:
      break;
    }

  }
  
  /**  Prüft, ob die abgebene Werte sind Zahlen */
  public int checkNumber() {
    Scanner scanner = new Scanner(System.in);
    if (scanner.hasNextInt()) {
      int number = scanner.nextInt();
      return number;
    } else {
      throw new NotANumberException("Falsche Eingabe!");
    }
  }

  /**
   * Zeig aller Ereignisse/Veranstaltungen für aktueller Monat
   */
  private void showMonthsEvents() {
    int year = calendar.getCurrentYear();
    int month = calendar.getCurrentMonth().getValue();
    System.out.println("\nMonatlische Ereignisse/Veranstaltungen:");
    List<MySchedule> list = new ArrayList<>();
    // Erstellt eine Liste mit passendene Daten (Monat und Jahr)
    list = events.select(Criterias.selectEventByYearMonth(year, month));

    String text;

    for (MySchedule event : list) {
      text = event.getDate() + ": " + event.getName() + " (" + event.getType()
          + ") " + " " + event.getDescription();
      text = event.getDate().equals(LocalDate.now())
          ? "\u001B[30m\u001B[43m " + text + " \u001B[0m"
          : " " + text;
      System.out.println(text);
    }
  }

  /**
   * Prüft, ob den Tagsnummer ist schon 7, dann markiert als Sonntag
   * 
   * @param text
   * @param day
   */
  private void showSundays(String text, int day) {
    if (day % 7 == 0) {
      System.out.println("\u001B[31m" + text + "\u001B[0m");
    } else {
      System.out.print(text + " ");
    }

  }

  /**
   * Erstellt Kalendarstage Markiert den
   * 
   * @param date
   * @return text: Kalendarstag
   */
  private String showDay(int date) {
    String text = date < 10 ? "0" + date : date + "";
    text = date == LocalDate.now().getDayOfMonth()
        && calendar.getCurrentMonth() == LocalDate.now().getMonth()
        && calendar.getCurrentYear() == LocalDate.now().getYear()
            ? "\u001B[30m\u001B[43m " + text + " \u001B[0m"
            : " " + text + " ";
    return text;
  }

  /**
   * Esrtellt Wochentagenname Etikiette den heutigen Tag markieren
   */
  private void showWeekdayNames() {
    for (int i = 0; i < 7; i++) {
      String weekdays = DayOfWeek.of(i + 1)
          .getDisplayName(TextStyle.SHORT_STANDALONE, Locale.GERMAN)
          .toUpperCase();
      System.out.print(" " + weekdays + "  ");
    }
    System.out.println();

  }

}
