import java.util.Scanner;

class CLI {
    FileWork getFileWorkWithArgs(){
        FileWork fileWork;
        try(Scanner scanner = new Scanner(System.in)){
            String command = getCommand(scanner);
            String path = getPath(scanner);
            String key = getKey(command, scanner);
            fileWork = new FileWork(command, path, key);
        }
        return fileWork;
    }

    private String getCommand(Scanner scanner){
        System.out.println("Оберіть команду:");
        System.out.println("Щоб зашифрувати файл, введіть 1");
        System.out.println("Щоб розшифрувати файл, введіть 2");
        System.out.println("Щоб розшифрувати файл без ключа, введіть 3");
        String command = scanner.nextLine();
        try {
            switch (Integer.parseInt(command)){
                case 1 -> command = "ENCRYPT";
                case 2 -> command = "DECRYPT";
                case 3 -> command = "BRUTE_FORCE";
                default -> throw new RuntimeException("Неправильна команда!");
        }
    }catch (NumberFormatException e){
            throw new RuntimeException("Неправильна команда!");
        }
        return command;
    }

    private String getKey(String command, Scanner scanner){
        String key = "";
        if(!command.equals("BRUTE_FORCE")) {
            System.out.println("Введіть ключ(ціле число) для "+(command.equals("ENCRYPT")? "шифрування":"розшифрування"));
            key = scanner.nextLine();
        }
        return key;
    }

    private String getPath(Scanner scanner){
        System.out.println("Вкажіть шлях до початкового файлу");
        return scanner.nextLine();
    }
}