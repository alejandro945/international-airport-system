package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trip implements Comparable<Trip>, Price, Serializable {

    private static final long serialVersionUID = 1L;
    private Ticket ticket;
    private Luggage rootLuggage; //Linked List
    private int tripPrice;
    private String id;
    private Seat flightSeat;
    private Trip father;
    private Trip left;
    private Trip right;

    /**
     * The constructor method use for test<br>
     */
    public Trip(String id, Ticket ticket, Luggage rootLuggage, Seat flightSeat) {
        this.id = id;
        this.ticket = ticket;
        this.rootLuggage = rootLuggage;
        this.flightSeat = flightSeat;
        calculatePrice(1);
    }

    /**
     * The constructor method of a Trip Object<br>
     */
    public Trip(String id, Ticket ticket, Seat flightSeat) {
        this.id = id;
        this.ticket = ticket;
        this.flightSeat = flightSeat;
        rootLuggage = null;
        calculatePrice(1);
    }

    /**
     * Calculates luggage price.
     * @return Integer representing luggage price.
     */
    public int luggagePrice() {
        int amount = 0;
        Luggage temp = rootLuggage;
        if (temp != null) {
            do {
                amount += temp.getLuggagePrice();
                temp = temp.getNextLuggage();
            } while (temp != null);
        }
        return amount;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Luggage getRootLuggage() {
        return this.rootLuggage;
    }

    public void setRootLuggage(Luggage rootLuggage) {
        this.rootLuggage = rootLuggage;
    }

    public int getTripPrice() {
        return this.tripPrice;
    }

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

    /**
     * Compares two trips price.
     * @param other Trip to be compared.
     * @return Returns -1 if this price is least than the other one. Returns 1 if this price is greater. Returns 0 if both prices are the same.
     */
    public int compareByPrice(Trip other) {
        return this.tripPrice - other.tripPrice;
    }

    /**
     * Adds luggage to the trip.
     * @param luggage Luggage to be added.
     */
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
            } while (next == null);
        }
        calculatePrice(1);
    }

    /**
     * Calculates trip price.
     * @param increase Price amount to increase.
     */
    @Override
    public void calculatePrice(int increase) {
        tripPrice = ticket.getFlightPrice() + luggagePrice();
    }

    /**
     * Returns string with all seats.
     * @return Returns string with all seats.
     */
    public String seatToString() {
        String letter = String.valueOf(flightSeat.getSeatLetter());
        int num = flightSeat.getSeatNumber();
        String ms = letter + num;
        return ms;
    }

    /**
     * Gets a list of luggage.
     * @return Returns a list containing all luggage.
     */
    public List<Luggage> getLuggages() {
        List<Luggage> tempList = new ArrayList<>();
        Luggage temp = getRootLuggage();
        while (temp != null) {
            tempList.add(temp);
            temp = temp.getNextLuggage();
        }
        return tempList;
    }

    public Seat getFlightSeat() {
        return flightSeat;
    }

    public void setFlightSeat(Seat flightSeat) {
        this.flightSeat = flightSeat;
    }

    /**
     * Compares two trip prices.
     * @param other Trip to be compared with.
     * @return Returns -1 if this price is least than the other one. Returns 1 if this price is greater. Returns 0 if both prices are the same.
     */
    @Override
    public int compareTo(Trip other) {
        return tripPrice - other.getTripPrice();
    }

    /**
     * Compares two trip dates.
     * @param other Trip to be compared with.
     * @return Returns -1 if this date is least than the other one. Returns 1 if this date is greater. Returns 0 if both date are the same.
     */
    public int compareByDate(Trip other) {
        return ticket.getFlight().getDepartureDate().compareTo(other.getTicket().getFlight().getDepartureDate());
    }

}
