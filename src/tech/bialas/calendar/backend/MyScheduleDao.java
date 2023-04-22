package tech.bialas.calendar.backend;

import java.util.List;


/**
 * DAO Pattern Interface
 * 
 * @author mariuszbialas
 */
public interface MyScheduleDao {
  
  /**
   * Auslesen alle events-Objekte (MySchedule) aus dem Datenspeicherung 
   * @return
   */
  List<MySchedule> getAllEvents();
  
  /**
   * Neue Ereignise/Veranstaltungen hizufügen
   * @param event
   */
  void addEvent(MySchedule event);
  
  /**
   * Entfernt eine Ereignis/Veranstaltung
   * @param product
   */
  void deleteEvent(MySchedule event);
  
  /**
   * Aktualizirung eines Ereignisses/Veranstaltunges Datum
   * 
   * @param event
   * @param date
   */
  void updateEvent(MySchedule event);
  
  /**
   * 
   * @param event
   */
  MySchedule getEventFromId(int id);
  
  
}
