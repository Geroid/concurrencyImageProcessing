package com.Geroid.ComProcesses;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
    public void start(Stage stage) throws Exception{
        NumberAxis x = new NumberAxis();
        x.setLabel("Threads");
        NumberAxis y = new NumberAxis();
        y.setLabel("Time");
        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(x,y);

        TextField user = new TextField();
        Button start = new Button("Start test");
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int threadCount = Integer.valueOf(user.getText());
                XYChart.Series series = new XYChart.Series();
                series.setName(String.valueOf(threadCount));
                for(int i = 1;i <= threadCount;i++ ) {
                    ImageStore store = new ImageStore("image.jpg");
                    store.phaser.register();
                    int step = store.image.getHeight() / i;
                    for (int threadIndex = 0; threadIndex < i; threadIndex++) {
                        new Worker(store, threadIndex * step, (threadIndex + 1) * step).start();
                    }
                    long startTime = System.currentTimeMillis();
                    store.phaser.arriveAndAwaitAdvance();
                    store.phaser.arriveAndAwaitAdvance();
                    long endTime = System.currentTimeMillis();

                    store.saveResult();
                    XYChart.Data d = new XYChart.Data(Integer.valueOf(i), endTime - startTime);
                    series.getData().add(d);
                }
                lineChart.getData().add(series);
            }
        });
        HBox contorl = new HBox(user, start);
        contorl.setAlignment(Pos.CENTER);
        VBox group = new VBox(lineChart,contorl);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("test");
        stage.setHeight(400);
        stage.setWidth(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
