package model;

public class Standard extends Ticket {
    private int flightPrice;

    public Standard(Flight flight, Seat fligthSeat, Luggage luggage, int flightPrice) {
        super(flight, fligthSeat, luggage);
        this.flightPrice = flightPrice;
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
}
