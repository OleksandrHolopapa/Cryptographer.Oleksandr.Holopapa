import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileWork {
    private final String command;
    private final Path sourceFilePath;
    private Path destinationFilePath;
    private final String key;

    FileWork(String command, String path, String key) {
        this.command = command;
        this.sourceFilePath = Path.of(path);
        this.destinationFilePath = Path.of(getDestinationFileName(path, command+"ED"));
        this.key = key;
    }

    void readAndWrite() throws IOException {
            AlgorithmOfCaesar algorithm = new AlgorithmOfCaesar();
            String initialText = Files.readString(sourceFilePath);
            String finalText = switch (command) {
                case "ENCRYPT" -> algorithm.coding(initialText, Integer.parseInt(key));
                case "DECRYPT" -> algorithm.decoding(initialText, Integer.parseInt(key));
                case "BRUTE_FORCE" -> returnFinalTextWithBruteForce(initialText, algorithm);
                default -> throw new RuntimeException("Невідома команда");
            };
            Files.writeString(destinationFilePath, finalText);
    }

    private String returnFinalTextWithBruteForce(String initialText, AlgorithmOfCaesar algorithm){
        int key = getKey(initialText, algorithm);
        destinationFilePath = Path.of(getDestinationFileName(sourceFilePath.getParent()+"/"+ sourceFilePath.getFileName(), command+", KEY = "+key));
        return algorithm.decoding(initialText, key);
    }

    private int checkingOfPointLastIndex(int pointLastIndex){
        if(pointLastIndex!=-1) return pointLastIndex;
        else throw new RuntimeException("Перевірте правильність шляху до файлу");
    }

    private String getDestinationFileName(String oldFileName, String fileStatus) {
        int pointLastIndex = checkingOfPointLastIndex(oldFileName.lastIndexOf("."));
        int symbolFirstIndex = oldFileName.indexOf('[');
        return oldFileName.substring(0, symbolFirstIndex!=-1? symbolFirstIndex:pointLastIndex) +"["+ fileStatus +"]"+ oldFileName.substring(pointLastIndex);
    }

    private int getKey(String text, AlgorithmOfCaesar algorithm){
        int key = 0;
        for (int i = 1; i < AlgorithmOfCaesar.LENGTH_OF_ALPHABET; i++) {
            String newText = algorithm.decoding(text, i);
            if(newText.contains(", ")&&newText.contains(". ")) {key = i; break;}
        }
        return key;
    }

}

