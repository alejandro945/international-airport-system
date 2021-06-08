package exeption;

public class MinorException extends Exception {
    private String message;

    public MinorException() {
        message = ("Sorry, you are minor and you do not have the relevant permission for travel");
    }

    public String getMessage() {
        return message;
    }
}
