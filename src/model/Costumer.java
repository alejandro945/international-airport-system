package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Costumer extends User {
    Random r = new Random();
    private String iconPath;
    private List<Trip> trip;
    private boolean wanted;
    private boolean pass;
    private boolean minor;

    public Costumer() { // JUnit Tests
        super("Tester", "User", 122442, "testerUser@correo.co", "password", UserRole.COSTUMER_USER);
        trip = new ArrayList<>();
        trip.add(new Trip(
                new Ticket(new Flight("Junio 4 2021", "8:00", "Junio 4 2021", "11:00", Places.BARCELONA, Places.MIAMI),
                        new Seat(4, 'A', true), 20000),
                new Luggage(20, 30, 70), 500000));
        trip.add(new Trip(
                new Ticket(new Flight("Junio 4 2021", "8:00", "Junio 4 2021", "11:00", Places.BOGOTA, Places.MILAN),
                        new Seat(4, 'A', true), 20000),
                new Luggage(20, 30, 70), 500000));
    }

    public Costumer(String name, String lastName, long id, String email, String password) {
        super(name, lastName, id, email, password, UserRole.COSTUMER_USER);
        trip = new ArrayList<>();
        wanted = r.nextBoolean();
        pass = r.nextBoolean();
        minor = r.nextBoolean();
    }

    public Costumer(String name, String lastName, long id, String iconPath) {
        super(name, lastName, id, "", "", UserRole.COSTUMER_USER);
        this.iconPath = iconPath;
        trip = new ArrayList<>();
        wanted = r.nextBoolean();
        pass = r.nextBoolean();
        minor = r.nextBoolean();
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public List<Trip> getTrip() {
        return this.trip;
    }

    public void setTrip(List<Trip> trip) {
        this.trip = trip;
    }

    public Random getR() {
        return this.r;
    }

    public void setR(Random r) {
        this.r = r;
    }

    public boolean isWanted() {
        return this.wanted;
    }

    public boolean getWanted() {
        return this.wanted;
    }

    public void setWanted(boolean wanted) {
        this.wanted = wanted;
    }

    public boolean isPass() {
        return this.pass;
    }

    public boolean getPass() {
        return this.pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public boolean isMinor() {
        return this.minor;
    }

    public boolean getMinor() {
        return this.minor;
    }

    public void setMinor(boolean minor) {
        this.minor = minor;
    }

}
