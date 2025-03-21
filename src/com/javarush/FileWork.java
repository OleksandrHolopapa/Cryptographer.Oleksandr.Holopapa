package com.javarush;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileWork {
    private final String command;
    private final Path initialFilePath;
    private Path finalFilePath;
    private String key;
    private String initialText;
    private String finalText;

    FileWork(String command, String initialFilePath, String key) {
        this.command = command;
        this.initialFilePath = Path.of(initialFilePath);
        this.key = key;
    }

    String getInitialText() {
        return initialText;
    }

    String getFinalText() {
        return finalText;
    }

    String getKey() {
        return key;
    }

    void readAndWrite() throws IOException {
        AlgorithmOfCaesar algorithm = new AlgorithmOfCaesar();
        initialText = Files.readString(initialFilePath);
        setFinalTextAndFinalFilePath(algorithm);
        Files.writeString(finalFilePath, finalText);
    }

    private int ifPointPresent(int pointLastIndex) {
        if (pointLastIndex != -1) return pointLastIndex;
        else throw new RuntimeException("Перевірте ім'я файлу. Потрібний формат імені файлу: ім'я файлу.тип файлу");
    }

    private String formFinalFilePath(String pathOfInitialFile, String commandActionOnTheFinalFile) {
        int lastIndexOfPoint = ifPointPresent(pathOfInitialFile.lastIndexOf("."));
        int lastIndexOfOpeningSquareBracket = pathOfInitialFile.lastIndexOf('[');
        return pathOfInitialFile.substring(0, lastIndexOfOpeningSquareBracket != -1 ? lastIndexOfOpeningSquareBracket : lastIndexOfPoint)
                + "[" + commandActionOnTheFinalFile + "]" + pathOfInitialFile.substring(lastIndexOfPoint);
    }

    private void bruteForce(AlgorithmOfCaesar algorithm) {
        key = "0";
        for (int i = 1; i < AlgorithmOfCaesar.LENGTH_OF_ALPHABET; i++) {
            String possibleFinalText = algorithm.decrypt(initialText, i);
            if (possibleFinalText.contains(", ") && possibleFinalText.contains(". ")) {
                key = "" + i;
                finalFilePath = Path.of(formFinalFilePath(initialFilePath.toString(), command + "D, KEY = " + key));
                finalText = possibleFinalText;
                break;
            }
        }
        if (key.equals("0")) throw new RuntimeException("Не вдалося підібрати ключ");
    }

    private void setFinalTextAndFinalFilePath(AlgorithmOfCaesar algorithm) {
        switch (command) {
            case "ENCRYPT" -> {
                finalFilePath = Path.of(formFinalFilePath(initialFilePath.toString(), command + "ED"));
                finalText = algorithm.encrypt(initialText, Integer.parseInt(key));
            }
            case "DECRYPT" -> {
                finalFilePath = Path.of(formFinalFilePath(initialFilePath.toString(), command + "ED"));
                finalText = algorithm.decrypt(initialText, Integer.parseInt(key));
            }
            case "BRUTE_FORCE" -> bruteForce(algorithm);
        }
    }
}