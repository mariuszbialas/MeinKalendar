package tech.bialas.calendar.backend;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse erstelt Objekt nach einer Verbindung mit mySQL Database
 * 
 * @author mariuszbialas
 *
 */
public class MyScheduleDaoImplWithDB implements MyScheduleDao {

  private static final String URL = "jdbc:mysql://localhost:3306/alfatraining_myschedule";
  private static final String USER = "root";
  private static final String PASSWORD = "";

  private static final String TABLE = "schedule";

  /** Alle elemente von  Databank auslesen */
  @Override
  public List<MySchedule> getAllEvents() {

    List<MySchedule> events = new ArrayList<>();
    String sql = "SELECT * FROM " + TABLE;

    try (Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement convert = connect.createStatement();
        ResultSet reply = convert.executeQuery(sql);) {
      while (reply.next()) {
        int id = reply.getInt(1);
        int typeIndex = reply.getInt(2);
        String name = reply.getNString(3);
        String desc = reply.getNString(4);
        LocalDate date = reply.getDate(5).toLocalDate();
        boolean annual = reply.getBoolean(6);

        MySchedule.EventType type = MySchedule.EventType.values()[typeIndex];

        MySchedule items = new MySchedule(type, name, desc, date, annual);
        items.setId(id);
        events.add(items);
      }
    } catch (SQLException exception) {
      // Falls keine Datenbank Verbingung, Alle Evnts werden von Datei auslesen
      MyScheduleManagment load = new MyScheduleManagment();
      events = load.readFromFile(new File("resources/events.list"));
//      exception.printStackTrace();
    }
    return events;
  }

  @Override
  public void addEvent(MySchedule event) {
    if (event.getId() != 0) {
      throw new RuntimeException(
          "Datensatz " + event + " ist schon gespeichert!");
    }

    String sql = "INSERT INTO " + TABLE + " VALUES (NULL, ?,?,?,?,?)";

    try (Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement convert = connect.prepareStatement(sql,
            Statement.RETURN_GENERATED_KEYS)) {

      convert.setInt(1, event.getType().ordinal());
      convert.setString(2, event.getName());
      convert.setString(3, event.getDescription());
      convert.setDate(4, Date.valueOf(event.getDate()));
      convert.setBoolean(5, event.isAnnual());
      convert.execute();

      ResultSet key = convert.getGeneratedKeys();
      key.next();
      int id = key.getInt(1);
      event.setId(id);

    } catch (SQLException exception) {
      exception.printStackTrace();
    }

    // Speichert alle EventS zur Datei
    List<MySchedule> events = getAllEvents();
    MyScheduleManagment save = new MyScheduleManagment();
    events.add(event);
    save.writeFileFromDB(new File("resources/events.list"), events);
  }

  @Override
  public void deleteEvent(MySchedule event) {

    String sql = "DELETE FROM " + TABLE + " WHERE id=?";

    try (Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement delete = connect.prepareStatement(sql);) {
      System.out.println("Entfernt wird: \n" + getEventFromId(event.getId()));
      delete.setInt(1, event.getId());
      delete.execute();

    } catch (SQLException exception) {
      throw new NotFoundInDatabaseException(
          "Fehler, ID in Datenbank nicht gefunden!" + exception);
    }
  }

  @Override
  public void updateEvent(MySchedule event) {
    String sql = "UPDATE " + TABLE + " SET description=?,date=? WHERE id=?";

    try (Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement update = connect.prepareStatement(sql);) {
      update.setNString(1, event.getDescription());
      update.setDate(2, Date.valueOf(event.getDate()));
      update.setInt(3, event.getId());
      update.execute();

    } catch (SQLException exception) {
      throw new NotFoundInDatabaseException(
          "Fehler, ID in Datenbank nicht gefunden! " + exception);
    }
  }

  @Override
  public MySchedule getEventFromId(int number) {
    MySchedule items = null;
    String sql = "SELECT * FROM " + TABLE + " WHERE id=" + number;

    try (Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement convert = connect.createStatement();
        ResultSet reply = convert.executeQuery(sql);) {
      while (reply.next()) {
        int id = reply.getInt(1);
        int typeIndex = reply.getInt(2);
        String name = reply.getNString(3);
        String desc = reply.getNString(4);
        LocalDate date = reply.getDate(5).toLocalDate();
        boolean annual = reply.getBoolean(6);

        MySchedule.EventType type = MySchedule.EventType.values()[typeIndex];

        items = new MySchedule(type, name, desc, date, annual);
        items.setId(id);
      }

    } catch (SQLException exception) {
      throw new NotFoundInDatabaseException(
          "Fehler, ID in Datenbank nicht gefunden!" + exception);
    }
    return items;

  }

}
