import java.io.IOException;

class Runner {
    void run(String[] argsOfMain){
        try {
            FileWork fileWork = switch (argsOfMain.length) {
                case 0 -> {
                    CLI client = new CLI();
                    yield client.getFileWorkWithArgs();
                }
                case 2 -> {
                    if(argsOfMain[0].equals("BRUTE_FORCE")) { yield new FileWork(argsOfMain[0], argsOfMain[1], "");
                    }else  throw new RuntimeException("Перевірте кількість вхідних параметрів");
                }
                case 3 -> new FileWork(argsOfMain[0], argsOfMain[1], argsOfMain[2]);
                default -> throw new RuntimeException("Перевірте кількість вхідних параметрів");
            };

            fileWork.readAndWrite();

        }catch (NumberFormatException e){
            System.out.println("Перевірте правильність введеного ключа. Ключ повинен бути цілим числом");
        }catch (IOException e){
            System.out.println("Помилка читання/запису файлу. Перевірте правильність шляху до файлу");
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }

    }
}