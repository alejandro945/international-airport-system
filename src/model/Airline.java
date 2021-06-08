package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Airline implements Serializable, Comparable<Airline> {
    private static final long serialVersionUID = 1L;
    private String airlineName;
    private String logo;
    private List<Aircraft> aircraft;
    private Advisor assistantRoot; // Bynary Tree
    private List<Pilot> pilots;
    private List<User> users;
    private Ticket ticket; // Binary Tree
    private List<Flight> flights;

    public Airline(String airlineName, String logo) {
        flights = new ArrayList<>();
        aircraft = new ArrayList<>();
        this.airlineName = airlineName;
        this.logo = logo;
        Aircraft plane = new Aircraft("planeCode", 234532, 48, this);
        flights.add(new Flight("1", "2021-07-06", "18:45", "2021-08-06", "17:00", Places.BOGOTA, Places.TOKIO, null,
                this, plane));
        flights.add(new Flight("2", "2021-08-06", "09:00", "2021-09-06", "13:00", Places.SYDNEY, Places.MOSCOW, null,
                this, plane));
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

    public String getAirlineName() {
        return this.airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public ImageView getLogo() {
        File file = new File(logo);
        ImageView icon = new ImageView(new Image(("file:///" + file.getAbsolutePath())));
        icon.setFitHeight(50);
        icon.setFitWidth(200);
        return icon;
    }

    public String getIcon() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Aircraft> getAircraft() {
        return this.aircraft;
    }

    public void setAircraft(List<Aircraft> aircraft) {
        this.aircraft = aircraft;
    }

    public List<Pilot> getPilots() {
        return this.pilots;
    }

    public void setPilots(List<Pilot> pilots) {
        this.pilots = pilots;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Flight> getFlights() {
        return this.flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return airlineName;
    }

    /**
     * Makes a list from the advisors binary tree.
     * 
     * @return Returns a list with all the airline advisors.
     */
    public List<Advisor> advisorsToArray() {
        ArrayList<Advisor> advisors = new ArrayList<>();

        // Código para recorrer árbol binario y agregar advisors al arraylist.

        return advisors;
    }

    /**
     * Merges all pilots and advisors into a single list.
     * 
     * @return Returns a list with all the airline employees.
     */
    public List<Collaborator> getEmployees() {
        ArrayList<Collaborator> employees = new ArrayList<>(pilots);
        employees.addAll(advisorsToArray());
        return new ArrayList<>(employees);
    }

    @Override
    public int compareTo(Airline a) {
        if(a.getAirlineName().equalsIgnoreCase(airlineName)){
            return 0;
        }else{
            return airlineName.compareTo(a.getAirlineName());
        }
    }
}
