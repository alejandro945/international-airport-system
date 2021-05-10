package model;

public abstract class Ticket {
    private Flight flight;
    private Seat fligthSeat;

    public Ticket(Flight flight, Seat fligthSeat) {
        this.flight = flight;
        this.fligthSeat = fligthSeat;
    }

    public Flight getFlight() {
        return this.flight;
    }

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

}
