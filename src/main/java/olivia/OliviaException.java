package olivia;

/**
 * Represents an Olivia exception.
 */
public class OliviaException extends Exception {
    public OliviaException(String message) {
        super("Oh No!!! " + message);
    }
}
