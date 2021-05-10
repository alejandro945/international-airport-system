package model;

import java.util.List;

public class Aircraft {
    private Seat[][] seat;// Bynary Search
    private Pilot pilot;
    private Assistant assistantRoot; // Bynary Tree
    private List<Costumer> costumer; // Bynary Search
    private Track track;
    private int planeSize;
    private int planeWeight;
    private String planeCode;

    public int getPlaneWeight() {
        return this.planeWeight;
    }

    public void setPlaneWeight(int planeWeight) {
        this.planeWeight = planeWeight;
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
     * @return Assistant
     */
    public Assistant getAssistantRoot() {
        return this.assistantRoot;
    }

    /**
     * @param assistantRoot
     */
    public void setAssistantRoot(Assistant assistantRoot) {
        this.assistantRoot = assistantRoot;
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

    /**
     * @return int
     */
    public int getPlaneSize() {
        return this.planeSize;
    }

    /**
     * @param planeSize
     */
    public void setPlaneSize(int planeSize) {
        this.planeSize = planeSize;
    }

}
