import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.rmi.runtime.NewThreadAction;

import java.util.Random;

public class Main extends Application {

    Label response;

    Double winSizeX = 800.0;
    Double winSizeY = 640.0;

    Double borderWidth = 20.0;
    Double borderHeight = 100.0;

    Random rand = new Random();

    public static void main(String[] args)
    {
        launch(args);
    }


    void  smoothButtonMove(Button btn,double x1, double y1, double x2, double y2, double time)
    {
        double dt = 0.0167;
        double n = time / dt;
        double dx = (x2-x1)/n;
        double dy = (y2-y1)/n;
        double x=x1, y= y1;

        for(int i=0; i<n; i++)
        {
            x+= dx;
            y+=dy;
            btn.setLayoutX(x);
            btn.setLayoutY(y);
            try {
                Thread.sleep(16);
            } catch (InterruptedException e)
            {

            }
        }
    }

    public void start(Stage myStage)
    {
        myStage.setTitle("Демонстрация работы с кнопками и событиями в JavaFX");
        Pane rootNode = new Pane();



        Scene myScene = new Scene(rootNode,winSizeX, winSizeY);

        myStage.setScene(myScene);

        response = new Label("Нажмите на кнопку");

        Button btnAlpha = new Button("Альфа");
        btnAlpha.setLayoutX(10);
        btnAlpha.setLayoutY(20);
        Button btnBeta = new Button("Бета");
        btnAlpha.setLayoutX(50);
        btnAlpha.setLayoutY(20);

        btnAlpha.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                response.setText("Нажата кнопка Альфа");
            }
        });

        btnBeta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                response.setText("Нажата кнопка Бета");
            }
        });

        btnBeta.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                double x =  rand.nextDouble()*(winSizeX - borderWidth);
                double y = rand.nextDouble()*(winSizeY - borderHeight);

                System.out.println();
                System.out.println(x);
                System.out.println(y);

                borderWidth = btnBeta.getWidth();
                borderHeight = btnBeta.getHeight();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        smoothButtonMove(btnBeta,btnBeta.getLayoutX(), btnBeta.getLayoutY(), x, y, 1);
                    }
                });

                t.start();



            }
        });

        myStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                winSizeX = (Double) newValue;
            }
        });

        myStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                winSizeY = (Double) newValue;
            }
        });



        rootNode.getChildren().addAll(btnAlpha, btnBeta, response);

        myStage.show();


    }
}
