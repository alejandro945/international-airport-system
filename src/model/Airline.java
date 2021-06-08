package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Airline implements Serializable {
    private static final long serialVersionUID = 1L;
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
        flights = new ArrayList<>();
        this.airlineName = airlineName;
        this.logo = logo;

        Aircraft plane = new Aircraft("planeCode", 234532, 48);
        flights.add(new Flight("1", "2021-06-04", "08:00", "2021-06-06", "11:00", Places.MADRID, Places.BOGOTA, null, this, plane));
        flights.add(
                new Flight("2", "2021-06-08", "09:00", "2021-06-09", "13:00", Places.SYDNEY, Places.MOSCOW, null, this, plane));
        flights.get(0).setFlightStatus(FlightState.AIRBORNE);
        flights.get(1).setFlightStatus(FlightState.AIRBORNE);
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

    public String getLogo() {
        return this.logo;
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

    public List<Luggage> getLuggages() {
        return this.luggages;
    }

    public void setLuggages(List<Luggage> luggages) {
        this.luggages = luggages;
    }

    @Override
    public String toString() {
        return airlineName;
    }

    
}
