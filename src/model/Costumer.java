package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Costumer extends User {
    Random r = new Random();
    private String iconPath;
    private Trip rootTrip;
    private boolean wanted;
    private boolean pass;
    private boolean minor;

    public Costumer() { // JUnit Tests
        super("Tester", "User", 122442, "testerUser@correo.co", "password", UserRole.COSTUMER_USER);
    }

    public Costumer(String name, String lastName, long id, String email, String password) {
        super(name, lastName, id, email, password, UserRole.COSTUMER_USER);
        rootTrip = null;
        wanted = r.nextBoolean();
        pass = r.nextBoolean();
        minor = r.nextBoolean();
    }

    public Costumer(String name, String lastName, long id, String iconPath) {
        super(name, lastName, id, "", "", UserRole.COSTUMER_USER);
        this.iconPath = iconPath;
        rootTrip = null;
        wanted = r.nextBoolean();
        pass = r.nextBoolean();
        minor = r.nextBoolean();
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

    public boolean isWanted() {
        return this.wanted;
    }

    public boolean getWanted() {
        return this.wanted;
    }

    public void setWanted(boolean wanted) {
        this.wanted = wanted;
    }

    public boolean isPass() {
        return this.pass;
    }

    public boolean getPass() {
        return this.pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public boolean isMinor() {
        return this.minor;
    }

    public boolean getMinor() {
        return this.minor;
    }

    public void setMinor(boolean minor) {
        this.minor = minor;
    }

    public Trip getRootTrip() {
        return rootTrip;
    }

    public void setRootTrip(Trip rootTrip) {
        this.rootTrip = rootTrip;
    }

    // -------------------- Methods

    // Add trip 
    public void addTrip(Trip trip){
        if(rootTrip == null){
            setRootTrip(trip);
        }else{
            Trip compare = rootTrip;
            do{
                if(trip.getTripPrice()>= compare.getTripPrice()){
                    if(compare.getRight() == null){
                        compare.setRight(trip);
                    } else {
                        compare = compare.getRight();
                    }
                } else {
                    if(compare.getLeft() == null){
                        compare.setLeft(trip);
                    } else {
                        compare = compare.getLeft();
                    }
                }
            }while(compare != null);
            
        }
    }

    // Print trips
    public List<Trip> getTrips(){
        List<Trip> list = new ArrayList<>();
        rangeTripsAdd(rootTrip, list);
        return list;
    }

    private void rangeTripsAdd (Trip node, List<Trip> list) {
        if (node == null) {
            return;
        }
        rangeTripsAdd(node.getLeft(), list);
        list.add(node);
        rangeTripsAdd(node.getRight(), list);
    }

    // Return trip by id
    public Trip getTrips(String id){
        Trip temp = null;
        rangeTripSearch(rootTrip, id, temp);
        return temp;
    }

    private void rangeTripSearch (Trip node, String id, Trip returnV) {
        if (node == null) {
            return ;
        }
        rangeTripSearch(node.getLeft(), id, returnV);
        if(id.compareTo(node.getId()) == 0){
            returnV = node;
        }
        rangeTripSearch(node.getRight(), id, returnV);
    }

    // Add luggage
    public void addLuggage(Luggage luggage, Trip trip){
        trip.addLuggage(luggage);
    }
}
