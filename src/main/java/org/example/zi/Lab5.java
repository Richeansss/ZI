package org.example.zi;

import java.math.BigInteger;

public class Lab5 {

    private static final int BLOCK_SIZE = 64;
    private static final int BLOCK_BYTE_SIZE = BLOCK_SIZE / 8; // 8 байтов

    private static final int KEY_128_SIZE = 128;
    private static final int KEY_256_SIZE = 256;

    private static final int BLOCK_128_SIZE = 128;
    private static final int BLOCK_256_SIZE = 256;

    private static final int BLOCK_128_BYTE_SIZE = BLOCK_128_SIZE / 8; // 16 байтов
    private static final int BLOCK_256_BYTE_SIZE = BLOCK_256_SIZE / 8; // 32 байта

    public static byte[] encrypt(BigInteger key, byte[] inputBytes) {
        // Преобразование входных данных в битовую последовательность
        BigInteger block = new BigInteger(inputBytes);

        // Вычисление количества раундов
        int roundCount = calculateRoundCount(key);

        // Разбиение ключа на подключи
        BigInteger[] subkeys = generateSubKeys(key, roundCount);

        BigInteger l = block.shiftRight(BLOCK_SIZE / 2);
        BigInteger r = block.and(BigInteger.valueOf((1L << (BLOCK_SIZE / 2)) - 1));

        for (int i = 0; i < roundCount; i++) {
            BigInteger roundKey = subkeys[i];
            BigInteger temp = fFunction(r, roundKey).xor(l);
            l = r;
            r = temp;
        }

        // Сборка зашифрованного блока
        BigInteger encryptedBlock = l.shiftLeft(BLOCK_SIZE / 2).add(r);
        byte[] encryptedBytes = encryptedBlock.toByteArray();

        // Убедимся, что длина массива зашифрованных байтов соответствует BLOCK_BYTE_SIZE
        if (encryptedBytes.length > BLOCK_BYTE_SIZE) {
            // Просто возвращаем зашифрованные байты без обрезки
            return encryptedBytes;
        } else if (encryptedBytes.length < BLOCK_BYTE_SIZE) {
            // Дополняем зашифрованные байты нулями до BLOCK_BYTE_SIZE
            byte[] paddedBytes = new byte[BLOCK_BYTE_SIZE];
            System.arraycopy(encryptedBytes, 0, paddedBytes, BLOCK_BYTE_SIZE - encryptedBytes.length, encryptedBytes.length);
            return paddedBytes;
        } else {
            // Возвращаем зашифрованные байты без изменений
            return encryptedBytes;
        }
    }

    public static byte[] decrypt(BigInteger key, byte[] encryptedBytes) {
        // Преобразование зашифрованных данных в битовую последовательность
        BigInteger block = new BigInteger(1, encryptedBytes); // Используем конструктор с указанием сдвига, чтобы избежать потери ведущих нулей

        // Вычисление количества раундов
        int roundCount = calculateRoundCount(key);

        // Разбиение ключа на подключи
        BigInteger[] subkeys = generateSubKeys(key, roundCount);

        // Выделение левой и правой частей блока
        BigInteger l = block.shiftRight(BLOCK_SIZE / 2);
        BigInteger r = block.and(BigInteger.valueOf((1L << (BLOCK_SIZE / 2)) - 1));

        // Расшифровка
        for (int i = roundCount - 1; i >= 0; i--) {
            BigInteger roundKey = subkeys[i];
            BigInteger temp = fFunction(l, roundKey).xor(r);
            r = l;
            l = temp;
        }

        // Пересборка расшифрованного блока
        BigInteger decryptedBlock = l.shiftLeft(BLOCK_SIZE / 2).add(r);
        byte[] decryptedBytes = decryptedBlock.toByteArray();

        // Уберем лишние нули в начале, если они есть
        int leadingZeros = BLOCK_BYTE_SIZE - decryptedBytes.length;
        if (leadingZeros > 0) {
            byte[] paddedBytes = new byte[BLOCK_BYTE_SIZE];
            System.arraycopy(decryptedBytes, 0, paddedBytes, leadingZeros, decryptedBytes.length);
            decryptedBytes = paddedBytes;
        }

        return decryptedBytes;
    }

    // Метод вычисления количества раундов на основе размера ключа
    private static int calculateRoundCount(BigInteger key) {
        int bitLength = key.bitLength();
        if (bitLength <= KEY_128_SIZE) {
            return 6; // например, для 128-битового ключа
        } else if (bitLength <= KEY_256_SIZE) {
            return 8; // например, для 256-битового ключа
        } else {
            return 0; // обработка других случаев
        }
    }

    // Метод генерации подключей
    private static BigInteger[] generateSubKeys(BigInteger key, int roundCount) {
        BigInteger[] subkeys = new BigInteger[roundCount];
        for (int i = 0; i < roundCount; i++) {
            subkeys[i] = key.shiftLeft(i);
        }
        return subkeys;
    }

    // Функция f
    private static BigInteger fFunction(BigInteger r, BigInteger roundKey) {
        return r.xor(roundKey);
    }
}
