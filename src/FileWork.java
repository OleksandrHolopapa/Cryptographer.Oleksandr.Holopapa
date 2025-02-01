import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileWork {
    private final String command;
    private final Path sourceFilePath;
    private Path destinationFilePath;
    private final String key;
    private String initialText;
    private String finalText;
    private int bruteForceKey;

    FileWork(String command, String path, String key) {
        this.command = command;
        this.sourceFilePath = Path.of(path);
        this.destinationFilePath = Path.of(getDestinationFileName(path, command+"ED"));
        this.key = key;
    }

    String getInitialText() {
        return initialText;
    }

    String getFinalText() {
        return finalText;
    }

    int getBruteForceKey() {
        return bruteForceKey;
    }

    void readAndWrite() throws IOException {
        AlgorithmOfCaesar algorithm = new AlgorithmOfCaesar();
        initialText = Files.readString(sourceFilePath);
        initializeFinalText(command, algorithm);
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
        bruteForceKey = 0;
        for (int i = 1; i < AlgorithmOfCaesar.LENGTH_OF_ALPHABET; i++) {
            String newText = algorithm.decoding(text, i);
            if(newText.contains(", ")&&newText.contains(". ")) {bruteForceKey = i; break;}
        }
        return bruteForceKey;
    }
    private void initializeFinalText(String command, AlgorithmOfCaesar algorithm) {
        switch (command) {
            case "ENCRYPT" -> finalText = algorithm.coding(initialText, Integer.parseInt(key));
            case "DECRYPT" -> finalText = algorithm.decoding(initialText, Integer.parseInt(key));
            case "BRUTE_FORCE" -> finalText = returnFinalTextWithBruteForce(initialText, algorithm);
        }
    }

}

