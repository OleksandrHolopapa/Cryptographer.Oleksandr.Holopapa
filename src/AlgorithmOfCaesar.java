class AlgorithmOfCaesar {

    private final static String ENGLISH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,«»\"\\:!?<>(){}";
    private final static String UKRAINIAN_ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя ";
    final static int LENGTH_OF_ALPHABET = ENGLISH_ALPHABET.length();

    private char codingSymbolOfInitialTex(char symbolOfInitialTex, int key, String usedAlphabet) {
        int positionOfSymbolInAlphabet = usedAlphabet.indexOf(symbolOfInitialTex);
        int positionOfNewSymbolInAlphabet = positionOfSymbolInAlphabet + key;
        while (positionOfNewSymbolInAlphabet < 0) {
            positionOfNewSymbolInAlphabet = usedAlphabet.length() + positionOfNewSymbolInAlphabet;
        }
        return usedAlphabet.charAt(positionOfNewSymbolInAlphabet % usedAlphabet.length());
    }

    private boolean isUkrainianAlphabet(char symbolOfInitialTex) {
        return UKRAINIAN_ALPHABET.indexOf(symbolOfInitialTex) != -1;
    }

    private boolean isEnglishAlphabet(char symbolOfInitialTex) {
        return ENGLISH_ALPHABET.indexOf(symbolOfInitialTex) != -1;
    }

    private String checkAlphabetAndFormFinalText(String initialText, int key) {
        StringBuilder finalText = new StringBuilder(initialText.length());
        char symbolOfInitialText;
        for (int i = 0; i < initialText.length(); i++) {
            symbolOfInitialText = initialText.charAt(i);
            if (isEnglishAlphabet(symbolOfInitialText)) {
                finalText.append(codingSymbolOfInitialTex(symbolOfInitialText, key, ENGLISH_ALPHABET));
            } else if (isUkrainianAlphabet(symbolOfInitialText)) {
                finalText.append(codingSymbolOfInitialTex(symbolOfInitialText, key, UKRAINIAN_ALPHABET));
            } else finalText.append(symbolOfInitialText);
        }
        return finalText.toString();
    }

    String encrypt(String initialTex, int key) {
        return checkAlphabetAndFormFinalText(initialTex, key);
    }

    String decrypt(String initialTex, int key) {
        return checkAlphabetAndFormFinalText(initialTex, -key);
    }

}
