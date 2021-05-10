package model;

public class Premium extends Ticket {
    private int premiumPrice;

    public Premium(Flight flight, Seat fligthSeat, int premiumPrice) {
        super(flight, fligthSeat);
        this.premiumPrice = premiumPrice;
    }

    /**
     * @return int
     */
    public int getPremiumPrice() {
        return this.premiumPrice;
    }

    /**
     * @param premiumPrice
     */
    public void setPremiumPrice(int premiumPrice) {
        this.premiumPrice = premiumPrice;
    }

}
