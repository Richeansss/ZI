package org.example.zi;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Lab2 {

    private static final char[] SUBSTITUTION_TABLE = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л',
            'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А',
            'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф',
            'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', ' ', '.', ':', '!', '?'};

    public static Button createLab2Button(TextField inputField, TextArea encryptedArea, TextArea decryptedArea) {
        Button lab2Button = new Button("Лаб 2");
        lab2Button.setOnAction(e -> {
            inputField.setVisible(true);
            String key = LabWorksApp.getInputFromUser("Введите ключ для шифрования и расшифровки:");
            String originalText = LabWorksApp.getInputFromUser("Введите текст для шифрования:");
            String encryptedText = encryptWithSubstitution(originalText, key);
            String decryptedText = decryptWithSubstitution(encryptedText, key);
            encryptedArea.setText(encryptedText);
            decryptedArea.setText(decryptedText);
        });
        return lab2Button;
    }

    static String encryptWithSubstitution(String text, String key) {
        StringBuilder encryptedText = new StringBuilder();

        for (char symbol : text.toCharArray()) {
            int index = findIndex(symbol, SUBSTITUTION_TABLE);
            if (index != -1) {
                int encryptedIndex = (index + findIndex(key.charAt(0), SUBSTITUTION_TABLE)) % SUBSTITUTION_TABLE.length;
                encryptedText.append(SUBSTITUTION_TABLE[encryptedIndex]);
            } else {
                encryptedText.append(symbol);
            }
        }

        return encryptedText.toString();
    }

    static String decryptWithSubstitution(String encryptedText, String key) {
        StringBuilder decryptedText = new StringBuilder();

        for (char symbol : encryptedText.toCharArray()) {
            int index = findIndex(symbol, SUBSTITUTION_TABLE);
            if (index != -1) {
                int decryptedIndex = (index - findIndex(key.charAt(0), SUBSTITUTION_TABLE) + SUBSTITUTION_TABLE.length)
                        % SUBSTITUTION_TABLE.length;
                decryptedText.append(SUBSTITUTION_TABLE[decryptedIndex]);
            } else {
                decryptedText.append(symbol);
            }
        }

        return decryptedText.toString();
    }

    private static int findIndex(char symbol, char[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == symbol) {
                return i;
            }
        }
        return -1;
    }
}
