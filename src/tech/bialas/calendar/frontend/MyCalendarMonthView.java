/**
 * 
 */
package tech.bialas.calendar.frontend;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Line;
import javafx.stage.StageStyle;
import tech.bialas.calendar.backend.MySchedule;
import tech.bialas.calendar.midlletier.Criterias;
import tech.bialas.calendar.midlletier.MyScheduleService;

/**
 * @author mariuszbialas
 *
 */
public class MyCalendarMonthView extends GridPane {

  /** Aktueller Jahr */
  private int year;
  /** Aktueller Monat */
  private int month;
  /** Alle Events von Datenbank oder von Datai */
  private MyScheduleService events;
  /** ein Kalendar-Objekt mit aktuellem Datum */
  private MyCalendar calendar;

  public MyCalendarMonthView(MyCalendar calendar, MyScheduleService events) {
    this.calendar = calendar;
    this.events = events;
    this.year = calendar.getCurrentYear();
    this.month = calendar.getCurrentMonth().getValue();

    // Erstellt Labels für Jahr un Monat
    Label yearTxt = new Label(Integer.toString(this.calendar.getCurrentYear()));
    Label monthTxt = new Label(this.calendar.getCurrentMonth()
        .getDisplayName(TextStyle.FULL_STANDALONE, Locale.GERMAN));
    yearTxt.setId("year");
    monthTxt.setId("month");

    // Kalenderansicht abhängig von Monat ändern
    renderCalendarView();
    this.setAlignment(Pos.CENTER);

    // Setzen Labels für Monat und Jahr
    this.add(monthTxt, 1, 0, 3, 1);
    this.add(yearTxt, 5, 0, 2, 1);

    // Setzen kurzer Name von Wochentagen
    for (int i = 0; i < 7; i++) {
      String weekdays = DayOfWeek.of(i + 1)
          .getDisplayName(TextStyle.SHORT_STANDALONE, Locale.GERMAN)
          .toUpperCase();
      Label label = new Label(weekdays);
      label.getStyleClass().add("weekdays");
      GridPane.setHalignment(label, HPos.CENTER);
      if (i == 6) {
        // Sonntags mit rotem Text markieren
        label.getStyleClass().add("sunday");
      }
      this.add(label, i, 1);
    }

    // Rendering Tage Zahl auf korrekte Position, abhängig von jedem Monat
    int row = 2;
    int col = this.calendar.getMonthWeekdayStart() - 1;
    int days = this.calendar.getDaysInMonth();

    for (int i = 0; i < days; i++) {
      int day = i + 1;
      Label labelDay = new Label(Integer.toString(day));
      labelDay.setPrefWidth(80);
      GridPane.setHalignment(labelDay, HPos.CENTER);
      labelDay.getStyleClass().add("day");

      // Aktueller Tag extra markieren
      showCurrentTag(day, labelDay);
      // Feiertage markieren
      showHolidays(day, labelDay);
      // Feiertage markieren
      showBirthdays(day, labelDay);
      // Zeigt ein Modal mit Taginfo
      labelDay.setOnMouseClicked(click -> new MyCalendarMonthViewDayInfoModal(events, calendar, day));
      this.add(labelDay, col, row);

      // Sonntags mit rotem Text markieren
      if (col == 6) {
        labelDay.getStyleClass().add("sunday");
        col = 0;
        row++;
      } else {
        col++;
      }
    }
    // Alle Ereignisse/Veranstaltungen für aktueller Monat anzeigen
    MyCalendarMonthViewEvents monthEvents = new MyCalendarMonthViewEvents(events, calendar);
    Label monthInfo = monthEvents.showEvents();
    monthInfo.getStyleClass().add("info");
    GridPane.setValignment(monthInfo, VPos.TOP);
    this.add(monthInfo, 0, 9, 7, 1);

    // Trennungslinie
    this.add(rendernDividingLine(), 0, 8);
    
  }

  /** Erstell eine Trenungline */
  private Line rendernDividingLine() {
    Line line = new Line();
    line.setStartX(0);
    line.setStartY(0);
    line.setEndX(700);
    line.setEndY(0);
    line.setStrokeWidth(2);
    return line;

  }

  /**
   * Die Methode render row und column für die Kalenderansicht
   */
  private void renderCalendarView() {
    for (int i = 0; i < 7; i++) {
      getColumnConstraints().add(new ColumnConstraints(100));
    }
    getRowConstraints().add(new RowConstraints(50));
    getRowConstraints().add(new RowConstraints(30));
    for (int i = 0; i < 6; i++) {
      getRowConstraints().add(new RowConstraints(60));
    }
    getRowConstraints().add(new RowConstraints(20));
    getRowConstraints().add(new RowConstraints(280));
    getRowConstraints().add(new RowConstraints(20));

  }

  /** Feiertage im Kalender markieren */
  private void showHolidays(int day, Label label) {
    List<MySchedule> holidays = new ArrayList<>();
    holidays = events
        .select(Criterias.selectAllHolidays(this.year, this.month));
    for (MySchedule item : holidays) {
      int dateToDay = Integer
          .parseInt(item.getDate().toString().substring(8, 10));
      if (day == dateToDay) {
        label.getStyleClass().add("holiday");
      }

    }

  }
  /** Geburtstage im Kalender markieren */
  private void showBirthdays(int day, Label label) {
    List<MySchedule> birthdays = new ArrayList<>();
    birthdays = events.select(Criterias.selectAllBirthdays(month));
    for (MySchedule item : birthdays) {
      int dateToDay = Integer
          .parseInt(item.getDate().toString().substring(8, 10));
      if (day == dateToDay) {
        label.getStyleClass().add("birthday");
      }
    }
    
  }

  /** Markiert wird aktualler Tag */
  private void showCurrentTag(int day, Label label) {
    if (day == LocalDate.now().getDayOfMonth()
        && this.calendar.getCurrentMonth() == LocalDate.now().getMonth()
        && this.calendar.getCurrentYear() == LocalDate.now().getYear()) {
      label.setId("today");
    }

  }
  
}
