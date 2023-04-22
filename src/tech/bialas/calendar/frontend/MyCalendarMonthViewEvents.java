/**
 * 
 */
package tech.bialas.calendar.frontend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import tech.bialas.calendar.backend.MySchedule;
import tech.bialas.calendar.midlletier.Criterias;
import tech.bialas.calendar.midlletier.MyScheduleService;

/**
 * Erstellt aller Ereignisse/Veranstaltungen für aktueller Monat
 * 
 * @author mariuszbialas
 */
public class MyCalendarMonthViewEvents {
  
  /** Aktueller Jahr */
  private int year;
  /** Aktueller Monat */
  private int month;
  /** Alle Events von Datenbank oder von Datai */
  private MyScheduleService events;
  
  
  public MyCalendarMonthViewEvents( MyScheduleService events, MyCalendar calendar ) {
    this.events = events;
    this.year = calendar.getCurrentYear();
    this.month = calendar.getCurrentMonth().getValue();

  }
  
  public Label showEvents() {
    String text = "";
    // Erstellt eine Liste mit passendene Daten (Monat und Jahr)
    List<MySchedule> list = new ArrayList<>();
    list = events.select(Criterias.selectEventByMonthAnnual(month));
    list.addAll(events.select(Criterias.selectEventByYearMonth(year, month)));

    for (MySchedule event : list) {
      String date = event.getDate().toString().substring(8, 10) + ". ";

      if ( event.getDate().equals(LocalDate.now()) ) {
        text += "HEUTE: " + event.getName().toUpperCase() + " " + event.getDescription()
            + "\n";
        
      } else if ( event.isAnnual()
          && event.getType().ordinal() == 3 ) {
        text += date + event.getName().toUpperCase() + " hat Geburtstag\n";
        
      } else if ( event.isAnnual()
          && event.getType().ordinal() == 1 ) {
        text += date + event.getName().toUpperCase() + "\n";
        
      } else if ( !event.isAnnual()
          && event.getType().ordinal() == 1 ) {
        text += date + event.getName().toUpperCase() + "\n";
        
      } else {
        text += date + event.getName() + " " + event.getDescription() + "\n";
      }
    }
    
    Label info = new Label(text);
    return info;
  }
  
  
  
}
