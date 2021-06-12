package model;

import java.io.Serializable;

public class Ticket implements Serializable, Price {
    private static final long serialVersionUID = 1L;
    private final int STANDAR_PRICE = 3000;
    private Flight flight;
    private Seat fligthSeat;
    private int flightPrice;

    /**
     * The constructor method of a Ticket Object<br>
     */
    public Ticket(Flight flight, Seat fligthSeat, int flightPrice) {
        this.flight = flight;
        this.fligthSeat = fligthSeat;
        this.flightPrice = flightPrice;
    }

    public Flight getFlight() {
        return this.flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Seat getFligthSeat() {
        return this.fligthSeat;
    }

    public void setFligthSeat(Seat fligthSeat) {
        this.fligthSeat = fligthSeat;
    }

    public int getFlightPrice() {
        return flightPrice;
    }

    public void setFlightPrice(int flightPrice) {
        this.flightPrice = flightPrice;
    }

    /**
     * Calculates ticket price.
     * @param increase Integer containing increase.
     */
    @Override
    public void calculatePrice(int increase) {
        flightPrice = STANDAR_PRICE * (increase / 100) + fligthSeat.getPrice();
    }

}
