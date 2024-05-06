package org.example.zi;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Lab2Window {

    public static void display() {
        Stage window = new Stage();
        window.setTitle("Лабораторная работа 2");

        // Текстовая метка с описанием программы
        String descriptionText = "Программа для шифрования и расшифровки текста методом замены.\n" +
                "Используются все буквы русского алфавита от а до я и от А до Я, а также символы: пробел, точка, двоеточие, восклицательный знак, вопросительный знак и запятая.";
        Label descriptionLabel = new Label(descriptionText);

        // Текстовые области для ввода, зашифрованного и расшифрованного текста
        TextField inputField = new TextField();
        inputField.setPromptText("Введите текст для шифрования/расшифровки");

        TextArea encryptedArea = new TextArea();
        encryptedArea.setEditable(false);
        encryptedArea.setWrapText(true);
        encryptedArea.setPromptText("Зашифрованный текст");

        TextArea decryptedArea = new TextArea();
        decryptedArea.setEditable(false);
        decryptedArea.setWrapText(true);
        decryptedArea.setPromptText("Расшифрованный текст");

        // Кнопки для шифрования и расшифрования
        Button encryptButton = new Button("Зашифровать");
        encryptButton.setOnAction(e -> {
            String key = LabWorksApp.getInputFromUser("Введите ключ для шифрования и расшифровки:");
            String originalText = inputField.getText();
            String encryptedText = Lab2.encryptWithSubstitution(originalText, key);
            encryptedArea.setText(encryptedText);

            // Дополнительно расшифруем текст для отображения
            String decryptedText = Lab2.decryptWithSubstitution(encryptedText, key);
            decryptedArea.setText(decryptedText);
        });

        // Контейнер VBox для компонентов
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(descriptionLabel, inputField, encryptButton, encryptedArea, decryptedArea);

        // Создание сцены и добавление к окну
        Scene scene = new Scene(layout, 500, 400);
        window.setScene(scene);
        window.show();
    }
}
