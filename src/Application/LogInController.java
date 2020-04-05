package Application;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {

  public TextField usernameTextField;
  public PasswordField passwordTextField;
  public Button logInButton;
  private Statement stmt;
  private Connection conn;

  public void initialize() {
    connectDataBase();
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

  public void logIn(javafx.event.ActionEvent actionEvent) throws IOException {
    try {

      String sqlUsername = usernameTextField.getText();
      String sql = "SELECT * FROM USER WHERE USERNAME =" + "'" + sqlUsername + "'";

      // goes through production record and gets the strings stored in column index 4-5
      ResultSet rs = stmt.executeQuery(sql);
      // Goes through the list of employees
      while (rs.next()) {

        // these lines of code correspond to the database table columns for username and password
        String username = rs.getString(1);
        String password = rs.getString(2);

        // Checks if the username and password from the database matches the imputed answers.
        if (usernameTextField.getText().equalsIgnoreCase(username)
            && passwordTextField.getText().equalsIgnoreCase(password)) {
          Alert success = new Alert(AlertType.CONFIRMATION);
          success.setHeaderText("Success!");
          success.setContentText("You have successfully signed in!");
          success.showAndWait();

          Stage stage;
          Parent root;

          // Based on button pressed if statement will load the selected scene.
          // if not scene is selected the default scene will be the main menu.
          stage = (Stage) logInButton.getScene().getWindow();
          root = FXMLLoader.load(getClass().getResource("PatientSelection.fxml"));

          // Gets root from if statement.
          // Scene width and height are both defined in main.
          Scene scene = new Scene(root, Main.SCENE_WIDTH, Main.SCENE_HEIGHT);
          stage.setScene(scene);
          scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
          stage.show();
        }
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
