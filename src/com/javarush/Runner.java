package com.javarush;

import java.io.IOException;

class Runner {
    private FileWork fileWork;

    FileWork getFileWork() {
        return fileWork;
    }

    void run(String[] argsOfMain, CLI frame) {
        try {
            fileWork = new FileWork(argsOfMain[0], argsOfMain[1], argsOfMain[2]);
            fileWork.readAndWrite();
            if (argsOfMain[0].equals("BRUTE_FORCE")) {
                frame.showMassage("Ключ, отриманий за допомогою BRUTE FORCE = " + fileWork.getKey());
            }
        } catch (NumberFormatException e) {
            frame.showMassage("Перевірте правильність введеного ключа. Ключ повинен бути цілим числом");
        } catch (RuntimeException e) {
            frame.showMassage(e.getMessage());
        } catch (IOException e) {
            frame.showMassage("Помилка читання/запису файлу. Перевірте правильність шляху до файлу");
        }
    }
}