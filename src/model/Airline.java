package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Airline implements Serializable, Comparable<Airline> {
    private static final long serialVersionUID = 1L;
    private String airlineName;
    private String logo;
    private List<Aircraft> aircraft;
    private Advisor assistantRoot; // Bynary Tree
    private List<Pilot> pilots;
    private List<User> users;
    private Ticket ticket; // Binary Tree
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

    /**
     * @return Assistant
     */
    public Advisor getAssistantRoot() {
        return this.assistantRoot;
    }

    /**
     * @param assistantRoot
     */
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

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Flight> getFlights() {
        return this.flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return airlineName;
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

    // For development purposes. Delete. ------------------------------------------
    public String inorder() {
        if (assistantRoot == null) {
            return "There are no advisors yet.";
        } else {
            return inorder(assistantRoot);
        }
    }

    private String inorder(Advisor advisor) {
        String msg = "";
        if (advisor.getLeft() != null) {
            msg += inorder(advisor.getLeft());
        }
        msg += advisor.getName() + " " + advisor.getLastName() + "\n";
        if (advisor.getRight() != null) {
            msg += inorder(advisor.getRight());
        }
        return msg;
    }
    // For development purposes. Delete. ------------------------------------------

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

    @Override
    public int compareTo(Airline a) {
        if (a.getAirlineName().equalsIgnoreCase(airlineName)) {
            return 0;
        } else {
            return airlineName.compareTo(a.getAirlineName());
        }
    }

    public void importDataEmployees(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(",");
            String name = parts[0];
            String lastName = parts[1];
            int id = Integer.parseInt(parts[2]);
            String type = parts[3];
            line = br.readLine();
            ;
        }
        br.close();
    }

    public void exportDataEmployees(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("AIRPORT SYSTEM EMPLOYEES REPORT");
        pw.println("Name,Last name,Id,Status");
        for (int i = 0; i < getEmployees().size(); i++) {
            Collaborator e = getEmployees().get(i);
            pw.println(
                    e.getName() + "," + e.getLastName() + "," + e.getId() + "," + e.getAirline() + "," + e.getType());
        }
        pw.close();
    }
}
