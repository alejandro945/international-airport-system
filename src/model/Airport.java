package model;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    public static final String USER_SUCCESS = " have been added to our Airport succesfully";
    public static final String USER_ERROR = " could not been added to our Airport (Already exists)";
    private Track firstTrack;
    private List<User> users;
    private Costumer logged;

    public Airport() {
        users = new ArrayList<User>();
    }

    public Track getFirstTrack() {
        return this.firstTrack;
    }

    public void setFirstTrack(Track firstTrack) {
        this.firstTrack = firstTrack;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Costumer getLogged() {
        return this.logged;
    }

    public void setLogged(Costumer logged) {
        this.logged = logged;
    }

    public String createUser(String name, String lastName, long id, String email, String password) {
        String msg = "";
        if (!searchUserId(id)) {
            Costumer newCostumer = new Costumer(name, lastName, id, email, password);
            users.add(newCostumer);
            msg = name + USER_SUCCESS;
        } else {
            msg = USER_ERROR;
        }
        return msg;
    }

    public String createUser(String name, String lastName, long id, String iconPath) {
        String msg = "";
        if (!searchUserId(id)) {
            Costumer newCostumer = new Costumer(name, lastName, id, iconPath);
            users.add(newCostumer);
            setLogged(newCostumer);
            msg = name + USER_SUCCESS;
        } else {
            msg = USER_ERROR;
        }
        return msg;
    }

    public boolean searchUserId(long newId) {
        boolean render = false;
        for (int i = 0; i < users.size() && !render; i++) {
            if (newId == users.get(i).getId()) {
                render = true;
            }
        }
        return render;
    }

    public boolean userVerification(long id, String password) {
        boolean found = false;
        for (int i = 0; i < users.size() && !found; i++) {
            if (users.get(i).getId() == (id) && users.get(i).getPassword().equals(password)) {
                setLogged((Costumer) users.get(i));
                found = true;
            }
        }
        return found;
    }
}
