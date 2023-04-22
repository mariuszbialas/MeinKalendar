/**
 * 
 */
package tech.bialas.calendar.main;

import java.util.Scanner;

import tech.bialas.calendar.backend.MyScheduleDao;
import tech.bialas.calendar.backend.MyScheduleDaoImplWithDB;
import tech.bialas.calendar.backend.MyScheduleDaoImplWithFile;

/**
 * @author mariuszbialas
 *
 */
public class MyScheduleDaoFactory {
  
  private MyScheduleDaoFactory() {}
  
  public static MyScheduleDao createMyScheduleDao(String impl) {
    
    int number = 0;
    
    Scanner buffer = new Scanner(impl);
    if (buffer.hasNextInt()) {
      number = buffer.nextInt();
      impl = "mySQL";
    }
    
    switch (impl) {
    case "mySQL":
      return new MyScheduleDaoImplWithDB();
    case "File":
      return new MyScheduleDaoImplWithFile();
    default:
      break;
    }
    return null;
    
  }

}
