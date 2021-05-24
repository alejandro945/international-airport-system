package model;

public abstract class Ticket {
    private Flight flight;
    private Seat fligthSeat;
    private Luggage luggage;

    public Ticket(Flight flight, Seat fligthSeat, Luggage luggage) {
        this.flight = flight;
        this.fligthSeat = fligthSeat;
        this.luggage = luggage;
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
     * @return Luggage
     */
    public Luggage getLuggage() {
        return this.luggage;
    }

    /**
     * @param luggage
     */
    public void setLuggage(Luggage luggage) {
        this.luggage = luggage;
    }

}
