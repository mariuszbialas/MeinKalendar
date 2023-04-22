package tech.bialas.calendar.backend;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Die Klasse MyEvents erstellt ein Objekt mit Ereignisse
 * 
 * @author mariuszbialas
 */
public class MySchedule implements Serializable {

  /**
   * Name des Eventstype
   * 
   * @author mariuszbialas
   */
  public enum EventType {
    
    OTHER, FEIERTAG_DE, FEIERTAG_PL, GEBURTSTAG, SPORT, BARCA, REISE, DEV
    
  }

  /** Primärschlüssel id-Spallte in Datenbank */
  private int id;
  /** Name des Eventsypes, type-Spallte in Datenbank */
  private EventType type;

  /** Name des Events, name-Spallte in Datenbank */
  private String name;

  /** Beschreibung, description-Spallte in Datenbank */
  private String description;

  /** Datum des Events, date-Spallte in Datenbank */
  private LocalDate date;

  /** Ob, das Event jedes Jahr findet stat, annual-Spallte in Datenbank */
  private boolean annual;

  
  public MySchedule(EventType type, String name, String description, LocalDate date,
      boolean annual) {
    this.name = name;
    this.description = description;
    this.date = date;
    this.annual = annual;
    this.type = type;

  }

  public int getId() {
    return id;
  }

  /**
   * 
   * Die Methode prüft ob gibt's shon gleiches ID, wenn nicht erstellt Objekt
   * mit dem ID
   * 
   * @param id
   */
  public void setId(int id) {
    if (this.id == 0) {
      this.id = id;
    } else {
      System.err.println("Fehler, ID schon vergeben!");
      System.exit(-1);
    }
  }


  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public boolean isAnnual() {
    return annual;
  }

  /**
   * @return the type
   */
  public EventType getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(EventType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "MySchedule [id=" + id + ", type=" + type + ", name=" + name
        + ", description=" + description + ", date=" + date + ", annual="
        + annual + "]";
  }

}
