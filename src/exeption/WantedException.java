package exeption;

public class WantedException extends Exception {
    private String message;

    /**
     * The constructor method of WantedException<br>
     */
    public WantedException() {
        message = ("You have been arrested and charges will be filed in court");
    }

    /**
     * Gets the pertinent exception message (In this case, costumer with judicial
     * background )
     * 
     * @return String
     */
    public String getMessage() {
        return message;
    }
}
