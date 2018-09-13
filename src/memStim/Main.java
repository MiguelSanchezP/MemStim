package memStim;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        AnchorPane MainPane = new AnchorPane();
        int rows = 5;
        int columns = 5;
        MainPane.setPrefWidth(600);
        MainPane.setPrefHeight(600);
        double height = MainPane.getPrefHeight();
        double width = MainPane.getPrefWidth();
        for (int i = 0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                Rectangle square = new Rectangle();
                double sqHeight = height/rows;
                double sqWidth = width/columns;
                square.setHeight(sqHeight);
                square.setWidth(sqWidth);
                MainPane.setTopAnchor(square, sqHeight*i);
                MainPane.setBottomAnchor(square, sqHeight*(rows-i));
                MainPane.setRightAnchor(square, sqWidth*(columns-j));
                MainPane.setLeftAnchor(square, sqWidth*j);
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
