package model;

import java.util.ArrayList;
import java.util.List;

public class Advisor extends Collaborator implements Comparable<Advisor> {

    private List<Costumer> costumers;
    private Advisor parent;
    private Advisor right;
    private Advisor left;

    /**
     * The constructor method of an Advisor Object<br>
     */
    public Advisor(String name, String lastName, long id, Airline airline) {
        super(name, lastName, id, airline, "Advisor");
        costumers = new ArrayList<>();
    }

    @Override
    public int getReport() {
        return costumers.size();
    }

    public List<Costumer> getCostumers() {
        return this.costumers;
    }

    public void setCostumers(Costumer c) {
        this.costumers.add(c);
    }

    public Advisor getRight() {
        return right;
    }

    public void setRight(Advisor right) {
        this.right = right;
    }

    public Advisor getLeft() {
        return left;
    }

    public void setLeft(Advisor left) {
        this.left = left;
    }

    public Advisor getParent() {
        return parent;
    }

    public void setParent(Advisor parent) {
        this.parent = parent;
    }

    /**
     * Compares two advisors ids.
     * 
     * @param advisor Advisor to be compared with.
     * @return Returns -1 if this id is least than the other one. Returns 1 if this id is greater. Returns 0 if both ids are the same.
     *
     */
    @Override
    public int compareTo(Advisor advisor) {
        int result = -1;
        if (this.getId() == advisor.getId()) {
            result = 0;
        } else if (this.getId() > advisor.getId()) {
            result = 1;
        }
        return result;
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
