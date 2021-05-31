package model;

import java.util.List;

public class Airline {
    private String airlineName;
    private String logo;
    private List<Aircraft> aircraft;
    private Advisor assistantRoot; // Bynary Tree
    private List<Pilot> pilots;
    private List<User> users;
    private Ticket ticket; // Binary Tree
    private List<Flight> flights;
    private List<Luggage> luggages;

    public Airline(String airlineName, String logo) {
        this.airlineName = airlineName;
        this.logo = logo;
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
