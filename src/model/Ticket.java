package model;

import java.io.Serializable;

public class Ticket implements Serializable, Price {
    private static final long serialVersionUID = 1L;
    private final int STANDAR_PRICE = 3000;
    private Flight flight;
    private Seat fligthSeat;
    private int flightPrice;

    public Ticket(Flight flight, Seat fligthSeat, int flightPrice) {
        this.flight = flight;
        this.fligthSeat = fligthSeat;
        this.flightPrice = flightPrice;
    }

    /**
     * @return Flight
     */
    public Flight getFlight() {
        return this.flight;
    }

    /**
     * @param flight
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * @return Seat
     */
    public Seat getFligthSeat() {
        return this.fligthSeat;
    }

    /**
     * @param fligthSeat
     */
    public void setFligthSeat(Seat fligthSeat) {
        this.fligthSeat = fligthSeat;
    }

    /**
     * @return int
     */
    public int getFlightPrice() {
        return flightPrice;
    }

    /**
     * @param flightPrice
     */
    public void setFlightPrice(int flightPrice) {
        this.flightPrice = flightPrice;
    }

    @Override
    public void calculatePrice(int increase) {
        flightPrice = STANDAR_PRICE * (increase / 100) + fligthSeat.getPrice();
    }

}
