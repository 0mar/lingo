package core;

import java.util.Arrays;
/**
 * Object that checks the correctness of a guess per letter.
 * In addition, this method provides new hints for the word.
 * @author omar
 */
class Checker {

    private Word solution;
    private int length;
    
    /**
     * Constructor for checker object
     * @param solution Correct Word.
     */
    Checker(Word solution) {
        
        this.solution = solution;
        this.length = solution.length();
    }

    /**
     * Check whether we succeeded in guessing the word.
     * @param guess Validated user input.
     * @return True if guess equals the solution, false otherwise.
     */
    public boolean wordIsCorrect(Word guess) {
        return guess.equals(solution);
    }

    /**
     * Evaluates the guess by letter-wise comparing it to the solution.
     * This algorithm is key to lingo. This form is not entirely correct.
     * @param guess Validated user input
     * @return Array of Hints, each representing the correctness of a letter.
     * @see Hint
     */
    public Hint[] evaluateWord(Word guess) {
        Hint[] pattern = new Hint[length];
        Hint[] solSide = new Hint[length];
        Arrays.fill(pattern, Hint.WRONG);
        Arrays.fill(solSide, Hint.WRONG);
        char[] gar = guess.toArray();
        char[] sar = solution.toArray();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (gar[i] == sar[j] && i == j) {
                    pattern[i] = pattern[i].upgradeHint(Hint.CORRECT);
                    solSide[j] = solSide[j].upgradeHint(Hint.CORRECT);
                } else if (gar[i] == sar[j] && solSide[j] == Hint.WRONG) {
                    if (gar[j] != sar[j]) {
                        pattern[i] = pattern[i].upgradeHint(Hint.CLOSE);
                        solSide[j] = solSide[j].upgradeHint(Hint.CLOSE);
                    }
                }
            }
        }
        return pattern;
    }

    /**
     * Obtain a new hint for the current solution.
     * The checker returns a Word with one extra correct letter.
     * This means the first (or random, not implemented) letter 
     * that has Hint WRONG or CLOSE.
     * This method is agnostic of the progress of the guessed word
     * and replaces all other wrong letters with '!'. 
     * Therefore, only use it in combination with the corresponding hints
     * 
     * @param curHints The hints we already obtained for the solution
     * @return A word with one extra correct letter.
     */
    public Word getNextHint(Hint[] curHints) {
        char[] compword = new char[length];
        for (int i = 0; i < length; i++) {
            if (curHints[i] == Hint.CORRECT) {
                compword[i] = solution.toArray()[i];
            } else {
                compword[i] = '!';
            }
        }
        for (int i = 0; i < length; i++) {
            if (curHints[i] != Hint.CORRECT) {
                compword[i] = solution.toArray()[i];
                break;
            }
        }
        return new Word(new String(compword));
    }
    
    /**
     * Todo: Refactor.
     * Indicates the next hint. Ugly method.
     * @param curHints
     * @return 
     */

    public static Hint[] addedHint(Hint[] curHints) {
        for (int i = 0; i < curHints.length; i++) {
            if (curHints[i] != Hint.CORRECT) {
                curHints[i] = Hint.CORRECT;
                break;
            }
        }
        return curHints;
    }
}
