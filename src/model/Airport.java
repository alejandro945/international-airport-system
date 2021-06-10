package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Airport implements Serializable {
    private static final long serialVersionUID = 1L;
    public final String SUCCESS = " have been added to our Airport successfully";
    public final String DELETE_SUCCESS = " have been deleted successfully";
    public final String EDIT_SUCCESS = " have been edited  successfully";
    public final String REPEATED = " Opps, register is already in app";
    public final String USER_ERROR = " could not been added to our Airport (Already exists)";
    public final String DELETE_ERROR = " Hey! is your account (Could not been deleted it)";
    public final String OAUTH_MESSAGE = " your account have been rendered successfully";
    private final String SAVE_PATH_FILE = "data/Airport.data";
    private int capital;
    private List<User> users;
    private List<Airline> airlines;
    private List<Flight> flights;
    private Track firstTrack;
    private Costumer logged;
    private User adminLogged;
    private List<Migration> migration;

    public Airport() {
        users = new ArrayList<>();
        airlines = new ArrayList<>();
        flights = new ArrayList<>();
        users.add(new User("Alejandro", "Varela", 1, "alejo8677@gmail.com", "1", UserRole.AIRPORT_ADMIN));
        migration = new ArrayList<>();
        dateRender();
    }

    public void dateRender() {
        File file = new File(SAVE_PATH_FILE);
        if (file.length() > 0) {
            loadData();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_PATH_FILE)));
            users = (List<User>) ois.readObject();
            airlines = (List<Airline>) ois.readObject();
            flights = (List<Flight>) ois.readObject();
            firstTrack = (Track) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
            oos.writeObject(users);
            oos.writeObject(airlines);
            oos.writeObject(flights);
            oos.writeObject(firstTrack);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Flight> getFlights() {
        flights.clear();
        for (Airline airline : airlines) {
            for (Flight flight : airline.getFlights()) {
                flights.add(flight);
            }
        }
        return flights;
    }

    public Track getFirstTrack() {
        return this.firstTrack;
    }

    public void setFirstTrack(Track firstTrack) {
        this.firstTrack = firstTrack;
    }

    public int getTrackAmount() {
        int trackAmount = 0;
        Track render = firstTrack;
        while (render != null) {
            trackAmount++;
            render = render.getNext();
        }
        return trackAmount;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Migration> getMigration() {
        return migration;
    }

    public Costumer getLogged() {
        return logged;
    }

    public void setLogged(Costumer logged) {
        this.logged = logged;
    }

    public int getCapital() {
        return this.capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public List<Airline> getAirlines() {
        return this.airlines;
    }

    public void setAirlines(List<Airline> airlines) {
        this.airlines = airlines;
    }

    public User getAdminLogged() {
        return adminLogged;
    }

    public void setAdminLogged(User adminLogged) {
        this.adminLogged = adminLogged;
    }

    public Migration createMigrationZone(Flight flight) {
        Migration render = null;
        if(!searchMigrationFlight(flight.getId())){
            render = new Migration(flight);
            migration.add(render);
        }
        return render;
    }
    public boolean searchMigrationFlight(String id) {
        boolean render = false;
        for (Migration m : getMigration()) {
            if(m.getFlight().getId()==id){
                render = true;
            }
        }
        return render;
    }
    public String editMigration(Migration m, Flight flight, int a, int c, int w, int mn) {
        m.setFlight(flight);
        m.setapp(a);
        m.setcov(c);
        m.setwant(w);
        m.setmin(mn);
        return "MIGRATION" + EDIT_SUCCESS;
    }

    public String deleteMigration(Migration m) {
        migration.remove(m);
        return "MIGRATION" + DELETE_SUCCESS;
    }

    public String createAirline(String airlineName, String logo) {
        String msg = "";
        if (airlines.size() == 0) {
            Airline newAirline = new Airline(airlineName, logo);
            airlines.add(newAirline);
            msg = airlineName + SUCCESS;
        } else {
            int i = 0;
            Airline newAirline = new Airline(airlineName, logo);
            while (i < airlines.size() && newAirline.compareTo(getAirlines().get(i)) > 0) {
                i++;
            }
            if (!searchBynaryAirline(airlineName)) {
                airlines.add(i, newAirline);
                msg = airlineName + SUCCESS;
            } else {
                msg = REPEATED;
            }
        }
        return msg;
    }

    public boolean searchBynaryAirline(String airlineName) {
        boolean render = false;
        int i = 0;
        int j = airlines.size() - 1;
        while (i <= j && !render) {
            int m = (i + j) / 2;
            if (airlines.get(m).getAirlineName().equals(airlineName)) {
                render = true;
            } else if (airlines.get(m).getAirlineName().compareTo(airlineName) > 0) {
                j = m - 1;
            } else {
                i = m + 1;
            }
        }
        return render;
    }

    // USER-TOWER-MIGRATION
    public String createUser(String name, String lastName, long id, String email, String password, UserRole role) {
        String msg = "";
        if (!searchUserId(id)) {
            User newUser = new User(name, lastName, id, email, password, role);
            users.add(newUser);
            msg = name + SUCCESS;
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
            msg = name + SUCCESS;
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
            msg = name + SUCCESS;
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
            msg = name + SUCCESS;
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

    public void addTrack(Track newTrack) {
        if (firstTrack == null) {
            firstTrack = newTrack;
        } else {
            addTrack(firstTrack, newTrack);
        }
    }

    /**
     * Adds track to the last position of the track's linked list.
     * 
     * @param track Track to add.
     */
    public void addTrack(Track current, Track track) {
        if (current.getNext() == null) {
            current.setNext(track);
            track.setPrev(current);
        } else {
            addTrack(current.getNext(), track);
        }
    }

    /**
     * Removes a track from the track's linked list.
     * 
     * @param toDelete Track to remove from the linked list.
     */
    public String removeTrack(Track toDelete) {
        removeTrack(firstTrack, toDelete);
        return "Track " + toDelete.getId() + DELETE_SUCCESS;
    }

    /**
     * Recursively search for the track to be deleted.
     * 
     * @param current  The track to be compared with the track to be deleted.
     * @param toDelete The track to be deleted.
     */
    private void removeTrack(Track current, Track toDelete) {
        if (current != null) {
            if (current == toDelete) {
                if (current == firstTrack) {
                    firstTrack = (current.getNext() != null) ? current.getNext() : null;
                } else {
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                    setTracksId();
                }
            } else {
                removeTrack(current.getNext(), toDelete);
            }
        }
    }

    private void setTracksId() {
        for (int i = 0; i < tracksToList().size(); i++) {
            tracksToList().get(i).setId(i + 1);
        }
    }

    /**
     * Creates a list from existing tracks.
     * 
     * @return Returns a list with all the existing tracks.
     */
    public List<Track> tracksToList() {
        return tracksToList(firstTrack);
    }

    /**
     * Auxiliar method that creates a list from the existing tracks.
     * 
     * @param current Current track. Receives first track at first.
     * @return Returns a list with all the existing tracks.
     */
    public List<Track> tracksToList(Track current) {
        List<Track> tracks = new ArrayList<>();
        while (current != null) {
            tracks.add(current);
            current = current.getNext();
        }
        return tracks;
    }

    public String editTrack(Track track, String gate, boolean state) {
        track.setGate(gate);
        if (state) {
            track.inMaintenance();
        } else {
            track.setInMaintenance(state);
        }
        return "Track " + track.getId() + EDIT_SUCCESS;
    }

}
