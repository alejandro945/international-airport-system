package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.crud.LugaggeController;

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
        int value = (int) Math.random()*3+1;
        state = CostumerState.values()[value].name();
    }

    public Costumer(String name, String lastName, long id, String iconPath) {
        super(name, lastName, id, "", "", UserRole.COSTUMER_USER);
        this.iconPath = iconPath;
        rootTrip = null;
        int value = (int) Math.random()*3+1;
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
    public String deleteTrip(Trip trip){
        Trip temp = getTrip(trip);

        Trip parent = temp.getFather() ;
        Trip right = temp.getRight();
        Trip left = temp.getLeft();

        // no children case
        if(right == null && left == null){
            if(parent.getLeft().equals(temp)){
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        } 

        // One children case
        if(right == null && left != null ){
            if(parent.getLeft().equals(temp)){
                parent.setLeft(left);
            } else {
                parent.setRight(left);
            }
        }

        if(right != null && left == null ){
            if(parent.getLeft().equals(temp)){
                parent.setLeft(right);
            } else {
                parent.setRight(right);
            }
        }

        // Two children case
        if(right != null && left != null ){
            if(parent.getLeft().equals(temp)){
                parent.setLeft(right);
            } else {
                parent.setRight(right);
            }
            addTrip(right, left);
        }

        temp.setFather(null);
        temp.setLeft(null);
        temp.setRight(null);

        return temp.getId();
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

    private int countTrips(Trip node, int count){
        if (node == null) {
            return 0;
        }
        countTrips(node.getLeft(), count);
        count++;
        countTrips(node.getRight(), count);
        return count;
    }

    public int countTrips(){
       return countTrips(rootTrip, 0);
    }

    
}
