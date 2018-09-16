package memStim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start (Stage stage) throws Exception {
        stage.setTitle("MemStim");
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        stage.setScene(new Scene (root));
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
