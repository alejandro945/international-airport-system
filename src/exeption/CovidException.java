package exeption;

public class CovidException extends Exception {
    private String message;

    public CovidException() {
        message = "Your safety is the most important thing, you cannot travel because you do not have the covid requirements";
    }

    public String getMessage() {
        return message;
    }
}
