package exeption;

public class WantedException extends Exception {
    private String message;

    public WantedException() {
        message = ("You have been arrested and charges will be filed in court");
    }

    public String getMessage() {
        return message;
    }
}
