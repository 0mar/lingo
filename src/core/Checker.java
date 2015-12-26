package core;

import java.util.Arrays;

/**
 * Object that checks the correctness of a guess per letter. In addition, this
 * method provides new hints for the word.
 *
 * @author omar
 */
class Checker {

    private Word solution;
    private int length;

    /**
     * Constructor for checker object
     *
     * @param solution Correct Word.
     */
    Checker(Word solution) {

        this.solution = solution;
        this.length = solution.length();
    }

    /**
     * Check if the guess equals the solution.
     *
     * @param guess Validated user input.
     * @return True if guess equals the solution, false otherwise.
     */
    public boolean wordIsCorrect(Word guess) {
        return guess.equals(solution);
    }

    /**
     * Evaluates the guess by letter-wise comparing it to the solution. This
     * algorithm is key to lingo. First checks all correct letters. Then checks
     * if other letters in guess are present in the word Any letter in guess can
     * only represent one letter in solution
     *
     * @param guess Validated user input
     * @return Array of Hints, each representing the correctness of a letter.
     * @see Hint
     */
    public Hint[] evaluateWord(Word guess) {
        Hint[] pattern = new Hint[length];
        Arrays.fill(pattern, Hint.WRONG);
        char[] gar = guess.toArray();
        char[] sar = solution.toArray();
        for (int i = 0; i < length; i++) {
            if (gar[i] == sar[i]) {
                pattern[i] = Hint.CORRECT;
                sar[i] = '!'; //Placeholder char
            }
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (gar[i] == sar[j] && pattern[i] != Hint.CORRECT) {
                    pattern[i] = Hint.CLOSE;
                    sar[j] = '!';
                }
            }
        }
        return pattern;
    }

    /**
     * Obtain a new hint for the current solution. The checker returns a Word
     * with one extra correct letter. This means the first (or random, not
     * implemented) letter that has Hint WRONG or CLOSE.
     *
     * @param curHints The hints we already obtained for the solution
     * @return Location in word of new hint
     */
    public int getNextHint(Hint[] curHints, boolean random) {
        int char_location = -1;
        // Even if not random, we need to see if new hints can be given
        for (int i = 0; i < length; i++) {
            if (curHints[i] != Hint.CORRECT) {
                char_location = i;
                break;
            }
        }
        if (char_location == -1) {
            System.out.println("Error: No new hints available");
            return char_location;
        }
        if (!random) {
            return char_location;
        }

        char_location = -1;
        while (char_location < 0) {
            char_location = (int) (Math.random() * length);
            if (curHints[char_location] == Hint.CORRECT) {
                char_location = -1;
            }
        }
        return char_location;
    }
}
