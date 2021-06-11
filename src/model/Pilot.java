package model;

import java.util.ArrayList;
import java.util.List;

public class Pilot extends Collaborator {
    private List<Flight> flight;

    public Pilot(String name, String lastName, long id, Airline airline) {
        super(name, lastName, id, airline, "Pilot");
        flight = new ArrayList<>();
    }

    @Override
    public int getReport() {
        return flight.size();
    }

    public List<Flight> getFlight() {
        return this.flight;
    }

    public void setFlight(Flight flight) {
        this.flight.add(flight);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return super.getName();
    }

}
