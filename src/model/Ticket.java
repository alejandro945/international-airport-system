package model;

import java.io.Serializable;

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    private Flight flight;
    private Seat fligthSeat;
    private int flightPrice;

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
        return this.flightPrice;
    }

    public void setFlightPrice(int flightPrice) {
        this.flightPrice = flightPrice;
    }    

}
