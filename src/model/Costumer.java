package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Costumer extends User {
    Random r = new Random();
    private String iconPath;
    private Trip rootTrip;
    private String state;

    public Costumer() { // JUnit Tests
        super("Tester", "User", 4, "testerUser@correo.co", "4", UserRole.COSTUMER_USER);

    }

    public Costumer(String name, String lastName, long id, String email, String password) {
        super(name, lastName, id, email, password, UserRole.COSTUMER_USER);
        rootTrip = null;
        int value = (int) Math.random() * 3 + 1;
        state = CostumerState.values()[value].name();
    }

    public Costumer(String name, String lastName, long id, String iconPath) {
        super(name, lastName, id, "", "", UserRole.COSTUMER_USER);
        this.iconPath = iconPath;
        rootTrip = null;
        int value = (int) Math.random() * 3 + 1;
        state = CostumerState.values()[value].name();
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Random getR() {
        return this.r;
    }

    public void setR(Random r) {
        this.r = r;
    }

    public Trip getRootTrip() {
        return rootTrip;
    }

    public void setRootTrip(Trip rootTrip) {
        this.rootTrip = rootTrip;
    }

    // -------------------- Methods

    // Add trip

    public void addTrip(Trip newPNode) {

        if (rootTrip == null) {
            rootTrip = newPNode;
        } else {
            addTrip(rootTrip, newPNode);
        }

    }

    private void addTrip(Trip parent, Trip newTrip) {
        if (newTrip.getTripPrice() <= parent.getTripPrice()) {
            if (parent.getLeft() == null) {
                parent.setLeft(newTrip);
            } else {
                addTrip(parent.getLeft(), newTrip);
            }
        } else {
            if (parent.getRight() == null) {
                parent.setRight(newTrip);
            } else {
                addTrip(parent.getRight(), newTrip);
            }
        }
    }

    // Print trips
    public List<Trip> getTrips() {
        List<Trip> list = new ArrayList<>();
        rangeTripsAdd(rootTrip, list);
        return list;
    }

    private void rangeTripsAdd(Trip node, List<Trip> list) {
        if (node == null) {
            return;
        }
        rangeTripsAdd(node.getLeft(), list);
        list.add(node);
        rangeTripsAdd(node.getRight(), list);
    }

    // Return trip by object
    public Trip getTrip(Trip toFind) {
        Trip temp = null;
        rangeTripSearch(rootTrip, toFind, temp);
        return temp;
    }

    private void rangeTripSearch(Trip node, Trip toFind, Trip returnV) {
        if (node == null) {
            return;
        }
        rangeTripSearch(node.getLeft(), toFind, returnV);
        if (node.equals(toFind)) {
            returnV = node;
        }
        rangeTripSearch(node.getRight(), toFind, returnV);
    }

    // Delete trip
    public String deleteTrip(Trip trip) {
        Trip temp = trip;

        //System.out.println(trip);

        if (temp != null) {
            Trip parent = temp.getFather() == null ? null : temp.getFather();
            Trip right = temp.getRight() == null ? null : temp.getRight();
            Trip left = temp.getLeft() == null ? null : temp.getLeft();
            
            if (right == null && left == null) {    // no children case
                if (parent == null) {
                    rootTrip = null;
                } else {
                    if (parent.getLeft() == (temp)) {
                        parent.setLeft(null);
                    } else {
                        parent.setRight(null);
                    }
                }
            } else if (right == null || left != null) { // One children case

                Trip tempSon = null;

                if (right == null) {
                    tempSon = left;                  
                } else {
                    tempSon = right;
                }
                tempSon.setFather(parent);

                if (parent == null) {
                    rootTrip = tempSon;
                } else {
                    if (parent.getLeft() == (temp)) {
                        parent.setLeft(tempSon);
                    } else {
                        parent.setRight(tempSon);
                    }
                }
            } else { // Two children case
                Trip minTrip = minTrip(right);

                deleteTrip(minTrip);

                temp.setRootLuggage(minTrip.getRootLuggage());
                temp.setTicket(minTrip.getTicket());
                temp.setTripPrice(minTrip.getTripPrice());
                temp.setId(minTrip.getId());
                temp.setFlightSeat(minTrip.getFlightSeat());                
            }

            temp.getFlightSeat().setSeatState(false);
            temp.setFather(null);
            temp.setLeft(null);
            temp.setRight(null);

            return temp.getId();

        } else {
            return "No se pudo eliminar";
        }

    }

    private Trip minTrip (Trip starTrip){
        if(starTrip.getLeft() == null ){
            return starTrip;
        }else{
            return minTrip(starTrip.getLeft());
        }
    }

    // Add luggage
    public void addLuggage(Luggage luggage, Trip trip) {
        trip.addLuggage(luggage);
    }

    public String getState() {
        return state;
    }

    public void setState(int state) {
        this.state = CostumerState.values()[state].name();
    }

    private int countTrips(Trip node, int count) {
        if (node == null) {
            return 0;
        }
        countTrips(node.getLeft(), count);
        count++;
        countTrips(node.getRight(), count);
        return count;
    }

    public int countTrips() {
        return countTrips(rootTrip, 0);
    }

    // Update Trip

}
