package tech.bialas.calendar.midlletier;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import tech.bialas.calendar.backend.MySchedule;
import tech.bialas.calendar.backend.MyScheduleDao;

/**
 * Die Klasse MyScheduleService macht eine Berechnungen, Daten Änderungen
 * 
 * @author mariuszbialas
 */
public class MyScheduleService {

  /** Quelle von Ereignisse/Veranstaltungen */
  private MyScheduleDao source;
  /** Alle Ereignisse/Veranstaltungen von Datenbank */
  private List<MySchedule> events;

  /**
   * @param source
   * @param events
   */
  public MyScheduleService(MyScheduleDao source) {
    this.source = source;
    this.events = this.source.getAllEvents();
  }

  /**
   * Erstellt eine Liste aller Ereignisse/Veranstaltungen
   * 
   * @return Liste aller Events
   */
  public List<MySchedule> selectAll() {
    return events;
  }

  /**
   * Erstellt eine Liste nach Krieterien Ereignisse/Veranstaltungen
   * 
   * @param criteria
   * @return Liste von ausgewählte Events
   */
  public List<MySchedule> select(Predicate<MySchedule> criteria) {
    return events
            .stream()
            .filter(criteria)
            .sorted(Comparator.comparing(MySchedule::getDate))
            .collect(Collectors.toList());
  }

  /** sortieren Events nach Sorte (Type) */
  public List<MySchedule> sortByType() {
    return events.stream().sorted(Comparator.comparing(MySchedule::getType))
        .collect(Collectors.toList());
  }

  /** sortieren Events nach Name */
  public List<MySchedule> sortByName() {
    return events.stream().sorted(Comparator.comparing(MySchedule::getName))
        .collect(Collectors.toList());
  }

  /** sortieren Events nach Datum */
  public List<MySchedule> sortByDate() {
    return events.stream()
        .sorted(Comparator.comparing(MySchedule::getDate).reversed())
        .collect(Collectors.toList());
  }

  public void addEvent(MySchedule event) {
    source.addEvent(event);
//    events.add(event);
    
  }
  
  public void updateEventDate(MySchedule event) {
    source.updateEvent(event);
  }
  
 


}
