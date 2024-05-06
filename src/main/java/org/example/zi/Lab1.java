package org.example.zi;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Lab1 {

    private static final char[][] POLYBIUS_TABLE = {
            {'а', 'б', 'в', 'г', 'д', 'е', 'ё'},
            {'ж', 'з', 'и', 'й', 'к', 'л', 'м'},
            {'н', 'о', 'п', 'р', 'с', 'т', 'у'},
            {'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ'},
            {'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б'},
            {'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З'},
            {'И', 'Й', 'К', 'Л', 'М', 'Н', 'О'},
            {'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х'},
            {'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь'},
            {'Э', 'Ю', 'Я', ' ', '.', ':', '!'},
            {'?', ','}
    };

    public static Button createLab1Button(TextField inputField) {
        Button lab1Button = new Button("Лаб 1");
        lab1Button.setOnAction(e -> {
            inputField.setVisible(true);
            String originalText = inputField.getText();
            System.out.println("Информация по Лабораторной работе 1. Введенный текст: " + originalText);
        });
        return lab1Button;
    }

    static String encryptText(String text) {
        StringBuilder encryptedText = new StringBuilder();

        for (char symbol : text.toCharArray()) {
            boolean found = false;
            for (int row = 0; row < POLYBIUS_TABLE.length; row++) {
                for (int col = 0; col < POLYBIUS_TABLE[row].length; col++) {
                    if (POLYBIUS_TABLE[row][col] == symbol) {
                        encryptedText.append(row + 1).append(col + 1).append(" ");
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        }

        return encryptedText.toString().trim();
    }

    // Метод для расшифровки текста с использованием таблицы Полибия
    static String decryptText(String encryptedText) {
        StringBuilder decryptedText = new StringBuilder();

        String[] tokens = encryptedText.split(" ");
        for (String token : tokens) {
            if (token.length() == 2) {
                int row = Character.digit(token.charAt(0), 10) - 1;
                int col = Character.digit(token.charAt(1), 10) - 1;

                if (row >= 0 && row < POLYBIUS_TABLE.length && col >= 0 && col < POLYBIUS_TABLE[row].length) {
                    char decodedChar = POLYBIUS_TABLE[row][col];
                    decryptedText.append(decodedChar);
                } else {
                    decryptedText.append(' ');
                }
            } else {
                decryptedText.append(' ');
            }
        }

        return decryptedText.toString();
    }

}