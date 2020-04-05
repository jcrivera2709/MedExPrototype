package Application;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PatientSelectionController {

  public Button selectPatientButton;
  public TableView<Patient> patientTableView;
  public TableColumn columnName;
  public TableColumn columnAge;
  public TableColumn columnGender;

  private Statement stmt;
  private Connection conn;
  private ObservableList<Patient> patientList;
  public static Patient patient;

  public void initialize() {
    connectDataBase();

    patientList = FXCollections.observableArrayList();

    try {
      String sql = "SELECT * FROM PATIENTS";
      ResultSet rs = conn.createStatement().executeQuery(sql);
      while (rs.next()) {
        String name = rs.getString(1);
        int age = rs.getInt(2);
        String gender = rs.getString(3);
        String chronic_condition = rs.getString(4);

        patientList.add(new Patient(name, age, gender, chronic_condition));
      }
      rs.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
    columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

    patientTableView.setItems(patientList);
  }

  public void patientSelect(ActionEvent actionEvent) throws IOException {
    patient = patientTableView.getSelectionModel().getSelectedItem();

    Stage stage;
    Parent root;

    // Based on button pressed if statement will load the selected scene.
    // if not scene is selected the default scene will be the main menu.
    stage = (Stage) selectPatientButton.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("PatientInfo.fxml"));

    // Gets root from if statement.
    // Scene width and height are both defined in main.
    Scene scene = new Scene(root, Main.SCENE_WIDTH, Main.SCENE_HEIGHT);
    stage.setScene(scene);
    scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
    stage.show();
  }

  private void connectDataBase() {
    //  Database credentials
    final String username = "";
    final String Jdbc_Driver = "org.h2.Driver";
    final String Db_Url = "jdbc:h2:./res/MedDB";
    String pass = "";

    try {
      Properties prop = new Properties();
      prop.load(new FileInputStream("res/property"));
      pass = prop.getProperty("password");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    try {
      // STEP 1: Register JDBC driver
      Class.forName(Jdbc_Driver);

      // STEP 2: Open a connection
      // empty password and username give warning in findbugs idea
      conn = DriverManager.getConnection(Db_Url, username, pass);

      // STEP 3: Execute a query
      stmt = conn.createStatement();

    } catch (Exception ex) {
      ex.printStackTrace();
    } // end try catch
  }
}
