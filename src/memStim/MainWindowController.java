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
import java.util.concurrent.TimeUnit;

public class MainWindowController {

    private Stage primaryStage = new Stage();
    private double x, y;
    private ArrayList<Boolean> status = new ArrayList<>();
    private boolean prev = true;
    private boolean next = false;
    private boolean bool = false;
    private Scene scene;

    @FXML
    private void handleNewGameBtn() {
        AnchorPane ap = new AnchorPane();
        int rows = 5;
        int columns = 5;

        scene = new Scene(ap, 600, 630);
        createLayout(ap, rows, columns);
    }

    private void createLayout (AnchorPane ap, int columns, int rows) {
        status.clear();
        next=false;
        prev=true;
        double height = 600;
        double width = 600;
        ap.getChildren().removeAll();
        GridPane gp = runNextLayout(rows, columns, height, width);
        ap.getChildren().add(gp);
//        runNextLayout(gp, rows, columns, height, width);
//        ap.getChildren().add(gp);
        gp.setAlignment(Pos.CENTER);
        Button button = new Button();
        button.setText("Confirm");
        button.setPrefHeight(30.0);
        ap.getChildren().remove(button);
        ap.getChildren().add(button);
        ap.setBottomAnchor(button, 0.0);
        ap.setRightAnchor(button, 0.0);
        ap.setBottomAnchor(gp, 30.0);
        gp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                int X = (int) (x / (width / rows));
                int Y = (int) (y / (height / columns));
                if (!prev) {
                    Rectangle r = getNodeWithColumnAndIndex(gp, X, Y);
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
//        scene = new Scene(ap, 600, 630);
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
                        Rectangle r = getNodeWithColumnAndIndex(gp, YPoint, XPoint);
                        if (r != null) {
                            r.setFill(Color.WHITE);
                        }
                    }
                }
            }
        }, 4000);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                bool = false;
                Timer timer1 = new Timer();
                if (!prev) {
//                    if (correctLayout(gp, status, handleButtonConfirm(gp, rows, columns), button)) {
//                        timer1.schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                next=true;
//                            }
//                        }, 2000);
//                    next =  correctLayout(gp, status, handleButtonConfirm(gp, rows, columns), button);
                    try {
                        paintTheLayout(gp, status, handleButtonConfirm(gp, rows, columns));
                        TimeUnit.SECONDS.sleep(3);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    timer1.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            System.out.println("Waiting while corrected");
//                        }
//                    }, 2000);
                    bool = correctLayout(gp, status, handleButtonConfirm(gp, rows, columns), button);
                        if (bool) {
                            createLayout(ap, columns, rows);
                        }
                    }
                }
            });
    }

    private GridPane runNextLayout (int rows, int columns, double height, double width) {
        GridPane gp = new GridPane();
        gp.setPrefWidth(width);
        gp.setPrefHeight(height);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int random = (int) (Math.random() * 4);
                Rectangle square = new Rectangle();
                double sqHeight = height / rows;
                double sqWidth = width / columns;
                square.setHeight(sqHeight);
                square.setWidth(sqWidth);
                gp.getChildren().add(square);
                gp.setColumnIndex(square, j);
                gp.setRowIndex(square, i);
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
        return gp;
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

    private Boolean correctLayout(GridPane gp, ArrayList<Boolean> m, ArrayList<Boolean> u, Button btn) {
        int tempCorrect=0;
        int tempPainted =0;
        for (Boolean b : m) {
            if (b.equals(Boolean.TRUE)) {
                tempPainted+=1;
            }
        }
        for (int i = 0; i < m.size(); i++) {
            Rectangle r = getNodeWithColumnAndIndex(gp, i % 5, i / 5);
//            System.out.println("reached here" + i);
            if (r != null) {
                if (m.get(i).equals(u.get(i)) && m.get(i).equals(Boolean.TRUE)) {
                    r.setFill(Color.GREEN);
                    tempCorrect += 1;
                }
//                } else if (!m.get(i).equals(u.get(i)) && m.get(i).equals(Boolean.TRUE)) {
//                    r.setFill(Color.YELLOW);
//                } else if (!m.get(i).equals(u.get(i)) && m.get(i).equals(Boolean.FALSE)){
//                    r.setFill(Color.RED);
//                }
//            }
            }
        }
//        for (int i = 0; i<1; i++) {
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    System.out.println("Reached there");
//                }
//            }, 2000);
//        }
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return (tempCorrect == tempPainted);
    }

    private void paintTheLayout (GridPane gp, ArrayList<Boolean> m, ArrayList<Boolean> u) {
        for (int i = 0; i < m.size(); i++) {
            Rectangle r = getNodeWithColumnAndIndex(gp, i%5, i/5);
//            System.out.println("reached here" + i);
            if (r != null) {
                if (m.get(i).equals(u.get(i)) && m.get(i).equals(Boolean.TRUE)) {
                    r.setFill(Color.GREEN);
                    System.out.println("reached there");
//                    tempCorrect+=1;
                } else if (!m.get(i).equals(u.get(i)) && m.get(i).equals(Boolean.TRUE)) {
                    r.setFill(Color.YELLOW);
                } else if (!m.get(i).equals(u.get(i)) && m.get(i).equals(Boolean.FALSE)){
                    r.setFill(Color.RED);
                }
            }
        }
    }
}
