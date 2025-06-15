package javaapplication3;

import java.io.*;
import java.nio.file.*;
import java.util.Random;

public class OneTimePadEncryptor {

    public static byte[] generateKey(int length) {
        byte[] key = new byte[length];
        new Random().nextBytes(key);
        return key;
    }

    public static byte[] xor(byte[] data, byte[] key) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte)(data[i] ^ key[i]);
        }
        return result;
    }

    public static void encrypt(String filePath) throws IOException {
        byte[] data = Files.readAllBytes(Paths.get(filePath));
        byte[] key = generateKey(data.length);
        byte[] encrypted = xor(data, key);

        Files.write(Paths.get(filePath + ".enc"), encrypted);
        Files.write(Paths.get(filePath + ".key"), key);
    }

    public static void decrypt(String encFilePath, String keyFilePath) throws IOException {
        byte[] encrypted = Files.readAllBytes(Paths.get(encFilePath));
        byte[] key = Files.readAllBytes(Paths.get(keyFilePath));
        byte[] decrypted = xor(encrypted, key);

        String outputPath = encFilePath.replace(".enc", ".decrypted");
        Files.write(Paths.get(outputPath), decrypted);
    }
}
