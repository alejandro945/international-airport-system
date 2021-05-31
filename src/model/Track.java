package model;

public class Track implements Maintenance {
    private int id;
    private boolean state;
    private String gate;

    public Track(int id, String gate) {
        this.id = id;
        this.gate = gate;
        state = true;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGate() {
        return this.gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    @Override
    public void inMaintenance() {

    }

}
