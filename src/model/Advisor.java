package model;

public class Advisor extends Collaborator {
    private Costumer costumer;

    public Advisor(String name, String lastName, long id, Airline airline, Costumer costumer) {
        super(name, lastName, id, airline);
        this.costumer = costumer;
    }

    /**
     * @return Costumer
     */
    public Costumer getCostumer() {
        return this.costumer;
    }

    /**
     * @param costumer
     */
    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }

}
