package Application;

import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PatientInfoController {

  public TextField textFieldName;
  public TextField textFieldAge;
  public TextField textFieldGender;
  public TextField textFieldChronicCondition;

  public Button updateButton;

  private Statement stmt;
  private Connection conn;

  public void updatePatientButton(ActionEvent actionEvent) throws SQLException {
    Alert success = new Alert(AlertType.CONFIRMATION);
    success.setHeaderText("Success!");
    success.setContentText("You have successfully updated the patient!");
    success.showAndWait();
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
