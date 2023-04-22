package tech.bialas.calendar.backend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse erstelt Objekt MySchedule von Datei
 * 
 * @author mariuszbialas
 *
 */
public class MyScheduleDaoImplWithFile implements MyScheduleDao {

  @Override
  public List<MySchedule> getAllEvents() {
    List<MySchedule> events = new ArrayList<>();
    MyScheduleManagment load = new MyScheduleManagment();
    events = load.readFromFile(new File("resources/events.list"));
    return events;
  }

  @Override
  public void addEvent(MySchedule event) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteEvent(MySchedule event) {
    // TODO Auto-generated method stub

  }

  @Override
  public void updateEvent(MySchedule event) {
    // TODO Auto-generated method stub

  }

  @Override
  public MySchedule getEventFromId(int id) {
    // TODO Auto-generated method stub
    return null;
  }

}
