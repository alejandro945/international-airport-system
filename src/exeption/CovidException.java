package exeption;

public class CovidException extends Exception {
    private String message;

    /**
     * The constructor method of a CovidException<br>
     */
    public CovidException() {
        message = "Your safety is the most important thing, you cannot travel because you do not have the covid requirements";
    }

    /**
     * Gets the pertinent exception message (In this case, costumer without covid
     * requirements)
     * 
     * @return String
     */
    public String getMessage() {
        return message;
    }
}
