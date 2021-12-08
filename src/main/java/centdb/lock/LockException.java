package centdb.lock;

public class LockException extends Exception{

    private final String errorMessage;

    public LockException(final String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
