package tech.bialas.calendar.frontend;

import java.time.LocalDate;
import java.time.Month;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tech.bialas.calendar.backend.MyScheduleDaoImplWithDB;
import tech.bialas.calendar.midlletier.MyScheduleService;

/**
 * Die Klasse ertellt ein JavaFX GUI Objekt, der Kalendaraussicht
 * 
 * @author mariuszbialas
 */
public class MyCalendarView extends Application {

  /** aktueller Jahr */
  private int year;
  /** aktuelle Monat */
  private Month month;
  /** ein Kalendar-Objekt mit aktuellem Datum */
  private MyCalendar calendar;
  /** Alle Events von Datenbank oder von Datai */
  private MyScheduleService events;
  

  @Override
  public void start(Stage primaryStage) throws Exception {

    primaryStage.setTitle("Mein Kalendar");
    BorderPane main = new BorderPane();

    // Erstellt einen Monatsausblick
    year = calendar.getCurrentYear();
    month = calendar.getCurrentMonth();
    main.setCenter(showCalendar(0));

    // Erstellt Buttons für die Monate wechseln
    Button left = new Button("  <<<");
    Button add = new Button("neu");
    Button home = new Button(" home ");
    Button right = new Button(">>>  ");
    left.setId("left");
    add.setId("add");
    left.setId("current");
    right.setId("right");

    // Action Events für die Buttuns, die Monate zu wechseln
    left.setOnAction(click -> main.setCenter(showCalendar(-1)));
    home.setOnAction(click -> main.setCenter(showCalendar(0)));
    right.setOnAction(click -> main.setCenter(showCalendar(1)));
    // Buttun Neues Event hizugefügen
    add.setOnAction(click -> new MyCalendarViewAddModal(events));
    
    GridPane grid = renderBottomContainer(left, home, add, right);
    main.setBottom(grid);
    BorderPane.setAlignment(grid, Pos.CENTER);

    Scene scene = new Scene(main, 800, 800);
    main.getStylesheets().add("file:resources/style.css");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  
  /** Erstellt Buttons für Menu-Bottom */
  private GridPane renderBottomContainer(Button left, Button home, Button add, Button right
      ) {
    GridPane grid = new GridPane();
    grid.setId("grid-bottom");
    for (int i = 0; i < 4; i++) {
      grid.getColumnConstraints().add(new ColumnConstraints(180));
    }
    grid.add(left, 0, 1);
    GridPane.setHalignment(left, HPos.LEFT);
    GridPane.setValignment(left, VPos.TOP);
    grid.add(home, 1, 1);
    GridPane.setHalignment(home, HPos.CENTER);
    GridPane.setValignment(home, VPos.TOP);
    grid.add(add, 2, 1);
    GridPane.setHalignment(add, HPos.CENTER);
    GridPane.setValignment(add, VPos.TOP);
    grid.add(right, 3, 1);
    GridPane.setHalignment(right, HPos.RIGHT);
    GridPane.setValignment(right, VPos.TOP);
    grid.setAlignment(Pos.CENTER);

    return grid;

  }

  /**
   * Esrtellt ein Objekt Kalendar mit entsprechendem Datum
   * 
   * @param number
   * @return
   */
  private MyCalendarMonthView showCalendar(int number) {
    MyCalendarMonthView newCalendar = null;
    switch (number) {
    case -1:
      year = month == Month.JANUARY ? year - 1 : year;
      month = month.minus(1);
      newCalendar = new MyCalendarMonthView(new MyCalendar(year, month), events);
      break;
    case 1:
      year = month == Month.DECEMBER ? year + 1 : year;
      month = month.plus(1);
      newCalendar = new MyCalendarMonthView(new MyCalendar(year, month), events);
      break;
    case 0:
      year = LocalDate.now().getYear();
      month = LocalDate.now().getMonth();
      newCalendar = new MyCalendarMonthView(new MyCalendar(), events);
      break;
    default:
      break;
    }
    return newCalendar;
  }

  public void init() {
    calendar = new MyCalendar();
    events = new MyScheduleService(new MyScheduleDaoImplWithDB());
  }

}
