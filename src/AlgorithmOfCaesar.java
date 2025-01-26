public class AlgorithmOfCaesar {

    private final static String ENGLISH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,«»\"\\:!?<>(){}";
    private final static String UKRAINIAN_ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя ";

    private char codingSymbol(char ch, int key, String alphabet){
        int pos = alphabet.indexOf(ch);
        int new_pos = pos+key;
        while(new_pos<0) new_pos = alphabet.length()+new_pos;
        return alphabet.charAt(new_pos%alphabet.length());
    }

    private boolean isUkrainianAlphabet(char ch){
        return UKRAINIAN_ALPHABET.indexOf(ch)!=-1;
    }

    private boolean isEnglishAlphabet(char ch){
        return ENGLISH_ALPHABET.indexOf(ch)!=-1;
    }

    private String checkAlphabetAndFormNewText(String text, int key){
        StringBuilder newText = new StringBuilder(text.length());
        char ch;
        for (int i = 0; i < text.length(); i++) {
            ch = text.charAt(i);
            if (isEnglishAlphabet(ch)) {
                newText.append(codingSymbol(ch, key, ENGLISH_ALPHABET));
            } else if (isUkrainianAlphabet(ch)) {
                newText.append(codingSymbol(ch, key, UKRAINIAN_ALPHABET));
            }else newText.append(ch);
        }
        return newText.toString();
    }

    public String coding(String text, int key){
        return checkAlphabetAndFormNewText(text, key);
    }

    public String decoding(String text, int key){
        return checkAlphabetAndFormNewText(text, -key);
    }

}
