package olivia;

/**
 * Represents an Olivia exception.
 */
public class OliviaException extends RuntimeException {
    public OliviaException(String message) {
        super("Oh No!!! " + message);
    }
}
