package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Migration implements Supplier, Serializable {
    private List<Costumer> people;

    public Migration() {
        people = new ArrayList<>();
    }

    public List<Costumer> getPeople() {
        return this.people;
    }

    public void setPeople(List<Costumer> people) {
        this.people = people;
    }

    @Override
    public void airportCharges(int investment) {

    }

}
