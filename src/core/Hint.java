package core;

/**
 * Extended enum to provide information about the correctness of letter in a
 * guess. If a letter is WRONG, it is not present in the solution If a letter is
 * CLOSE, the solution contains the letter in another position If a letter is
 * CORRECT, the solution contains the letter in that position.
 *
 * @author omar
 */
public enum Hint {

    WRONG, // = 0
    CLOSE, // = 1
    CORRECT;//= 2//= 2//= 2//= 2

}
