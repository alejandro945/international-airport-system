package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Airline implements Serializable, Comparable<Airline> {

    private static final long serialVersionUID = 1L;
    private String airlineName;
    private String logo;
    private List<Aircraft> aircraft;
    private Advisor assistantRoot; // Bynary Tree
    private List<Pilot> pilots;
    private List<User> users;
    private List<Flight> flights;

    private final int LEFT_ADVISOR = 1;
    private final int RIGHT_ADVISOR = 2;
    private final int TWO_ADVISORS = 3;

    public Airline(String airlineName, String logo) {
        pilots = new ArrayList<>();
        flights = new ArrayList<>();
        aircraft = new ArrayList<>();
        this.airlineName = airlineName;
        this.logo = logo;
    }

    public Advisor getAssistantRoot() {
        return this.assistantRoot;
    }

    public void setAssistantRoot(Advisor assistantRoot) {
        this.assistantRoot = assistantRoot;
    }

    public String getAirlineName() {
        return this.airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public ImageView getLogo() {
        File file = new File(logo);
        ImageView icon = new ImageView(new Image(("file:///" + file.getAbsolutePath())));
        icon.setFitHeight(50);
        icon.setFitWidth(200);
        return icon;
    }

    public String getIcon() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Aircraft> getAircraft() {
        return this.aircraft;
    }

    public void setAircraft(List<Aircraft> aircraft) {
        this.aircraft = aircraft;
    }

    public List<Pilot> getPilots() {
        return this.pilots;
    }

    public void setPilots(List<Pilot> pilots) {
        this.pilots = pilots;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Flight> getFlights() {
        return this.flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    /**
     * Returns a string representation of the object.
     * @return Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return airlineName;
    }

    /**
     * Searches an aircraft by its code.
     * @param code String representing the aircraft code.
     * @return Returns the aircraft if found. Else returns null.
     */
    public Aircraft searchAircraft(String code){
        Aircraft a = null;
        for (Aircraft ac : aircraft) {
            if(ac.getPlaneCode().equalsIgnoreCase(code)){
                a = ac;
            }
        }
        return a;
    }

    /**
     * Verifies if an advisor is the root of the tree.
     * 
     * @param advisor Advisor to be checked.
     * @return Returns true if the advisor is the root of the tree. Else returns
     *         false.
     */
    public boolean isRoot(Advisor advisor) {
        return assistantRoot == advisor;
    }

    /**
     * Verifies if an advisor is a leaf of the tree.
     * 
     * @param advisor Advisor to be checked.
     * @return Returns true if the advisor is a leaf of the tree. Else returns
     *         false.
     */
    public boolean isLeaf(Advisor advisor) {
        return advisor.getLeft() == null && advisor.getRight() == null;
    }

    /**
     * Verifies if an advisor is an internal of the tree.
     * 
     * @param advisor Advisor to be checked.
     * @return Returns true if the advisor is an internal of the tree. Else returns
     *         false.
     */
    public boolean isInternal(Advisor advisor) {
        return !isLeaf(advisor);
    }

    /**
     * Adds an advisor to the binary tree.
     * 
     * @param advisor Advisor to be added.
     */
    public void addAdvisor(Advisor advisor) {
        addAdvisor(assistantRoot, advisor);
    }

    /**
     * Auxiliar method to add an advisor to the binary tree.
     * 
     * @param origin  Reference to origin advisor. Starts with root.
     * @param current Reference to current advisor.
     */
    private void addAdvisor(Advisor origin, Advisor current) {
        if (assistantRoot == null) {
            assistantRoot = current;
        } else {
            if (origin.compareTo(current) > 0) {
                if (origin.getLeft() != null) {
                    addAdvisor(origin.getLeft(), current);
                } else {
                    current.setParent(origin);
                    origin.setLeft(current);
                }
            } else {
                if (origin.getRight() != null) {
                    addAdvisor(origin.getRight(), current);
                } else {
                    current.setParent(origin);
                    origin.setRight(current);
                }
            }
        }
    }

    /**
     * Removes advisor from the binary tree.
     * @param advisor Advisor to be removed.
     */
    public void removeAdvisor(Advisor advisor) {
        if (assistantRoot == null) {
            System.out.println("There are no advisors to delete.");
        } else if (isLeaf(advisor)) {
            removeLeaf(advisor);
        } else if (advisor.getRight() != null && advisor.getLeft() == null) {
            removeWithChild(advisor, RIGHT_ADVISOR);
        } else if (advisor.getRight() == null && advisor.getLeft() != null) {
            removeWithChild(advisor, LEFT_ADVISOR);
        } else {
            removeWithChild(advisor, TWO_ADVISORS);
        }
    }

    /**
     * Removes leaf from binary tree.
     * @param advisor Advisor to be removed.
     */
    private void removeLeaf(Advisor advisor) {
        if (isRoot(advisor)) {
            assistantRoot = null;
        } else {
            Advisor parent = advisor.getParent();

            if (parent.getLeft() == advisor) {
                parent.setLeft(null);
            }

            if (parent.getRight() == advisor) {
                parent.setRight(null);
            }

            advisor = null;
        }
    }

    /**
     * Removes advisor when it has child.
     * @param advisor Advisor to be removed.
     * @param advisorType Integer representing if it has a child on its left, right or both. Linked with constants.
     */
    private void removeWithChild(Advisor advisor, int advisorType) {
        Advisor nextAdvisor = null;

        switch (advisorType) {
            case LEFT_ADVISOR:
                nextAdvisor = advisor.getLeft();
                break;
            case RIGHT_ADVISOR:
                nextAdvisor = minSubTree(advisor.getRight());
                break;
            case TWO_ADVISORS:
                nextAdvisor = minSubTree(advisor.getRight());
                if (!isRoot(nextAdvisor.getParent())) {
                    advisor.getLeft().setParent(nextAdvisor);
                    advisor.getRight().setParent(nextAdvisor);
                    if (nextAdvisor.getParent().getLeft() == nextAdvisor) {
                        nextAdvisor.getParent().setLeft(null);
                    } else if (nextAdvisor.getParent().getRight() == nextAdvisor) {
                        nextAdvisor.getParent().setRight(null);
                    }
                }
                break;
        }

        nextAdvisor.setParent(advisor.getParent());

        if (!isRoot(advisor)) {
            if (advisor.getParent().getLeft() == advisor) {
                advisor.getParent().setLeft(nextAdvisor);
            } else if (advisor.getParent().getRight() == advisor) {
                advisor.getParent().setRight(nextAdvisor);
            }
        } else {
            assistantRoot = nextAdvisor;
        }

        if (advisor.getRight() != null && advisor.getRight() != nextAdvisor) {
            nextAdvisor.setRight(advisor.getRight());
        }

        if (advisor.getLeft() != null && advisor.getLeft() != nextAdvisor) {
            nextAdvisor.setLeft(advisor.getRight());
        }

        advisor = null;
    }

    /**
     * Gets minimum value from a binary sub tree.
     * @param advisor Head advisor from sub tree.
     * @return Returns minimum advisor from sub tree.
     */
    private Advisor minSubTree(Advisor advisor) {
        if (advisor != null && advisor.getLeft() != null) {
            while (!isLeaf(advisor)) {
                advisor = minSubTree(advisor.getLeft());
            }
        }
        return advisor;
    }

    /**
     * Makes a list from the advisors binary tree.
     * 
     * @return Returns a list with all the airline advisors.
     */
    public ArrayList<Advisor> advisorsToArray() {
        ArrayList<Advisor> advisors = new ArrayList<>();
        advisorsToArray(assistantRoot, advisors);
        return advisors;
    }

    /**
     * Auxiliar method to create a list from a binary tree.
     * 
     * @param current Reference to current advisor.
     * @param result  Arraylist with advisors.
     */
    private void advisorsToArray(Advisor current, ArrayList<Advisor> result) {
        if (current == null) {
            return;
        }
        advisorsToArray(current.getLeft(), result);
        result.add(current);
        advisorsToArray(current.getRight(), result);
    }

    /**
     * Merges all pilots and advisors into a single list.
     * 
     * @return Returns a list with all the airline employees.
     */
    public List<Collaborator> getEmployees() {
        ArrayList<Collaborator> employees = new ArrayList<>(pilots);
        employees.addAll(advisorsToArray());
        return new ArrayList<>(employees);
    }

    /**
     * Compares two airline names.
     * @param a Airline to be compared with.
     * @return Returns -1 if the name is least than the other one. Returns 1 if this name is greater. Returns 0 if both names are the same.
     */
    @Override
    public int compareTo(Airline a) {
        if (a.getAirlineName().equalsIgnoreCase(airlineName)) {
            return 0;
        } else {
            return airlineName.compareTo(a.getAirlineName());
        }
    }

    /**
     * Creates a list from advisors linked list.
     * @return Returns list with all advisors.
     */
    public List<Advisor> getAdvisor() {
        List<Advisor> list = new ArrayList<>();
        rangeAdvisorsAdd(assistantRoot, list);
        return list;
    }

    /**
     * Auxiliar method to create list from advisors linked list.
     * @param advisor Actual advisor.
     * @param list List containing advisors.
     */
    private void rangeAdvisorsAdd(Advisor advisor, List<Advisor> list) {
        if (advisor == null) {
            return;
        }
        rangeAdvisorsAdd(advisor.getLeft(), list);
        list.add(advisor);
        rangeAdvisorsAdd(advisor.getRight(), list);
    }

    /**
     * Adds pilot to the airline.
     * @param name String containing the pilot's name.
     * @param lastName String containing the pilot's lastname.
     * @param id Integer containing the pilot's identification number.
     * @param airline Airline linked with the pilot.
     */
    public void addPilot(String name, String lastName, int id, Airline airline){
        Pilot temp = new Pilot(name, lastName, id, airline);
        pilots.add(temp);
    }

    /**
     * Adds flight to the airline.
     * @param id Identification number of the flight.
     * @param departureDate Departure date of the flight.
     * @param departureHour Departure hour of the flight.
     * @param arrivalDate Arrival date of the flight.
     * @param arrivalHour Arrival hour of the flight.
     * @param origin Origin place of the flight.
     * @param destination Destination of the flight.
     * @param track Track the flight will use.
     * @param airline Airline linked with the flight.
     * @param aircraft Aircraft linked with the flight.
     */
    public void addFlight(String id,String departureDate,String departureHour,String arrivalDate,String arrivalHour,Places origin, Places destination,Track track,Airline airline, Aircraft aircraft){
        Flight temp = new Flight(id, departureDate, departureHour, arrivalDate, arrivalHour, origin, destination, track, airline, aircraft);
        flights.add(temp);
    }

    /**
     * Adds a new aircraft to the airline.
     * @param plane Aircraft to be added.
     */
    public void addAircraft(Aircraft plane){
        aircraft.add(plane);
    }

    /**
     * Removes pilot from airline.
     * @param pilot Pilot to be removed.
     */
    public void removePilot(Pilot pilot){
        pilots.remove(pilot);
    }

    /**
     * Removes flight from airline.
     * @param flight Flight to be removed.
     */
    public void removeFlight(Flight flight){
        flights.remove(flight);
    }

    /**
     * Removes aircraft from airline.
     * @param plane Aircraft to be removed.
     */
    public void removeAircraft(Aircraft plane){
        aircraft.remove(plane);
    }

    /**
     * Imports employee data from file.
     * @param fileName Name of the file containing all the data.
     * @throws IOException
     */
    public void importDataEmployees(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(";");
            String name = parts[1];
            String lastName = parts[2];
            int id = Integer.parseInt(parts[3]);
            String type = parts[4];
            line = br.readLine();
            if(type.equalsIgnoreCase("pilot")){
                pilots.add(new Pilot(name, lastName, id, this));
            }else{
                addAdvisor(new Advisor(name, lastName, id, this));
            };
        }
        br.close();
    }

    /**
     * Exports employee data to a file.
     * @param fileName Name of the file to be exported with the data.
     * @throws FileNotFoundException
     */
    public void exportDataEmployees(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("AIRPORT SYSTEM EMPLOYEES REPORT");
        pw.println("Name;Last name;Id;Type;Airline");
        for (int i = 0; i < getEmployees().size(); i++) {
            Collaborator e = getEmployees().get(i);
            pw.println(
                    e.getName() + ";" + e.getLastName() + ";" + e.getId() + ";" + e.getType() + ";" + e.getAirline());
        }
        pw.close();
    }

    /**
     * Import aircraft data from file.
     * @param fileName Name of the file containing all the data.
     * @throws IOException
     */
    public void importDataAircraft(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(";");
            String planeCode = parts[1];
            int planeWeight = Integer.parseInt(parts[2]);
            int capacity = Integer.parseInt(parts[3]);
            line = br.readLine();
            if(searchAircraft(planeCode)==null){
                aircraft.add(new Aircraft(planeCode, planeWeight, capacity, this));
            }
        }
        br.close();
    }

    /**
     * Exports aircraft data to a file.
     * @param fileName Name of the file to be exported with the data.
     * @throws FileNotFoundException
     */
    public void exportDataAircraft(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("AIRPORT SYSTEM AIRCRAFT REPORT");
        pw.println("Code;Weight;Capacity;Airline");
        for (int i = 0; i < getAircraft().size(); i++) {
            Aircraft a = getAircraft().get(i);
            pw.println(
                    a.getPlaneCode() + ";" + a.getPlaneWeight() + ";" + a.getCapacity() + ";" + a.getAirline());
        }
        pw.close();
    }

}
