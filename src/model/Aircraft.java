package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Aircraft implements Maintenance, Serializable {
    private static final long serialVersionUID = 1L;
    private final int AIRCRAFT_COLUMNS = 6;
    private Seat[][] seat;// Bynary Search
    private Pilot pilot;
    private List<Costumer> costumer; // Bynary Search
    private int planeWeight;
    private String planeCode;
    private int capacity;
    private boolean inMaintenance;

    public Aircraft(String planeCode, int planeWeight, int capacity) {
        this.planeCode = planeCode;
        this.planeWeight = planeWeight;
        this.capacity = capacity;
        inMaintenance = false;
        createSeats();
        costumer = new ArrayList<>();
    }

    /**
     * @return int
     */
    public int getPlaneWeight() {
        return this.planeWeight;
    }

    /**
     * @param planeWeight
     */
    public void setPlaneWeight(int planeWeight) {
        this.planeWeight = planeWeight;
    }

    /**
     * @return String
     */
    public String getPlaneCode() {
        return this.planeCode;
    }

    /**
     * @param planeCode
     */
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
