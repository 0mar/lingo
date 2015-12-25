package input;

import java.util.*;
import java.io.*;

/**
 * Class for creating word lists and reading them Any word list is stored with a
 * .dic extension.
 *
 * @author omar
 */
public class WordPopper {

    ArrayList<String> lines;
    String masterdic = "pickdic.txt";
    String subdic = "dic-size";

    public static Comparator<String> COMPARE_BY_SIZE = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.length() - o2.length();
        }
    };

    /**
     * Returns a stack of words with given length
     *
     * @param n length of word
     */
    public WordPopper(int n) {
        BufferedReader br = null;
        lines = new ArrayList();
        try {
            String curline;
            br = new BufferedReader(new FileReader("dic-size" + n + ".dic"));
            while ((curline = br.readLine()) != null) {
                lines.add(curline);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No dictionary found in " + "dic-size" + n + ".dic");
            System.out.println("Dictionary is being created...");
            createNewDictionary(n);
        } catch (IOException ex) {
            System.out.println("Couldn't open dictionary in " + "dic-size" + n + ".dic");
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Reads a dictionary and create a word list with given length
     * @param n word length
     * @param t Unused
     */
    public WordPopper(int n, boolean t) {
        createNewDictionary(n);
    }

    /**
     * Get a random new word from the word list
     * @return new word with appropriate length
     */
    public String getNextLingoWord() {
        return lines.remove((int) (Math.random() * lines.size()));
    }

    /**
     * Create and store a new list of words from a large dictionary
     * @param n required length of words.
     */
    private void createNewDictionary(int n) {
        BufferedReader br = null;
        lines = new ArrayList();
        try {
            String curline;
            br = new BufferedReader(new FileReader(masterdic));
            while ((curline = br.readLine()) != null) {
                lines.add(curline);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No dictionary found in " + masterdic + ". Check your files");
        } catch (IOException ex) {
            System.out.println("Couldn't open dictionary in " + masterdic);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
            }
        }
        System.out.println(lines.size());
        Collections.sort(lines, COMPARE_BY_SIZE);
        try {
            PrintWriter pw = new PrintWriter(subdic + n + ".dic", "UTF-8");
            for (String s : lines) {
                if (s.length() == n) {
                    pw.println(s);
                    System.out.println(s);
                }
                pw.flush();
            }
        } catch (Exception ex) {
            System.out.println("printing failed");
        } finally {
            System.out.println("Dictionary creation complete. Restart program");
            System.exit(0);
        }

    }

}
