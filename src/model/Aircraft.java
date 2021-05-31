package model;

import java.util.List;

public class Aircraft implements Maintenance {
    private Seat[][] seat;// Bynary Search
    private Pilot pilot;
    private List<Costumer> costumer; // Bynary Search
    private Track track;
    private int planeWeight;
    private String planeCode;
    private int capacity;

    public Aircraft(String planeCode, int planeWeight, int capacity) {
        this.planeCode = planeCode;
        this.planeWeight = planeWeight;
        this.capacity = capacity;
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
     * @param costumer
     */
    public void setCostumer(List<Costumer> costumer) {
        this.costumer = costumer;
    }

    /**
     * @return Track
     */
    public Track getTrack() {
        return this.track;
    }

    /**
     * @param track
     */
    public void setTrack(Track track) {
        this.track = track;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void inMaintenance() {

    }

}
