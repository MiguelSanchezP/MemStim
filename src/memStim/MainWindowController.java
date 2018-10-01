package memStim;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindowController {

    private Stage primaryStage = new Stage();
    private double x, y;
    private HashMap<Integer[], Boolean> configurations = new HashMap<>();
    boolean prev = true;

    @FXML
    private void handleNewGameBtn() throws InterruptedException {
        AnchorPane ap = new AnchorPane();
        GridPane MainPane = new GridPane();
        int rows = 5;
        int columns = 5;
        MainPane.setPrefWidth(600);
        MainPane.setPrefHeight(600);
        double height = MainPane.getPrefHeight();
        double width = MainPane.getPrefWidth();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int random = (int) (Math.random() * 4);
                Rectangle square = new Rectangle();
                double sqHeight = height / rows;
                double sqWidth = width / columns;
                square.setHeight(sqHeight);
                square.setWidth(sqWidth);
                MainPane.getChildren().add(square);
                MainPane.setColumnIndex(square, j);
                MainPane.setRowIndex(square, i);
                square.setStroke(Color.GREY);
                Integer[] numbers = {i, j};
                if (random<1) {
                    square.setFill(Color.BLACK);
                    configurations.put(numbers, true);
                } else {
                    square.setFill(Color.WHITE);
                    configurations.put(numbers, false);
                }
            }
        }
        ap.getChildren().add(MainPane);
        MainPane.setAlignment(Pos.CENTER);
        Button button = new Button();
        button.setText("Confirm");
        button.setPrefHeight(30.0);
        ap.getChildren().add(button);
        ap.setBottomAnchor(button, 0.0);
        ap.setRightAnchor(button, 0.0);
        ap.setBottomAnchor(MainPane, 30.0);
        MainPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                int X = (int)(x/(width/rows));
                int Y = (int)(y/(height/columns));
                if (!prev) {
                    Rectangle r = getNodeWithColumnAndIndex(MainPane, X, Y);
                    if (r!=null) {
                        if (r.getFill().equals(Color.WHITE)) {
                            r.setFill(Color.BLACK);
                        }else{
                            r.setFill(Color.WHITE);
                        }
                    }
                }
            }
        });
        Scene scene = new Scene(ap, 600, 630);
        primaryStage.setTitle("MemStim");
        primaryStage.setScene(scene);
        primaryStage.show();


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                prev = false;
                for (int i =0; i<rows; i++) {
                    for (int j = 0; j <columns; j++) {
                        Rectangle r = getNodeWithColumnAndIndex(MainPane, j, i);
                        if (r!=null) {
                            r.setFill(Color.WHITE);
                        }
                    }
                }
                //create the code to clean the gridpane and to make the application change with the clicked mouse
            }
        }, 2000);
    }

    private void updatePane(GridPane gp) throws InterruptedException{
        Thread.sleep(5000);
        gp.getChildren().clear();
    }

    private Rectangle getNodeWithColumnAndIndex(GridPane gp, int column, int row) {
        ObservableList<Node> list = gp.getChildren();
        for (Node n : list) {
            if (gp.getRowIndex(n).equals(row) && gp.getColumnIndex(n).equals(column)) {
                return(Rectangle) n;
            }
        }
        return null;
    }
}
