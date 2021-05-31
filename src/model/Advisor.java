package model;

import java.util.ArrayList;
import java.util.List;

public class Advisor extends Collaborator {
    private List<Costumer> costumers;

    public Advisor(String name, String lastName, long id, Airline airline) {
        super(name, lastName, id, airline);
        costumers = new ArrayList<>();
    }

    /**
     * @return List<Costumer>
     */
    public List<Costumer> getCostumers() {
        return this.costumers;
    }

    /**
     * @param costumers
     */
    public void setCostumers(List<Costumer> costumers) {
        this.costumers = costumers;
    }

}
