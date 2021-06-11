package model;

public class AirlineUser extends User {

    private Airline airline;

    public AirlineUser(String name, String lastName, long id, String email, String password, Airline airline) {
        super(name, lastName, id, email, password, UserRole.AIRLINE_ADMIN);
        this.airline = airline;
    }

    public Airline getAirline() {
        return this.airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

}
