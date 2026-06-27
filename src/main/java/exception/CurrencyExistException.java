package exception;

public class CurrencyExistException extends RuntimeException {
    public CurrencyExistException(String message) {
        super(message);
    }
}
