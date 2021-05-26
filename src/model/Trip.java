package model;

public class Trip {
    private Ticket ticket;
    private Luggage luggage;
    private int tripPrice;

    public Trip(Ticket ticket, Luggage luggage, int tripPrice) {
        this.ticket = ticket;
        this.luggage = luggage;
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
    public Luggage getLuggage() {
        return this.luggage;
    }

    /**
     * @param luggage
     */
    public void setLuggage(Luggage luggage) {
        this.luggage = luggage;
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

}
