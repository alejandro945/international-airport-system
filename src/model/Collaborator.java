package model;

public abstract class Collaborator extends Person {
    private Airline airline;

    public Collaborator(String name, String lastName, long id, Airline airline) {
        super(name, lastName, id);
        this.airline = airline;
    }

    public Airline getAirline() {
        return this.airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

}
