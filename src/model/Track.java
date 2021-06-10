package model;

import java.io.Serializable;

public class Track implements Maintenance, Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private boolean inMaintenance;
    private String gate;
    private String state;

    private Track next;
    private Track prev;

    public Track(int id, String gate) {
        this.id = id;
        this.gate = gate;
        inMaintenance = false;
    }

    /**
     * @return int
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        state = (inMaintenance) ? "INACTIVE" : "ACTIVE";
        return state;
    }

    /**
     * @return boolean
     */
    public boolean isInMaintenance() {
        return this.inMaintenance;
    }

    /**
     * @param state
     */
    public void setInMaintenance(boolean inMaintenance) {
        this.inMaintenance = inMaintenance;
    }

    /**
     * @return String
     */
    public String getGate() {
        return this.gate;
    }

    /**
     * @param gate
     */
    public void setGate(String gate) {
        this.gate = gate;
    }

    @Override
    public void inMaintenance() {
        inMaintenance = true;
    }

    /**
     * @return Track
     */
    public Track getNext() {
        return next;
    }

    /**
     * @param next
     */
    public void setNext(Track next) {
        this.next = next;
    }

    /**
     * @return Track
     */
    public Track getPrev() {
        return prev;
    }

    /**
     * @param prev
     */
    public void setPrev(Track prev) {
        this.prev = prev;
    }

    public String toString() {
        return gate;
    }
}
