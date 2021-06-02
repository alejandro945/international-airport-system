package model;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    public static final String USER_SUCCESS = " have been added to our Airport successfully";
    public static final String DELETE_SUCCESS = " have been deleted successfully";
    public static final String EDIT_SUCCESS = " have been edited  successfully";
    public static final String USER_ERROR = " could not been added to our Airport (Already exists)";
    public static final String DELETE_ERROR = " Hey! is your account (Could not been deleted it)";
    public static final String OAUTH_MESSAGE = " your account have been rendered successfully";
    
    private Track firstTrack;
    private Track lastTrack;
    private int trackAmount;
    private List<User> users;
    private List<Airline> airlines;
    private Costumer logged;
    private User adminLogged;
    private List<Flight> flights;

    private ControlTower controlTower;

    public Airport() {
        users = new ArrayList<>();
        airlines = new ArrayList<>();
        flights = new ArrayList<>();

        users.add(new User("Alejandro", "Varela", 1, "alejo8677@gmail.com", "1", UserRole.AIRPORT_ADMIN));
        airlines.add(new Airline("Avianca", ""));
        airlines.add(new Airline("Spirit", ""));
        airlines.add(new Airline("Viva Air", ""));

        controlTower = new ControlTower();
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

    public List<Airline> getAirlines() {
        return this.airlines;
    }

    public void setAirlines(List<Airline> airlines) {
        this.airlines = airlines;
    }

    public User getAdminLogged() {
        return this.adminLogged;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }


    public void setAdminLogged(User adminLogged) {
        this.adminLogged = adminLogged;
    }

    public ControlTower getControlTower() {
        return controlTower;
    }

    public void setControlTower(ControlTower controlTower) {
        this.controlTower = controlTower;
    }

    // USER-TOWER-MIGRATION
    public String createUser(String name, String lastName, long id, String email, String password, UserRole role) {
        String msg = "";
        if (!searchUserId(id)) {
            User newUser = new User(name, lastName, id, email, password, role);
            users.add(newUser);
            msg = name + USER_SUCCESS;
        } else {
            msg = USER_ERROR;
        }
        return msg;
    }

    public String editUser(User user, String name, String lastName, long id, String email, String password,
            UserRole role) {
        user.setName(name);
        user.setLastName(lastName);
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return name + EDIT_SUCCESS;
    }

    // AIRLINE-ADMIN
    public String createUser(String name, String lastName, long id, String email, String password, Airline airline) {
        String msg = "";
        if (!searchUserId(id)) {
            AirlineUser newAirlineUser = new AirlineUser(name, lastName, id, email, password, airline);
            users.add(newAirlineUser);
            msg = name + USER_SUCCESS;
        } else {
            msg = USER_ERROR;
        }
        return msg;
    }

    public String editUser(AirlineUser user, String name, String lastName, long id, String email, String password,
            Airline airline) {
        user.setName(name);
        user.setLastName(lastName);
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setAirline(airline);
        return name + EDIT_SUCCESS;
    }

    // JWT-COSTUMER
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

    // OAUTH2
    public String createUser(String name, String lastName, long id, String iconPath) {
        String msg = "";
        if (!searchUserId(id)) {
            Costumer newCostumer = new Costumer(name, lastName, id, iconPath);
            users.add(newCostumer);
            setLogged(newCostumer);
            msg = name + USER_SUCCESS;
        } else {
            setLogged((Costumer) searchUser(id));
            msg = OAUTH_MESSAGE;
        }
        return msg;
    }

    public String deleteUser(User user) {
        String msg = "";
        if (getAdminLogged() != user) {
            users.remove(user);
            msg = user.getName() + DELETE_SUCCESS;
        } else {
            msg = DELETE_ERROR;
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

    public User searchUser(long id) {
        User user = null;
        for (User u : users) {
            if (u.getId() == id) {
                user = u;
            }
        }
        return user;
    }

    public boolean userVerification(long id, String password) {
        boolean found = false;
        for (int i = 0; i < users.size() && !found; i++) {
            if (users.get(i).getId() == (id) && users.get(i).getPassword().equals(password)) {
                if (users.get(i) instanceof Costumer) {
                    setLogged((Costumer) users.get(i));
                } else {
                    setAdminLogged(users.get(i));
                }
                found = true;
            }
        }
        return found;
    }

    /**
     * Adds track to the last position of the track's linked list.
     * @param track Track to add.
     */
    public void addTrack(Track track) {
        if(firstTrack == null) {
            firstTrack = track;
            lastTrack = firstTrack;
        } else {
            track.setPrev(lastTrack);
            lastTrack.setNext(track);
            lastTrack = track;
        }
        trackAmount++;
    }

    /**
     * Removes a track from the track's linked list.
     * @param toDelete Track to remove from the linked list.
     */
    public String removeTrack(Track toDelete) {
        removeTrack(firstTrack, toDelete);
        trackAmount--;
        return "Track " + toDelete.getId() + DELETE_SUCCESS;
    }

    /**
     * Recursively search for the track to be deleted.
     * @param current The track to be compared with the track to be deleted.
     * @param toDelete The track to be deleted.
     */
    private void removeTrack(Track current, Track toDelete) {
        if(current!=null) {
            if(current==toDelete) {
                if(current==firstTrack) {
                    if(trackAmount==1) {
                        firstTrack = null;
                        lastTrack = null;
                    } else {
                        firstTrack = current.getNext();
                    }
                } else if(current==lastTrack) {
                    current.getPrev().setNext(null);
                    lastTrack = current.getPrev();
                } else {
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                }
            } else {
                removeTrack(current.getNext(), toDelete);
            }
        }
    }

    /**
     * Creates a list from existing tracks.
     * @return Returns a list with all the existing tracks.
     */
    public List<Track> tracksToList() {
        return tracksToList(firstTrack);
    }

    /**
     * Auxiliar method that creates a list from the existing tracks.
     * @param current Current track. Receives first track at first.
     * @return Returns a list with all the existing tracks.
     */
    public List<Track> tracksToList(Track current) {
        List<Track> tracks = new ArrayList<>();

        while (current!=null) {
            tracks.add(current);
            current = current.getNext();
        }

        return tracks;
    }

    public String editTrack(Track track, String gate) {
        track.setGate(gate);
        return "Track " + track.getId() + EDIT_SUCCESS;
    }
    
}
