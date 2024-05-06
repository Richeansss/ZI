package org.example.zi;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Arrays;
import java.util.List;

public class Lab3 {

    private static final List<Character> ALPHABET = Arrays.asList(
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л',
            'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
            'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', ' ', '.', ':', '!', '?', ','
    );

    private static final RealMatrix keyMatrix = MatrixUtils.createRealMatrix(new double[][]{
            {1, 3, 2},
            {2, 1, 5},
            {3, 2, 1}
    });

    private static final RealMatrix cipherKeyInv = MatrixUtils.inverse(keyMatrix);

    public static RealMatrix encrypt(String inputText) {
        return multiply(createMatrix(inputText), keyMatrix);
    }

    public static RealMatrix decrypt(RealMatrix encryptedText) {
        return multiply(encryptedText, cipherKeyInv);
    }

    private static RealMatrix createMatrix(String text) {
        int columns = keyMatrix.getColumnDimension();
        int rows = (int) Math.ceil((double) text.length() / columns);
        double[][] matrixData = new double[rows][columns];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (index < text.length()) {
                    char ch = text.charAt(index++);
                    matrixData[i][j] = ALPHABET.indexOf(ch) + 1;
                } else {
                    matrixData[i][j] = ALPHABET.indexOf(' ') + 1;
                }
            }
        }
        return MatrixUtils.createRealMatrix(matrixData);
    }

    private static RealMatrix multiply(RealMatrix matrixText, RealMatrix matrixKey) {
        return matrixText.multiply(matrixKey);
    }

    static String convertMatrixToString(RealMatrix matrix) {
        StringBuilder sb = new StringBuilder();
        for (double[] row : matrix.getData()) {
            for (double val : row) {
                int index = (int) Math.round(val) - 1;
                sb.append(ALPHABET.get(index));
            }
        }
        return sb.toString();
    }
}
