package tech.bialas.calendar.frontend;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.StageStyle;
import tech.bialas.calendar.backend.MySchedule;
import tech.bialas.calendar.midlletier.MyScheduleService;

public class MyCalendarViewAddModal {

  private GridPane grid = new GridPane();
  /** Ertellt wird ein Service für Datenbank Zugriff */
  private MyScheduleService events;

  public MyCalendarViewAddModal(MyScheduleService service) {
    this.events = service;

    Alert alert = new Alert(AlertType.NONE);
    DialogPane dialog = alert.getDialogPane();
    dialog.setMinWidth(300);
    dialog.setMinHeight(200);
    dialog.getStyleClass().add("add.event");

    grid.setPadding(new Insets(20));
    grid.setHgap(10);
    grid.setVgap(10);

    DatePicker datePicker = new DatePicker();
    datePicker.setPromptText("Datum des Event");
    datePicker.setPrefWidth(260);
    ChoiceBox<MySchedule.EventType> typeText = new ChoiceBox<>();
    for (MySchedule.EventType type : MySchedule.EventType.values()) {
      typeText.getItems().add(type);
    }
    
    Label title = new Label("Neues Event hizufügen");
    TextField nameText = new TextField();
    nameText.setPromptText("Name des Events, Feiertags.... ");
    TextField descText = new TextField();
    descText.setPromptText("Name des Events, Feiertags.... ");
    CheckBox checkBox = new CheckBox("Jährlich?");
    checkBox.setStyle(
        "-fx-font-size: 16px; -fx-font-weight: bold;");
    Button save = new Button("Speichern");
    save.setPrefWidth(260);
    save.setStyle(
        "-fx-font-size: 22px; -fx-font-weight: bold;");
    save.setOnMouseEntered(event -> {
      save.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: red;");
    });
    save.setOnMouseExited(event -> {
      save.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: black;");
    });

    for (int i = 0; i < 7; i++) {
      grid.getRowConstraints().add(new RowConstraints(50));
    }
    grid.getColumnConstraints().add(new ColumnConstraints(100));

    grid.add(datePicker, 0, 0, 2, 1);
    grid.add(typeText, 0, 1);
    grid.add(nameText, 1, 1);
    grid.add(descText, 0, 2, 2, 1);
    grid.add(checkBox, 0, 3);
    grid.add(save, 0, 4, 2, 1);

    // Erstellt neues MySchedule-Objekt
    save.setOnAction(click -> saveNewEvent(
        new MySchedule(typeText.getValue(), nameText.getText(),
            descText.getText(), datePicker.getValue(), checkBox.isSelected())));

    dialog.setContent(grid);
    alert.initStyle(StageStyle.UNDECORATED);
    alert.getButtonTypes().setAll(ButtonType.CLOSE);
    alert.showAndWait();

  }

  /** Speichert neues Evnet zum Datenbank */
  private void saveNewEvent(MySchedule event) {
    events.addEvent(event);
  }

}
