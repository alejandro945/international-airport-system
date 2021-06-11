package exeption;

public class MinorException extends Exception {
    private String message;

    /**
     * The constructor method of a MinorException<br>
     */
    public MinorException() {
        message = ("Sorry, you are minor and you do not have the relevant permission for travel");
    }

    /**
     * Gets the pertinent exception message (In this case, costumer without parents
     * approvement [Minor])
     * 
     * @return String
     */
    public String getMessage() {
        return message;
    }
}
