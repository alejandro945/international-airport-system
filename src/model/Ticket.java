package model;

public class Ticket implements Price {
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
        return this.flightPrice;
    }

    /**
     * @param flightPrice
     */
    public void setFlightPrice(int flightPrice) {
        this.flightPrice = flightPrice;
    }

    @Override
    public void calculatePrice(int increase) {

    }

}
