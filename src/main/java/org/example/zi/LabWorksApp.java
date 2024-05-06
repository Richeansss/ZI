package org.example.zi;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class LabWorksApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Лабораторные работы");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER); // Центрирование элементов по вертикали

        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.CENTER);




        Button lab1Button = new Button("Лаб 1");
        Button lab2Button = new Button("Лаб 2");
        Button lab3Button = new Button("Лаб 3");
        Button lab4Button = new Button("Лаб 4");
        Button lab5Button = new Button("Лаб 5");


        buttonContainer.getChildren().addAll(lab1Button, lab2Button, lab3Button, lab4Button, lab5Button);


        lab1Button.setOnAction(e -> Lab1Window.display());

        lab2Button.setOnAction(e -> Lab2Window.display());

        lab3Button.setOnAction(e -> Lab3Window.display());

        lab4Button.setOnAction(e -> Lab4Window.display());

        lab5Button.setOnAction(e -> Lab5Window.display());

        root.getChildren().addAll(buttonContainer);

        Scene scene = new Scene(root, 400, 200); // Установка размеров сцены
        primaryStage.setScene(scene);
        primaryStage.show();

    }



    public static String getInputFromUser(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        dialog.showAndWait();
        return dialog.getResult();
    }
}
