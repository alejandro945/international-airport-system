package model;

import java.util.List;

public class Migration {
    private List<Costumer> people;

    public Migration(List<Costumer> people) {
        this.people = people;
    }

    public List<Costumer> getPeople() {
        return this.people;
    }

    public void setPeople(List<Costumer> people) {
        this.people = people;
    }

}
