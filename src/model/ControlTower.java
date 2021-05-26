package model;

import java.util.List;

public class ControlTower {
    private List<Flight> flight;
    private boolean state;
    private int maintainceTime;

    public ControlTower(List<Flight> flight, boolean state, int maintainceTime) {
        this.flight = flight;
        this.state = state;
        this.maintainceTime = maintainceTime;
    }

    public List<Flight> getFlight() {
        return this.flight;
    }

    public void setFlight(List<Flight> flight) {
        this.flight = flight;
    }

    public boolean isState() {
        return this.state;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getMaintainceTime() {
        return this.maintainceTime;
    }

    public void setMaintainceTime(int maintainceTime) {
        this.maintainceTime = maintainceTime;
    }

}
