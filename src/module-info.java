module mycalender {
  requires java.sql;
  requires mysql.connector.j;
  requires javafx.graphics;
  requires javafx.controls;
  requires java.desktop;
  
  opens tech.bialas.calendar.frontend;
}