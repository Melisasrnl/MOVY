import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class Censors {
    private static ArrayList<String> badWords = new ArrayList<>();
    public static void addWords() throws FileNotFoundException{
            String filename = "badwords.txt";
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                
                String word = sc.nextLine().trim();
                badWords.add(word);
            }
            sc.close();
        }
    public static String censor(String message) {
        String[] words = message.split(" ");
        StringBuilder censoredMessage = new StringBuilder();
        for (int i = 0; i< words.length;i++) {
            String word = words[i];
            boolean isBadWord = false;
            for (String badWord :badWords) {
                if(word.equalsIgnoreCase(badWord)) {
                    isBadWord=true;
                    break;
                }
            }
            if(isBadWord) {
                String censoredWord = word.replaceAll("[a-zA-Z]", "*");
                censoredMessage.append(censoredWord);
            }
            else {
                censoredMessage.append(word);
            }
            if (i < words.length - 1) {
                censoredMessage.append(" "); 
            }
        }
        return censoredMessage.toString();
    }
        
}

