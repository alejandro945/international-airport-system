package model;

import java.util.ArrayList;
import java.util.List;

public class Costumer extends User {
    private String iconPath;
    private List<Ticket> ticket;

    public Costumer(){  //JUnit Tests
        super("Tester", "User", 122442, "testerUser@correo.co", "password", UserRole.COSTUMER_USER);
        ticket = new ArrayList<>();
    }

    public Costumer(String name, String lastName, long id, String email, String password) {
        super(name, lastName, id, email, password, UserRole.COSTUMER_USER);
        ticket = new ArrayList<>();
    }

    public Costumer(String name, String lastName, long id, String iconPath) {
        super(name, lastName, id, "", "", UserRole.COSTUMER_USER);
        this.iconPath = iconPath;
        ticket = new ArrayList<>();
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public List<Ticket> getTicket() {
        return this.ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

}
