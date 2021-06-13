package model;

import java.util.ArrayList;
import java.util.List;

public class Pilot extends Collaborator {

    private List<Flight> flight;

    /**
     * The constructor method of a Pilot Object<br>
     */
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

    /**
     * @param fl Flight to be set.
     */
    public void setFlight(Flight fl) {
        if (flight.isEmpty()) {
            flight.add(fl);
        }
        boolean added = false;
        for (int i = 0; i < flight.size() && !added; i++) {
            if(!flight.get(i).getId().equals(fl.getId())){
                flight.add(fl);
                added = true;
            }
        }
    }

    /**
     * Removes flight from pilot's flights list.
     * @param f Flight to be removed.
     */
    public void removeFlight(Flight f) {
        flight.remove(f);
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return super.getName();
    }

}
