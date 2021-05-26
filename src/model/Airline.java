package model;

import java.util.List;

public class Airline {
    private String airlineName;
    private List<Aircraft> aircraft;
    private Advisor assistantRoot; // Bynary Tree
    private List<User> users;
    private Ticket ticket; // Binary Tree

    public String createUser(String name, String lastName, long id, String email, String password) {
        String msg = "";
        Costumer newCostumer = new Costumer(name, lastName, id, email, password);
        users.add(newCostumer);
        msg = name + Airport.USER_SUCCESS;
        return msg;
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
}
