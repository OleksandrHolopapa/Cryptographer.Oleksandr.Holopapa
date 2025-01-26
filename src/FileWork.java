import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWork {
    private String command;
    private Path source_file;
    private Path destination_file;
    private String key;

    public FileWork(String command, String path, String key) {
        this.command = command;
        this.source_file = Path.of(path);
        this.destination_file = Path.of(getDestinationFileName(path, command+"ED"));
        this.key = key;
    }

    public void readAndWrite(){
        try {
            AlgorithmOfCaesar algorithm = new AlgorithmOfCaesar();
            String initialText = Files.readString(source_file);
            String finalText = switch (command) {
                case "ENCRYPT" -> algorithm.coding(initialText, Integer.parseInt(key));
                case "DECRYPT" -> algorithm.decoding(initialText, Integer.parseInt(key));
                case "BRUTE_FORCE" -> returnNewTextWithBruteForce(initialText, algorithm);
                default -> throw new RuntimeException("Невідома команда");
            };
            Files.writeString(destination_file, finalText);

        }catch (NumberFormatException e){
            System.out.println("Перевірте правильність введеного ключа. Ключ повинен бути цілим числом");
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println("Помилка читання з файлу. Перевірте правильність шляху до файлу");
        }
    }

    private String returnNewTextWithBruteForce(String initialText, AlgorithmOfCaesar algorithm){
        int key = get_key(initialText, algorithm);
        destination_file = Path.of(getDestinationFileName(source_file.getParent()+"/"+source_file.getFileName(), command+", KEY = "+key));
        return algorithm.decoding(initialText, key);
    }

    private static String getDestinationFileName(String oldFileName, String status) {
        int pointLastIndex = oldFileName.lastIndexOf(".");
        int symbolFirstIndex = oldFileName.indexOf('[');
        return oldFileName.substring(0, symbolFirstIndex!=-1? symbolFirstIndex:pointLastIndex) +"["+ status +"]"+ oldFileName.substring(pointLastIndex);
    }

    private int get_key(String text, AlgorithmOfCaesar algorithm){
        int key = 0;
        for (int i = 1; i < 69; i++) {
            String newText = algorithm.decoding(text, i);
            if(newText.contains(", ")&&newText.contains(". ")) {key = i; break;}
        }
        return key;
    }

}

