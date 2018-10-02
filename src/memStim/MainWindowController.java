/* Add the method to memorize the prev configuration and revise the current one (Button Confirm)*/

package memStim;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindowController {

    private Stage primaryStage = new Stage();
    private double x, y;
    private ArrayList<Boolean> status = new ArrayList<>();
    private boolean prev = true;

    @FXML
    private void handleNewGameBtn() {
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
                if (random < 1) {
                    square.setFill(Color.BLACK);
                    status.add(i * 5 + j, Boolean.TRUE);
                } else {
                    square.setFill(Color.WHITE);
                    status.add(i * 5 + j, Boolean.FALSE);
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
                int X = (int) (x / (width / rows));
                int Y = (int) (y / (height / columns));
                if (!prev) {
                    Rectangle r = getNodeWithColumnAndIndex(MainPane, X, Y);
                    if (r != null) {
                        if (r.getFill().equals(Color.WHITE)) {
                            r.setFill(Color.BLACK);
                        } else {
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
                for (int i = 0; i < status.size(); i++) {
                    if (status.get(i).equals(Boolean.TRUE)) {
                        int XPoint = i / 5;
                        int YPoint = i % 5;
                        Rectangle r = getNodeWithColumnAndIndex(MainPane, YPoint, XPoint);
                        if (r != null) {
                            r.setFill(Color.WHITE);
                        }
                    }
                }
            }
        }, 10000);
        if (button.isPressed()) {
            correctLayout(MainPane, status, handleButtonConfirm(MainPane, rows, columns));
        }

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!prev) {
                    correctLayout(MainPane, status, handleButtonConfirm(MainPane, rows, columns));
                }
            }
        });
    }

    private Rectangle getNodeWithColumnAndIndex(GridPane gp, int column, int row) {
        ObservableList<Node> list = gp.getChildren();
        for (Node n : list) {
            if (gp.getRowIndex(n).equals(row) && gp.getColumnIndex(n).equals(column)) {
                return (Rectangle) n;
            }
        }
        return null;
    }

    private ArrayList<Boolean> handleButtonConfirm(GridPane gp, int r, int c) {
        ArrayList<Boolean> userInput = new ArrayList<>();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                Rectangle rec = getNodeWithColumnAndIndex(gp, j, i);
                if (rec != null) {
                    if (rec.getFill().equals(Color.WHITE)) {
                        userInput.add(i * 5 + j, Boolean.FALSE);
                    } else {
                        userInput.add(i * 5 + j, Boolean.TRUE);
                    }
                }
            }
        }
        return userInput;
    }

    private void correctLayout(GridPane gp, ArrayList<Boolean> m, ArrayList<Boolean> u) {
        for (int i = 0; i < m.size(); i++) {
            int column = i%5;
            int row = i/5;
            Rectangle r = getNodeWithColumnAndIndex(gp, column, row);
            if (r != null) {
                if (m.get(i).equals(u.get(i)) && m.get(i).equals(Boolean.TRUE)) {
                    r.setFill(Color.GREEN);
                } else if (!m.get(i).equals(u.get(i)) && m.get(i).equals(Boolean.TRUE)) {
                    r.setFill(Color.YELLOW);
                } else if (!m.get(i).equals(u.get(i)) && m.get(i).equals(Boolean.FALSE)){
                    r.setFill(Color.RED);
                }
            }
        }
    }
}
