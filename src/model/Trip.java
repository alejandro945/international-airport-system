package model;

import java.io.Serializable;

public class Trip implements Serializable {
    private Ticket ticket;
    private Luggage rootLuggage;
    private int tripPrice;
    private String id;

    private Trip father;
    private Trip left;
    private Trip right;

    public Trip(String id, Ticket ticket, Luggage rootLuggage, int tripPrice) {
        this.id = id;
        this.ticket = ticket;
        this.rootLuggage = rootLuggage;
        this.tripPrice = tripPrice;
    }

    /**
     * @return Ticket
     */
    public Ticket getTicket() {
        return this.ticket;
    }

    /**
     * @param ticket
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * @return Luggage
     */
    public Luggage getRootLuggage() {
        return this.rootLuggage;
    }

    /**
     * @param luggage
     */
    public void setRootLuggage(Luggage rootLuggage) {
        this.rootLuggage = rootLuggage;
    }

    /**
     * @return int
     */
    public int getTripPrice() {
        return this.tripPrice;
    }

    /**
     * @param tripPrice
     */
    public void setTripPrice(int tripPrice) {
        this.tripPrice = tripPrice;
    }

    public Trip getFather() {
        return father;
    }

    public void setFather(Trip father) {
        this.father = father;
    }

    public Trip getLeft() {
        return left;
    }

    public void setLeft(Trip left) {
        this.left = left;
    }

    public Trip getRight() {
        return right;
    }

    public void setRight(Trip right) {
        this.right = right;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Compare

    public int compareByPrice(Trip other) {
        return this.tripPrice - other.tripPrice;
    }

    // Add luggage

    public void addLuggage(Luggage luggage) {
        if (rootLuggage == null) {
            setRootLuggage(luggage);
        } else {
            Luggage next = rootLuggage;
            do {
                if (next.getNextLuggage() == null) {
                    next.setNextLuggage(luggage);
                    luggage.setPreviousLuggage(next);
                } else {
                    next = next.getNextLuggage();
                }
            } while (next != null);
        }
    }

}
