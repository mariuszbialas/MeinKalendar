package tech.bialas.calendar.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse speichern ertelltes Objekt nach Databank verbibdung zum einen
 * Dataien. Die Klaasse die laden Myschedule-Objekten von Dateien ...
 * 
 * 
 * @author mariuszbialas
 *
 */
public class MyScheduleManagment {

  /**
   * Die Methode speichern eine Liste von MySchedule-Objekten zur Datei
   * 
   * @param target
   * @param events
   */
  public void writeFileFromDB(File target, List<MySchedule> events) {
    try (ObjectOutputStream writer = new ObjectOutputStream(
        new FileOutputStream(target))) {
      writer.writeObject(events);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Die Methode lesen MySchedule-Objekte von Datei
   * 
   * @param target
   * @return
   */
  public List<MySchedule> readFromFile(File target) {
    List<MySchedule> list = new ArrayList<>();

    try (ObjectInputStream reader = new ObjectInputStream(
        new FileInputStream(target))) {

      Object object = reader.readObject();
      list = (List<MySchedule>) object;

    } catch (IOException exception) {
      exception.printStackTrace();
    } catch (ClassNotFoundException exception) {
      System.out.println("Bitte die SerialVersionUID vergleichen");
    }

    return list;

  }

  public static void main(String[] args) {
    List<MySchedule> list = new ArrayList<>();
    System.out.println("start");

    MyScheduleManagment read = new MyScheduleManagment();
    list = read.readFromFile(new File("resources/events.list"));
    System.out.println("list loaded\n" + list);

    list.forEach(System.out::println);

  }
}