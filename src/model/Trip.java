package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trip implements Comparable<Trip>, Price, Serializable {

    private static final long serialVersionUID = 1L;
    private Ticket ticket;
    private Luggage rootLuggage;
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
     *
     * @return
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
     *
     * @param other
     * @return
     */
    public int compareByPrice(Trip other) {
        return this.tripPrice - other.tripPrice;
    }

    /**
     *
     * @param luggage
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
     *
     * @param increase
     */
    @Override
    public void calculatePrice(int increase) {
        tripPrice = ticket.getFlightPrice() + luggagePrice();
    }

    /**
     *
     * @return
     */
    public String seatToString() {
        String letter = String.valueOf(flightSeat.getSeatLetter());
        int num = flightSeat.getSeatNumber();
        String ms = letter + num;
        return ms;
    }

    /**
     *
     * @return
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
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(Trip other) {
        return tripPrice - other.getTripPrice();
    }

    /**
     *
     * @param other
     * @return
     */
    public int compareByDate(Trip other) {
        return ticket.getFlight().getDepartureDate().compareTo(other.getTicket().getFlight().getDepartureDate());
    }

}
