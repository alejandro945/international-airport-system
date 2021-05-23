package model;

public class Costumer extends User {
    private String iconPath;
    private Ticket ticket;

    public Costumer(String name, String lastName, long id, String email, String password) {
        super(name, lastName, id, email, password);
    }

    public Costumer(String name, String lastName, long id, String iconPath) {
        super(name, lastName, id, "", "");
        this.iconPath = iconPath;
    }

    /**
     * @return Ticket
     */
    public Ticket getTicket() {
        return this.ticket;
    }

    /**
     * @param ticket
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getIconPath() {
        return this.iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

}
