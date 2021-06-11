package model;

import java.util.ArrayList;
import java.util.List;

public class Pilot extends Collaborator {
    private List<Flight> flight;

    public Pilot(String name, String lastName, long id, Airline airline) {
        super(name, lastName, id, airline, "Pilot");
        flight = new ArrayList<>();
    }
    public int getReport() {
        return flight.size();
    }
    /**
     * @return List<Flight>
     */
    public List<Flight> getFlight() {
        return this.flight;
    }

    /**
     * @param flight
     */
    public void setFlight(Flight flight) {
        this.flight.add(flight);
    }

    @Override
    public String toString() {
        return super.getName();
    }
    

}
