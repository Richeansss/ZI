package org.example.zi;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Lab4Window {

    private static PrivateKey privateKey; // Приватный ключ доступен во всем классе

    public static void display() {
        Stage window = new Stage();
        window.setTitle("Шифрование RSA");

        Label inputLabel = new Label("Введите сообщение для шифрования:");
        TextArea inputArea = new TextArea();
        inputArea.setWrapText(true);

        Button encryptButton = new Button("Зашифровать");
        TextArea outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false);

        Label publicKeyLabel = new Label("Публичный ключ RSA:");
        TextField publicKeyField = new TextField();
        publicKeyField.setEditable(false);

        Button copyKeyButton = new Button("Копировать ключ");

        Label decryptionLabel = new Label("Введите зашифрованный текст для расшифровки:");
        TextArea encryptedTextArea = new TextArea();
        encryptedTextArea.setWrapText(true);

        Button pasteButton = new Button("Вставить из буфера обмена");
        pasteButton.setOnAction(e -> {
            // Получение текста из буфера обмена и вставка его в текстовую область зашифрованного текста
            Clipboard clipboard = Clipboard.getSystemClipboard();
            if (clipboard.hasString()) {
                String clipboardText = clipboard.getString();
                encryptedTextArea.setText(clipboardText);
            }
        });

        Button decryptButton = new Button("Расшифровать");
        TextArea decryptedArea = new TextArea();
        decryptedArea.setWrapText(true);
        decryptedArea.setEditable(false);

        encryptButton.setOnAction(e -> {
            try {
                String message = inputArea.getText();

                // Генерация пары ключей RSA
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048); // Размер ключа
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                privateKey = keyPair.getPrivate(); // Сохранение приватного ключа

                // Шифрование сообщения
                byte[] encryptedMessage = Lab4.encrypt(message, publicKey);

                // Отображение зашифрованного сообщения в формате Base64
                outputArea.setText(Base64.getEncoder().encodeToString(encryptedMessage));

                // Отображение публичного ключа
                publicKeyField.setText(publicKey.toString());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        copyKeyButton.setOnAction(e -> {
            // Копирование зашифрованного сообщения в буфер обмена
            String encryptedMessage = outputArea.getText();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(encryptedMessage);
            clipboard.setContent(content);
        });

        decryptButton.setOnAction(e -> {
            try {
                String encryptedText = encryptedTextArea.getText();
                byte[] encryptedMessage = Base64.getDecoder().decode(encryptedText); // Декодировать Base64 обратно в байты

                // Расшифрование сообщения
                String decryptedMessage = Lab4.decrypt(encryptedMessage, privateKey);
                decryptedArea.setText(decryptedMessage); // Отобразить расшифрованное сообщение

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox inputLayout = new VBox(5);
        inputLayout.getChildren().addAll(inputLabel, inputArea, encryptButton);

        VBox outputLayout = new VBox(5);
        outputLayout.getChildren().addAll(publicKeyLabel, new HBox(publicKeyField, copyKeyButton), outputArea);

        VBox decryptionLayout = new VBox(5);
        decryptionLayout.getChildren().addAll(decryptionLabel, encryptedTextArea, pasteButton,decryptedArea, decryptButton );

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10));
        layout.setVgap(10);
        layout.setHgap(10);
        layout.add(inputLayout, 0, 0);
        layout.add(outputLayout, 0, 1);
        layout.add(decryptionLayout, 0, 2);

        Scene scene = new Scene(layout, 400, 600); // Увеличил высоту сцены для удобства
        window.setScene(scene);
        window.show();
    }
}
