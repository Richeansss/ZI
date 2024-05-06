package org.example.zi;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.math3.linear.RealMatrix;

import static org.example.zi.Lab3.convertMatrixToString;

public class Lab3Window {

    public static void display() {
        Stage window = new Stage();
        window.setTitle("Лабораторная работа 3");

        // Описание программы
        String descriptionText = "Программа для шифрования и расшифровки текста методом умножения матриц.";
        Label descriptionLabel = new Label(descriptionText);

        // Текстовое поле для ввода текста
        TextField inputField = new TextField();
        inputField.setPromptText("Введите текст для шифрования/расшифровки");

        // Текстовые области для отображения зашифрованного и расшифрованного текста
        TextArea encryptedArea = new TextArea();
        encryptedArea.setEditable(false);
        encryptedArea.setWrapText(true);
        encryptedArea.setPromptText("Зашифрованный текст");

        TextArea decryptedArea = new TextArea();
        decryptedArea.setEditable(false);
        decryptedArea.setWrapText(true);
        decryptedArea.setPromptText("Расшифрованный текст");

        // Кнопка для выполнения операции шифрования и расшифрования
        Button lab3Button = new Button("Выполнить");

        lab3Button.setOnAction(e -> {
            String originalText = inputField.getText();

            // Проверка на кириллические символы в тексте
            if (!containsCyrillic(originalText)) {
                encryptedArea.setText("Текст должен содержать кириллические символы!");
                decryptedArea.setText("Текст должен содержать кириллические символы!");
                return;
            }

            RealMatrix encryptedText = Lab3.encrypt(originalText);
            RealMatrix decryptedText = Lab3.decrypt(encryptedText);
            encryptedArea.setText(toStringMatrix(encryptedText));
            decryptedArea.setText(convertMatrixToString(decryptedText));
        });

        // Контейнер VBox для компонентов
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(
                descriptionLabel,
                inputField,
                lab3Button,
                encryptedArea,
                decryptedArea
        );

        // Создание сцены и добавление к окну
        Scene scene = new Scene(layout, 500, 400);
        window.setScene(scene);
        window.show();
    }

    // Метод для проверки наличия кириллических символов в строке
    private static boolean containsCyrillic(String text) {
        for (char ch : text.toCharArray()) {
            if ((ch >= '\u0410' && ch <= '\u044F') || (ch == 'ё' || ch == 'Ё')) {
                return true;
            }
        }
        return false;
    }

    // Метод для преобразования матрицы в строку для вывода
    private static String toStringMatrix(RealMatrix matrix) {
        StringBuilder sb = new StringBuilder();
        for (double[] row : matrix.getData()) {
            for (double val : row) {
                sb.append((int) Math.round(val)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}
