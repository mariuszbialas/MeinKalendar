package tech.bialas.calendar.main;

import javafx.application.Application;

/**
 * Die Klasse startet Anwendung Mein Kalendar
 * 
 * @author mariuszbialas
 *
 */
public class Main {
  
  public static void main(String[] source) {
    
    String typeOfSource;
    
    if (source.length == 1) {
      typeOfSource = source[0];
    }else {
      typeOfSource = "10";
    }
    
//    MyScheduleDao schedule = MyScheduleDaoFactory.createMyScheduleDao(typeOfSource);
//    MyScheduleService events = new MyScheduleService(schedule);
//    TerminalUIApp show = new TerminalUIApp(events);
//    show.launch();
    
    
      Application.launch(tech.bialas.calendar.frontend.MyCalendarView.class, "source", typeOfSource);
  }

  

}
