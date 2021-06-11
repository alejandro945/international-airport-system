package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Costumer extends User {
    Random r = new Random();
    private String iconPath;
    private Trip rootTrip;
    private String state;
    private String notifications;

    public Costumer() { // JUnit Tests
        super("Tester", "User", 8, "testerUser@correo.co", "8", UserRole.COSTUMER_USER);
        int value = (int) ((Math.random() * 4));
        state = CostumerState.values()[value].name();
    }

    public Costumer(String name, String lastName, long id, String email, String password) {
        super(name, lastName, id, email, password, UserRole.COSTUMER_USER);
        rootTrip = null;
        int value = (int) ((Math.random() * 4));
        state = CostumerState.values()[value].name();
    }

    public Costumer(String name, String lastName, long id, String iconPath) {
        super(name, lastName, id, "", "", UserRole.COSTUMER_USER);
        this.iconPath = iconPath;
        rootTrip = null;
        int value = (int) ((Math.random() * 4));
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

    public String getNotifications() {
        return this.notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
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
                newTrip.setFather(parent);
            } else {
                addTrip(parent.getLeft(), newTrip);
            }
        } else {
            if (parent.getRight() == null) {
                parent.setRight(newTrip);
                newTrip.setFather(parent);
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
            

            Trip parent = temp.getFather();
            Trip right = temp.getRight();
            Trip left = temp.getLeft();
            
            if (right == null && left == null) {    // no children case
                if (rootTrip == temp) {
                    rootTrip = null;
                } else {
                    if (parent.getLeft() == (temp)) {
                        parent.setLeft(null);
                    } else {
                        parent.setRight(null);
                    }
                }
            } else if (right == null || left == null) { // One children case

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

    // Remove Trip
    public String removeTrip(Trip trip) {
        if(rootTrip==null) {
            System.out.println("There are no trips to delete.");
        } else if(isLeaf(trip)) {
            removeLeaf(trip);
        } else if(trip.getRight()!=null && trip.getLeft()==null) {
            removeWithChild(trip, 2);
        } else if(trip.getRight()==null && trip.getLeft()!=null){
            removeWithChild(trip, 1);
        } else {
            removeWithChild(trip, 3);
        }
        return "Trip removed";
    }

    private void removeLeaf(Trip trip) {
        if(trip == rootTrip) {
            rootTrip = null;
        } else {
            Trip parent = trip.getFather();

            if(parent.getLeft() == trip) {
                parent.setLeft(null);
            }

            if(parent.getRight() == trip) {
                parent.setRight(null);
            }

            trip = null;
        }
    }

    private void removeWithChild(Trip trip, int tripType) {
        Trip nextTrip = null;

        switch (tripType) {
            case 1:
                nextTrip = trip.getLeft();
                break;
            case 2:
                nextTrip = minSubTree(trip.getRight());
                break;
            case 3:
                nextTrip = minSubTree(trip.getRight());
                if(!isRoot(nextTrip.getFather())) {
                    trip.getLeft().setFather(nextTrip);
                    trip.getRight().setFather(nextTrip);
                    if(nextTrip.getFather().getLeft()==nextTrip) {
                        nextTrip.getFather().setLeft(null);
                    } else if(nextTrip.getFather().getRight()==nextTrip) {
                        nextTrip.getFather().setRight(null);
                    }
                }
                break;
        }

        nextTrip.setFather(trip.getFather());

        if(!(trip.getFather() == null)) {
        //if(!isRoot(trip)) {
            if(trip.getFather().getLeft()==trip) {
                trip.getFather().setLeft(nextTrip);
            } else if(trip.getFather().getRight()==trip) {
                trip.getFather().setRight(nextTrip);
            }
        } else {
            rootTrip = nextTrip;
        }

        if(trip.getRight()!=null && trip.getRight()!=nextTrip) {
            nextTrip.setRight(trip.getRight());
        }

        if(trip.getLeft()!=null && trip.getLeft()!=nextTrip) {
            nextTrip.setLeft(trip.getRight());
        }

        trip = null;
    }

    private Trip minSubTree(Trip trip) {
        if(trip!=null && trip.getLeft()!=null) {
            while(!isLeaf(trip)) {
                trip = minSubTree(trip.getLeft());
            }
        }
        return trip;
    }
    /**
     * Verifies if an trip is the root of the tree.
     * @param trip trip to be checked.
     * @return Returns true if the trip is the root of the tree. Else returns false.
     */
    public boolean isRoot(Trip trip) {
        return rootTrip == trip;
    }

    /**
     * Verifies if a trip is a leaf of the tree.
     * @param trip Trip to be checked.
     * @return Returns true if the advisor is a leaf of the tree. Else returns false.
     */
    public boolean isLeaf(Trip trip) {
        return trip.getLeft() == null && trip.getRight() == null;
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
