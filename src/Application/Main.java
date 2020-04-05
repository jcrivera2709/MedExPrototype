package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  static final int SCENE_WIDTH = 700;
  static final int SCENE_HEIGHT = 500;

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("LogInFXML.fxml"));

    // Creates new scene with 700 by 500 dimension using stylesheet.css as the style
    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    primaryStage.setScene(scene);
    scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
