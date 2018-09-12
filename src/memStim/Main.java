package memStim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane MainPane = new AnchorPane();
        int rows = 8;
        int columns = 8;
        for (int i = 0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                Rectangle square = new Rectangle();
                double var = 600.0/rows;
                square.setHeight(var);
                square.setWidth(var);
                MainPane.setTopAnchor(square, var*i);
                MainPane.setBottomAnchor(square, var*(rows-i));
                MainPane.setRightAnchor(square, var*(columns-j));
                MainPane.setLeftAnchor(square, var*j);
                if ((i+j)%2==0) {
                    square.setFill (Color.BLACK);
                }else{
                    square.setFill (Color.WHITE);
                }
                MainPane.getChildren().add(square);
            }
        }
        Scene scene = new Scene (MainPane, 600, 600);
        primaryStage.setTitle("MemStim");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
