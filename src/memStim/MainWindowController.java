package memStim;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import javafx.event.Event;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class MainWindowController {

    private Stage primaryStage = new Stage();
    private double x, y;

    @FXML
    private void handleNewGameBtn() {
        GridPane MainPane = new GridPane();
        int rows = 5;
        int columns = 5;
        MainPane.setPrefWidth(600);
        MainPane.setPrefHeight(600);
        double height = MainPane.getPrefHeight();
        double width = MainPane.getPrefWidth();
        int number = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int random = (int) (Math.random() * 2);
                Rectangle square = new Rectangle();
                double sqHeight = height / rows;
                double sqWidth = width / columns;
                square.setHeight(sqHeight);
                square.setWidth(sqWidth);
                MainPane.setColumnIndex(square, j);
                MainPane.setRowIndex(square, i);
                MainPane.getChildren().add(square);
                square.setStroke(Color.GREY);
                if (random == 0) {
                    square.setFill(Color.BLACK);
                } else {
                    square.setFill(Color.WHITE);
                }
            }
        }
        MainPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                System.out.println("The mouse was clicked at the point (" + x + "," + y + ")");
            }
        });
        Scene scene = new Scene(MainPane, 600, 600);
        primaryStage.setTitle("MemStim");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node getNodeWithColumnAndIndex(GridPane gp, int column, int row) {
        ObservableList<Node> list = gp.getChildren();
        for (Node n : list) {
            if (gp.getRowIndex(n) == row && gp.getColumnIndex(n) == column) {
                return n;
            }
        }
        return null;
    }
}
