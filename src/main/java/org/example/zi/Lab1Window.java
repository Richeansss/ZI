package org.example.zi;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Lab1Window {

    public static void display() {
        Stage window = new Stage();
        window.setTitle("Лабораторная работа 1");

        // Текстовая метка с описанием программы
        String descriptionText = "Программа для шифрования и расшифровки текста методом прямоугольника Полибия.\n";
        Label descriptionLabel = new Label(descriptionText);
        descriptionLabel.setFont(Font.font("Arial", 11)); // Устанавливаем шрифт

        // Текстовые области для ввода, зашифрованного и расшифрованного текста
        TextArea inputArea = new TextArea();
        inputArea.setPromptText("Текст для шифрования/расшифровки");

        TextArea encryptedArea = new TextArea();
        encryptedArea.setEditable(false);
        encryptedArea.setWrapText(true);
        encryptedArea.setPromptText("Зашифрованный текст");

        TextArea decryptedArea = new TextArea();
        decryptedArea.setEditable(false);
        decryptedArea.setWrapText(true);
        decryptedArea.setPromptText("Расшифрованный текст");

        // Кнопки для шифрования и расшифровки
        Button encryptButton = new Button("Зашифровать");
        encryptButton.setOnAction(e -> {
            String originalText = inputArea.getText();
            String encryptedText = Lab1.encryptText(originalText);
            encryptedArea.setText(encryptedText);
        });

        Button decryptButton = new Button("Расшифровать");
        decryptButton.setOnAction(e -> {
            String encryptedText = encryptedArea.getText();
            String decryptedText = Lab1.decryptText(encryptedText);
            decryptedArea.setText(decryptedText);
        });

        // Контейнер VBox для компонентов
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(new Label("Описание программы:"), descriptionLabel, inputArea, encryptButton, encryptedArea, decryptButton, decryptedArea);

        // Создание сцены и добавление к окну
        Scene scene = new Scene(layout, 500, 400);
        window.setScene(scene);
        window.show();
    }
}

