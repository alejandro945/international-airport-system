package model;

public class Trip {
    private Ticket ticket;
    private Luggage rooLuggage;
    private int tripPrice;
    private String id;

    private Trip father;
    private Trip left;
    private Trip right;

    public Trip(String id, Ticket ticket, Luggage rooLuggage, int tripPrice) {
        this.id = id;
        this.ticket = ticket;
        this.rooLuggage = rooLuggage;
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
    public Luggage getRootLuggage() {
        return this.rooLuggage;
    }

    /**
     * @param luggage
     */
    public void setRootLuggage(Luggage rooLuggage) {
        this.rooLuggage = rooLuggage;
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

    public Luggage getRooLuggage() {
        return rooLuggage;
    }

    public void setRooLuggage(Luggage rooLuggage) {
        this.rooLuggage = rooLuggage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Compare

    public int compareByPrice(Trip other) {
        return this.tripPrice - other.tripPrice;
    }

    // Add luggage

    public void addLuggage(Luggage luggage) {
        if (rooLuggage == null) {
            setRootLuggage(luggage);
        } else {
            Luggage next = rooLuggage;
            do{
                if(next.getNextLuggage() == null){
                    next.setNextLuggage(luggage);
                    luggage.setPreviousLuggage(next);
                } else {
                    next = next.getNextLuggage();
                }
            } while(next != null);
        }
    }

    

    
}
