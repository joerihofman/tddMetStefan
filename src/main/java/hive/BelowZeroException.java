package hive;

public class BelowZeroException extends Exception {
    public BelowZeroException() { super(); }
    public BelowZeroException(String message) { super(message); }
    public BelowZeroException(String message, Throwable cause) { super(message, cause); }
    public BelowZeroException(Throwable cause) { super(cause); }
}
