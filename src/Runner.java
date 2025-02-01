import java.io.IOException;

class Runner {
    private FileWork fileWork;
    FileWork getFileWork() {
        return fileWork;
    }

    void run(String[] argsOfMain, CLI frame){
        try {
            fileWork = switch (argsOfMain.length) {
                case 2 -> {
                    if(argsOfMain[0].equals("BRUTE_FORCE")) {
                        yield new FileWork(argsOfMain[0], argsOfMain[1], "");
                    }else  throw new RuntimeException("Перевірте кількість вхідних параметрів");
                }
                case 3 -> new FileWork(argsOfMain[0], argsOfMain[1], argsOfMain[2]);
                default -> throw new RuntimeException("Перевірте кількість вхідних параметрів");
            };
            fileWork.readAndWrite();
            if(argsOfMain[0].equals("BRUTE_FORCE")) frame.showMassage("Ключ, отриманий за допомогою BRUTE FORCE = "+fileWork.getBruteForceKey());
        }catch (NumberFormatException e){
            frame.showMassage("Перевірте правильність введеного ключа. Ключ повинен бути цілим числом");
        }catch (RuntimeException e){
            frame.showMassage(e.getMessage());
        }catch (IOException e){
            frame.showMassage("Помилка читання/запису файлу. Перевірте правильність шляху до файлу");
        }

    }
}