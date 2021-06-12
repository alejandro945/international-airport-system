package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Costumer extends User {

    Random r = new Random();
    private String iconPath;
    private Trip rootTrip; // Binary Tree
    private String state;
    private String notifications;

    public Costumer(int value) { // JUnit Tests
        super("Tester", "User", 8, "testerUser@correo.co", "8", UserRole.COSTUMER_USER);
        state = CostumerState.values()[value].name();
    }

    /**
     * The constructor method of an JWT Costumer Object<br>
     */
    public Costumer(String name, String lastName, long id, String email, String password) {
        super(name, lastName, id, email, password, UserRole.COSTUMER_USER);
        rootTrip = null;
        int value = (int) ((Math.random() * 4));
        state = CostumerState.values()[value].name();
    }

    /**
     * The constructor method of an Google Auth Costumer Object<br>
     */
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

    /**
     * Adds a new trip to the customer.
     * @param newPNode Trip to be added.
     */
    public void addTrip(Trip newPNode) {

        if (rootTrip == null) {
            rootTrip = newPNode;
        } else {
            addTrip(rootTrip, newPNode);
        }

    }

    /**
     * Adds a new trip to the customer.
     * @param parent Parent trip.
     * @param newTrip Trip to be added.
     */
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

    /**
     * Creates a lists from trips linked list.
     * @return Returns a list with all the customer trips.
     */
    public List<Trip> getTrips() {
        List<Trip> list = new ArrayList<>();
        rangeTripsAdd(rootTrip, list);
        bubbleSorting(list);
        return list;
    }

    /**
     * Bubble sorting algorithm for trips.
     * @param list List containing customer's trips.
     */
    private void bubbleSorting(List<Trip> list) {

        for (int i = list.size() - 1; i > 0; i--) {

            for (int j = 0; j < list.size() - 1; j++) {

                if (list.get(j).compareByDate(list.get(j + 1)) > 0) {

                    Trip temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set((j + 1), temp);

                } else if (list.get(j).compareByDate(list.get(j + 1)) == 0) {
                    if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                        Trip temp = list.get(j);
                        list.set(j, list.get(j + 1));
                        list.set((j + 1), temp);
                    }
                }
            }
        }
    }

    /**
     * Auxiliary method to create a list from trip's linked list.
     * @param node Reference to current trip.
     * @param list List with trips.
     */
    private void rangeTripsAdd(Trip node, List<Trip> list) {
        if (node == null) {
            return;
        }
        rangeTripsAdd(node.getLeft(), list);
        list.add(node);
        rangeTripsAdd(node.getRight(), list);
    }

    /**
     * Gets a trip.
     * @param toFind Trip to be founded.
     * @return Returns the trip founded. Returns null if nothing is found.
     */
    public Trip getTrip(Trip toFind) {
        Trip temp = null;
        rangeTripSearch(rootTrip, toFind, temp);
        return temp;
    }

    /**
     * Searches for a trip.
     * @param node Reference to current trip.
     * @param toFind Trip to be founded.
     * @param returnV Trip to be returned.
     */
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

    /**
     * Deletes a trip from customer trip list.
     * @param trip Trip to be deleted.
     * @return Returns string with operation result.
     */
    public String deleteTrip(Trip trip) {
        Trip temp = trip;

        if (temp != null) {

            Trip parent = temp.getFather();
            Trip right = temp.getRight();
            Trip left = temp.getLeft();

            if (right == null && left == null) { // no children case
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
            return "It could not been delete it";
        }

    }

    /**
     * Gets minimum value from a binary tree.
     * @param starTrip Head trip from tree.
     * @return Returns minimum trip from tree.
     */
    private Trip minTrip(Trip starTrip) {
        if (starTrip.getLeft() == null) {
            return starTrip;
        } else {
            return minTrip(starTrip.getLeft());
        }
    }

    /**
     * Removes a trip from trips binary tree.
     * @param trip Trip to be removed.
     * @return Returns string with operation result.
     */
    public String removeTrip(Trip trip) {
        if (rootTrip == null) {
            System.out.println("There are no trips to delete.");
        } else if (isLeaf(trip)) {
            removeLeaf(trip);
        } else if (trip.getRight() != null && trip.getLeft() == null) {
            removeWithChild(trip, 2);
        } else if (trip.getRight() == null && trip.getLeft() != null) {
            removeWithChild(trip, 1);
        } else {
            removeWithChild(trip, 3);
        }
        return "Trip removed succesfully";
    }

    /**
     * Removes leaf from binary tree.
     * @param trip Trip to be removed.
     */
    private void removeLeaf(Trip trip) {
        if (trip == rootTrip) {
            rootTrip = null;
        } else {
            Trip parent = trip.getFather();

            if (parent.getLeft() == trip) {
                parent.setLeft(null);
            }

            if (parent.getRight() == trip) {
                parent.setRight(null);
            }

            trip = null;
        }
    }

    /**
     * Removes trip when it has child.
     * @param trip Trip to be removed.
     * @param tripType Integer representing if it has a child on its left, right or both.
     *
     */
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
                if (!isRoot(nextTrip.getFather())) {
                    trip.getLeft().setFather(nextTrip);
                    trip.getRight().setFather(nextTrip);
                    if (nextTrip.getFather().getLeft() == nextTrip) {
                        nextTrip.getFather().setLeft(null);
                    } else if (nextTrip.getFather().getRight() == nextTrip) {
                        nextTrip.getFather().setRight(null);
                    }
                }
                break;
        }

        nextTrip.setFather(trip.getFather());

        if (!(trip.getFather() == null)) {
            // if(!isRoot(trip)) {
            if (trip.getFather().getLeft() == trip) {
                trip.getFather().setLeft(nextTrip);
            } else if (trip.getFather().getRight() == trip) {
                trip.getFather().setRight(nextTrip);
            }
        } else {
            rootTrip = nextTrip;
        }

        if (trip.getRight() != null && trip.getRight() != nextTrip) {
            nextTrip.setRight(trip.getRight());
        }

        if (trip.getLeft() != null && trip.getLeft() != nextTrip) {
            nextTrip.setLeft(trip.getRight());
        }

        trip = null;
    }

    /**
     * Gets minimum value from a binary sub tree.
     *
     * @param trip Head trip from sub tree.
     * @return Returns minimum trip from sub tree.
     */
    private Trip minSubTree(Trip trip) {
        if (trip != null && trip.getLeft() != null) {
            while (!isLeaf(trip)) {
                trip = minSubTree(trip.getLeft());
            }
        }
        return trip;
    }

    /**
     * Verifies if an trip is the root of the tree.
     * 
     * @param trip trip to be checked.
     * @return Returns true if the trip is the root of the tree. Else returns false.
     */
    public boolean isRoot(Trip trip) {
        return rootTrip == trip;
    }

    /**
     * Verifies if a trip is a leaf of the tree.
     * 
     * @param trip Trip to be checked.
     * @return Returns true if the advisor is a leaf of the tree. Else returns
     *         false.
     */
    public boolean isLeaf(Trip trip) {
        return trip.getLeft() == null && trip.getRight() == null;
    }

    /**
     * Adds luggage to customer.
     * @param luggage Luggage to be added.
     * @param trip Trip reference for the luggage to be added.
     */
    public void addLuggage(Luggage luggage, Trip trip) {
        trip.addLuggage(luggage);
    }

    public String getState() {
        return state;
    }

    public void setState(int state) {
        this.state = CostumerState.values()[state].name();
    }

    /**
     * Counts the amount of trips.
     * @param node Reference to current trip.
     * @param count Integer containing the count of trips.
     * @return Returns an integer with all the amount of trips.
     */
    private int countTrips(Trip node, int count) {
        if (node == null) {
            return 0;
        }
        countTrips(node.getLeft(), count);
        count++;
        countTrips(node.getRight(), count);
        return count;
    }

    /**
     * Auxiliary method to count amount of trips.
     * @return Returns customer's amount of trips.
     */
    public int countTrips() {
        return countTrips(rootTrip, 0);
    }

}
