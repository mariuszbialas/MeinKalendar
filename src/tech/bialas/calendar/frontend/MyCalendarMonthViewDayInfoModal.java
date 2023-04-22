package tech.bialas.calendar.frontend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import tech.bialas.calendar.backend.MySchedule;
import tech.bialas.calendar.midlletier.Criterias;
import tech.bialas.calendar.midlletier.MyScheduleService;

/**
 * @author mariuszbialas
 *
 */
public class MyCalendarMonthViewDayInfoModal {
  
  /** Aktueller Jahr */
  private int year;
  /** Aktueller Monat */
  private int month;
  /** Aktueller Tag */
  private int day;
  /** Alle Events von Datenbank oder von Datai */
  private MyScheduleService events;
  
  private GridPane grid = new GridPane();
  
  private List<MySchedule> list = new ArrayList<>();
  
  
  public MyCalendarMonthViewDayInfoModal( MyScheduleService events, MyCalendar calendar, int day ) {
    this.events = events;
    this.year = calendar.getCurrentYear();
    this.month = calendar.getCurrentMonth().getValue();
    this.day = day;
    
    Alert alert = new Alert(AlertType.NONE);
    DialogPane dialog = alert.getDialogPane();
    dialog.setMinWidth(200);
    dialog.setMinHeight(50);
    dialog.getStyleClass().add("event-day-info");
    
    grid.setPadding(new Insets(10));
    grid.setHgap(5);
    grid.setVgap(5);
    
    Label date = new Label();
    String dateToTxt= (day < 10 ? "0"+day : day) + "-" +
        (month < 10 ? "0"+month : month) + "-" + 
         year + "\n";
    date.setText(dateToTxt);
    date.setStyle(
        "-fx-font-size: 18px; -fx-font-weight: bold;");
    
    Label dayEvents = new Label();
    String eventsToTxt = "";
    list = events.select(Criterias.selectEventByMonthDay( month, day));
    for (MySchedule event : list) {
      // Wenn Jahr stimmt oder Events ist jährich
      if(event.getDate().getYear() == year || event.isAnnual()) {
        eventsToTxt += event.getName() + " (" + event.getType()+")" + event.getDescription() + "\n";
      }
    
    }
    dayEvents.setText(eventsToTxt);
    dayEvents.setStyle(
        "-fx-font-size: 16px; ");
    
    grid.add(date, 0, 0);
    grid.add(dayEvents, 0, 1);
    
    dialog.setContent(grid);
    alert.initStyle(StageStyle.UNDECORATED);
    alert.getButtonTypes().setAll(ButtonType.CLOSE);
    alert.showAndWait();
    
    
  }
  
  


}
