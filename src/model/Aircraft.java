package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Aircraft implements Maintenance, Serializable {
    private static final long serialVersionUID = 1L;
    private final int AIRCRAFT_COLUMNS = 6;
    private Seat[][] seat;// Bynary Search
    private Airline airline;
    private Pilot pilot;
    private List<Costumer> costumer; // Bynary Search
    private int planeWeight;
    private String planeCode;
    private int capacity;
    private boolean inMaintenance;

    public Aircraft(String planeCode, int planeWeight, int capacity,Airline airline) {
        this.planeCode = planeCode;
        this.airline = airline;
        this.planeWeight = planeWeight;
        this.capacity = capacity;
        inMaintenance = false;
        createSeats();
        costumer = new ArrayList<>();
        costumer.add(new Costumer());
        costumer.add(new Costumer());
        costumer.add(new Costumer());
        costumer.add(new Costumer());
        costumer.add(new Costumer());
        costumer.add(new Costumer());
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return planeCode;
    }

    public int getPlaneWeight() {
        return this.planeWeight;
    }

    public void setPlaneWeight(int planeWeight) {
        this.planeWeight = planeWeight;
    }

    public Airline getAirline() {
        return this.airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public String getPlaneCode() {
        return this.planeCode;
    }

    public void setPlaneCode(String planeCode) {
        this.planeCode = planeCode;
    }

    /**
     * @return Seat[][]
     */
    public Seat[][] getSeat() {
        return this.seat;
    }

    /**
     * @param seat
     */
    public void setSeat(Seat[][] seat) {
        this.seat = seat;
    }

    /**
     * @return Pilot
     */
    public Pilot getPilot() {
        return this.pilot;
    }

    /**
     * @param pilot
     */
    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    /**
     * @return ArrayList<Costumer>
     */
    public List<Costumer> getCostumer() {
        return this.costumer;
    }

    /**
     * @return boolean
     */
    public boolean isInMaintenance() {
        return this.inMaintenance;
    }

    /**
     * @param inMaintenance
     */
    public void setInMaintenance(boolean inMaintenance) {
        this.inMaintenance = inMaintenance;
    }

    /**
     * @param costumer
     */
    public void setCostumer(List<Costumer> costumer) {
        this.costumer = costumer;
    }

    /**
     * @return int
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * @param capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void createSeats() {
        int rows = capacity / AIRCRAFT_COLUMNS;
        seat = new Seat[rows][AIRCRAFT_COLUMNS];
        for (int i = 0; i < rows; i++) {
            char firstLetter = 'A';
            for (int j = 0; j < AIRCRAFT_COLUMNS; j++) {
                if (i < 6) {
                    seat[i][j] = new Seat((i + 1), firstLetter, false, 140000);
                } else {
                    seat[i][j] = new Seat((i + 1), firstLetter, false , 140000);
                }
                firstLetter++;
            }
        }
    }

    @Override
    public void inMaintenance() {
        inMaintenance = true;
    }

}
