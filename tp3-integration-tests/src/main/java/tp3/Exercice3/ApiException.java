
package tp3.Exercice3;

public class ApiException extends Exception {
    public ApiException(String message) {
        super(message);
    }
    
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}