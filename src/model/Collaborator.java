package model;

public abstract class Collaborator extends Person {
    private Airline airline;
    private String type;

    public Collaborator(String name, String lastName, long id, Airline airline, String type) {
        super(name, lastName, id);
        this.airline = airline;
        this.type = type;
    }

    public Airline getAirline() {
        return this.airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract String toString();
}
