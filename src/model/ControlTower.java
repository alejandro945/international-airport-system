package model;

import java.util.ArrayList;
import java.util.List;

public class ControlTower {
    private List<Flight> flights;
    private boolean maintenance;
    private int maintenanceTime;

    public ControlTower() {
        flights = new ArrayList<>();
        maintenance = false;
        maintenanceTime = 10;
    }

    public List<Flight> getFlights() {
        return this.flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public int getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(int maintenanceTime) {
        this.maintenanceTime = maintenanceTime;
    }

}
