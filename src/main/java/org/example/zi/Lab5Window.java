package org.example.zi;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Lab5Window {

    public static void display() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Шифрование FEAL-N");

        Label selectedKeyLabel = new Label("Выбранный ключ: ");
        Label selectedKeyDisplay = new Label("Ключ не выбран");

        Label inputLabel = new Label("Введите текст для шифрования:");
        TextArea inputArea = new TextArea();
        inputArea.setWrapText(true);

        Label outputLabel = new Label("Расшифрованный текст:");
        TextArea outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false);

        Label encryptedBlockLabel = new Label("Зашифрованный блок:");
        TextArea encryptedBlockArea = new TextArea();
        encryptedBlockArea.setWrapText(true);

        Button encryptButton = new Button("Зашифровать");
        Button decryptButton = new Button("Расшифровать");

        Button key64Button = new Button("64-битный ключ");
        key64Button.setOnAction(e -> selectedKeyDisplay.setText("64-битный ключ"));

        Button key128Button = new Button("128-битный ключ");
        key128Button.setOnAction(e -> selectedKeyDisplay.setText("128-битный ключ"));

        Button key256Button = new Button("256-битный ключ");
        key256Button.setOnAction(e -> selectedKeyDisplay.setText("256-битный ключ"));

        HBox keyButtonsLayout = new HBox(10);
        keyButtonsLayout.getChildren().addAll(key64Button, key128Button, key256Button);

        encryptButton.setOnAction(e -> {
            String keyText = selectedKeyDisplay.getText();
            String inputText = inputArea.getText();
            if (!keyText.isEmpty() && !inputText.isEmpty()) {
                try {
                    byte[] inputBytes = inputText.getBytes(StandardCharsets.UTF_8);
                    BigInteger key = new BigInteger(keyText.split("-")[0]);
                    byte[] encryptedBytes = Lab5.encrypt(key, inputBytes);
                    encryptedBlockArea.setText(new BigInteger(1, encryptedBytes).toString());
                } catch (NumberFormatException ex) {
                    outputArea.setText("Неверный ввод. Пожалуйста, введите целые числа для ключа.");
                }
            } else {
                outputArea.setText("Пожалуйста, введите как ключ, так и текст для шифрования.");
            }
        });

        decryptButton.setOnAction(e -> {
            String keyText = selectedKeyDisplay.getText();
            String encryptedBlockText = encryptedBlockArea.getText();
            if (!keyText.isEmpty() && !encryptedBlockText.isEmpty()) {
                try {
                    byte[] encryptedBytes = new BigInteger(encryptedBlockText).toByteArray();
                    BigInteger key = new BigInteger(keyText.split("-")[0]);
                    byte[] decryptedBytes = Lab5.decrypt(key, encryptedBytes);
                    outputArea.setText(new String(decryptedBytes, StandardCharsets.UTF_8));
                } catch (NumberFormatException ex) {
                    outputArea.setText("Неверный ввод. Пожалуйста, введите целые числа для ключа.");
                }
            } else {
                outputArea.setText("Пожалуйста, введите как ключ, так и зашифрованный блок.");
            }
        });

        VBox inputLayout = new VBox(10);
        inputLayout.getChildren().addAll(selectedKeyLabel, selectedKeyDisplay, keyButtonsLayout, inputLabel, inputArea, encryptButton);

        VBox outputLayout = new VBox(10);
        outputLayout.getChildren().addAll(outputLabel, outputArea);

        VBox encryptedBlockLayout = new VBox(10);
        encryptedBlockLayout.getChildren().addAll(encryptedBlockLabel, encryptedBlockArea, decryptButton);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10));
        layout.setVgap(20);
        layout.setHgap(20);
        layout.add(inputLayout, 0, 0);
        layout.add(outputLayout, 0, 1);
        layout.add(encryptedBlockLayout, 1, 0);

        Scene scene = new Scene(layout, 800, 400); // Увеличил ширину сцены
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
