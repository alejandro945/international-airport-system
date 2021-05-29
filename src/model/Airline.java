package model;

import java.util.List;

public class Airline {
    private String airlineName;
    private List<Aircraft> aircraft;
    private Advisor assistantRoot; // Bynary Tree
    private List<User> users;
    private Ticket ticket; // Binary Tree
    private List<Flight> flights;

    public Airline(String airlineName) {
        this.airlineName = airlineName;
    }

    /**
     * @return Assistant
     */
    public Advisor getAssistantRoot() {
        return this.assistantRoot;
    }

    /**
     * @param assistantRoot
     */
    public void setAssistantRoot(Advisor assistantRoot) {
        this.assistantRoot = assistantRoot;
    }

    @Override
    public String toString() {
        return airlineName;
    }
}
