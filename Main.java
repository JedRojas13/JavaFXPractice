package sample;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class Main extends Application {

//    private static final Object Circle = new Circle();
    double orgSceneX, orgSceneY;
    ArrayList<Circle> circles = new ArrayList<>();
    Stage window;
    Scene scene1, scene2;

    private Circle createCircle(double x, double y, double r, Color color, String name) {
        Circle circle = new Circle(x, y, r, color);
        circle.setCursor(Cursor.HAND);

        circle.setOnMousePressed((t) -> {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            Circle c = (Circle) (t.getSource());
            c.toFront();
        });
        circle.setOnMouseDragged((t) -> {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;

            Circle c = (Circle) (t.getSource());

            c.setCenterX(c.getCenterX() + offsetX);
            c.setCenterY(c.getCenterY() + offsetY);

            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
        });

        // added code
        circles.add(circle);
        return circle;
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        // labels and buttons for 1
        Label label1 = new Label("Scene 1:");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));

        // layout1
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 500, 260);


        // labels and buttons for 2
        Label label2 = new Label("Scene 2:");
        Button button2 = new Button("Go to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));

        // layout 2
        Group root = new Group();
        root.getChildren().addAll(label2, button2);
        scene2 = new Scene(root, 500, 260);

        // code to add circles on dbl click
        scene2.setOnMouseClicked(mouseEvent -> {
            System.out.println("clicked! " + mouseEvent.getTarget() + " " + mouseEvent.getSource());
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            if(mouseEvent.getClickCount()==2) {
                Circle circle = createCircle(x, y, 15, Color.BLUE, "Jed");
                System.out.println("cirrcle after createCircle: " + circle.toString());
                root.getChildren().add(circle);

                // deletes on right click
                circle.setOnMouseClicked(e -> {
                    if(e.getButton()== MouseButton.SECONDARY) {
                        System.out.println("cirrcle after setOnMouseClicked: " + circle.toString());
                        root.getChildren().remove(circle);
                    } else if (e.getButton()==MouseButton.PRIMARY){
//                        editCircle(circle);
                        //pull up editCircle menu
                    }
                });
            }
        });

        window.setScene(scene1);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}






























