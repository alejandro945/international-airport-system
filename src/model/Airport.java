package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Airport implements Serializable {

    private static final long serialVersionUID = 1L;
    public final String FILE_SEPARATOR = ";";
    public final String SUCCESS = " have been added to our Airport successfully";
    public final String DELETE_SUCCESS = " have been deleted successfully";
    public final String EDIT_SUCCESS = " have been edited  successfully";
    public final String REPEATED = " Opps, register is already in app";
    public final String USER_ERROR = " could not been added to our Airport (Already exists)";
    public final String DELETE_ERROR = " Hey! is your account (Could not been deleted it)";
    public final String OAUTH_MESSAGE = " your account have been rendered successfully";
    private final String SAVE_PATH_FILE = "data/Airport.data";
    private List<User> users;
    private List<Airline> airlines;
    private List<Flight> flights;
    private Track firstTrack; // Linked List
    private AirlineUser airlineLogged;
    private Costumer logged;
    private User adminLogged;
    private List<Migration> migration;

    /**
     * The constructor method of an Airport Object(main class of the model)<br>
     */
    public Airport() {
        users = new ArrayList<>();
        airlines = new ArrayList<>();
        flights = new ArrayList<>();
        users.add(new User("Alejandro", "Varela", 1, "alejo8677@gmail.com", "1", UserRole.AIRPORT_ADMIN));
        migration = new ArrayList<>();
        dateRender();
    }

    /**
     * Verify that there is indeed a binary file to deserialize and start with the
     * application persistence.
     */
    public void dateRender() {
        File file = new File(SAVE_PATH_FILE);
        if (file.length() > 0) {
            loadData();
        }
    }

    /**
     * Loads all program data by deserializing primitive data and objects from file.
     */
    @SuppressWarnings("unchecked")
    public void loadData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(SAVE_PATH_FILE)));
            users = (List<User>) ois.readObject();
            airlines = (List<Airline>) ois.readObject();
            flights = (List<Flight>) ois.readObject();
            firstTrack = (Track) ois.readObject();
            migration = (List<Migration>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves all program data by writing primitive data types and graphs of Java
     * objects to an OutputStream.
     */
    public void saveData() {
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
            oos.writeObject(users);
            oos.writeObject(airlines);
            oos.writeObject(flights);
            oos.writeObject(firstTrack);
            oos.writeObject(migration);
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

    /**
     * Gets the munber of tracks in App.
     */
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

    public User getAdminLogged() {
        return adminLogged;
    }

    public void setAdminLogged(User adminLogged) {
        this.adminLogged = adminLogged;
    }

    public AirlineUser getAirlineLogged() {
        return this.airlineLogged;
    }

    public void setAirlineLogged(AirlineUser airlineLogged) {
        this.airlineLogged = airlineLogged;
    }

    public List<Airline> getAirlines() {
        return this.airlines;
    }

    public void setAirlines(List<Airline> airlines) {
        this.airlines = airlines;
    }

    /**
     * Import airlines data from file.
     * 
     * @param fileName Name of the file containing all the data.
     * @throws IOException
     */
    public void importDataAirlines(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            String airlineName = parts[1];
            String logo = parts[2];
            line = br.readLine();
            createAirline(airlineName, logo);
        }
        br.close();
    }

    /**
     * Export airlines data to a file.
     * 
     * @param fileName Name of the file to be exported with the data.
     * @throws FileNotFoundException
     */
    public void exportDataAirlines(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("AIRPORT SYSTEM AIRLINES REPORT");
        pw.println("Airline;Icon Path");
        for (int i = 0; i < airlines.size(); i++) {
            Airline a = airlines.get(i);
            pw.println(a.getAirlineName() + FILE_SEPARATOR + a.getIcon());
        }
        pw.close();
    }

    /**
     * Import tracks data to a file.
     * 
     * @param fileName Name of the file containing all the data.
     * @throws IOException
     */
    public void importDataTracks(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            int id = Integer.parseInt(parts[1]);
            String gate = parts[2];
            line = br.readLine();
            addTrack(new Track(id, gate));
        }
        br.close();
    }

    /**
     * Export all tracks data to a file.
     * 
     * @param fileName Name of the file to be exported with the data.
     * @throws FileNotFoundException
     */
    public void exportDataTracks(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("AIRPORT SYSTEM TRACKS REPORT");
        pw.println("Code;Gate;Status");
        for (int i = 0; i < getTrackAmount(); i++) {
            Track t = tracksToList().get(i);
            pw.println(t.getId() + FILE_SEPARATOR + t.getGate() + FILE_SEPARATOR + t.getState());
        }
        pw.close();
    }

    /**
     * Import migration data to a file.
     * 
     * @param fileName Name of the file containing all the data.
     * @throws IOException
     */
    public void importDataMigration(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            Flight flight = new Flight(parts[1]);
            int approved = Integer.parseInt(parts[2]);
            int covid = Integer.parseInt(parts[3]);
            int wanted = Integer.parseInt(parts[4]);
            int minor = Integer.parseInt(parts[5]);
            line = br.readLine();
            Migration m = createMigrationZone(flight);
            m.setapp(approved);
            m.setcov(covid);
            m.setwant(wanted);
            m.setmin(minor);
            m.airportCharges();
        }
        br.close();
    }

    /**
     * Export all migration data to a file.
     * 
     * @param fileName Name of the file to be exported with the data.
     * @throws FileNotFoundException
     */
    public void exportDataMigration(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("AIRPORT SYSTEM MIGRATION REPORT");
        pw.println("Code Flight;Approved;Covid;Wanted;Minor;Capital");
        for (int i = 0; i < migration.size(); i++) {
            Migration m = migration.get(i);
            pw.println(m.getId() + FILE_SEPARATOR + m.getApproved() + FILE_SEPARATOR + m.getCovid() + FILE_SEPARATOR
                    + m.getWanted() + FILE_SEPARATOR + m.getMinor() + FILE_SEPARATOR + m.getCapital());
        }
        pw.close();
    }

    /**
     * Import users data to a file.
     * 
     * @param fileName Name of the file containing all the data.
     * @throws IOException
     */
    public void importDataUsers(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            String[] parts = line.split(FILE_SEPARATOR);
            String name = parts[1];
            String lastName = parts[2];
            long id = Long.parseLong(parts[3]);
            String email = parts[4];
            String password = parts[3];
            UserRole role = UserRole.valueOf(parts[5]);
            Airline airline = searchBynaryAirline(parts[6]);
            line = br.readLine();
            switch (role) {
                case COSTUMER_USER:
                    createUser(name, lastName, id, email, password);
                    break;
                case AIRLINE_ADMIN:
                    createUser(name, lastName, id, email, password, airline);
                    break;
                default:
                    createUser(name, lastName, id, email, password, role);
                    break;
            }
        }
        br.close();
    }

    /**
     * Export all users data to a file.
     * 
     * @param fileName Name of the file to be exported with the data.
     * @throws FileNotFoundException
     */
    public void exportDataUsers(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("AIRPORT SYSTEM USERS REPORT");
        pw.println("Name;Last Name;Id;Email;Role;Airline");
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            String msg = (u.getName() + FILE_SEPARATOR + u.getLastName() + FILE_SEPARATOR + u.getId() + FILE_SEPARATOR
                    + u.getEmail() + FILE_SEPARATOR + u.getRole().name());
            if (u instanceof AirlineUser) {
                AirlineUser a = (AirlineUser) u;
                msg += FILE_SEPARATOR + a.getAirline();
            }
            pw.println(msg);
        }
        pw.close();
    }

    /**
     * Create an assigned migration instance and binary looking for a flight within
     * the airport to assign it with its respective clients.<br>
     * <b> Post: <b> dds a Migration Zone in Airport. <br>
     * 
     * @param flight != null
     * @return A migration object that will be rendered later in App.
     */
    public Migration createMigrationZone(Flight flight) {
        Migration render = null;
        if (!searchMigrationFlight(flight.getId())) {
            render = new Migration(flight);
            migration.add(render);
        }
        return render;
    }

    /**
     * Obtains the expenses of the airport that must be delivered to the migration
     * area depending on the clients they have retained on each flight.
     * 
     * @return Suppliers cost to pay.
     */
    public int getExpenses() {
        int i = 0;
        int amount = 0;
        while (i < migration.size()) {
            amount += migration.get(i).getCapital();
            i++;
        }
        return amount;
    }

    /**
     * Look for a flight within all immigration zones to verify that it has not been
     * reviewed before.
     * 
     * @param id != String or even null.
     * @return a boolean condition that will be used later in Thread.
     */
    public boolean searchMigrationFlight(String id) {
        boolean render = false;
        for (Migration m : getMigration()) {
            if (m.getFlight().getId() == id) {
                render = true;
            }
        }
        return render;
    }

    /**
     * Deletes a Migration Instance in Airport destined list.
     * @param m != null
     * @param flight != null
     * @param a != String or null
     * @param c != String or null
     * @param w != String or null
     * @param mn != String or null
     * @return An String message for notification purposes.
     */
    public String editMigration(Migration m, Flight flight, int a, int c, int w, int mn) {
        m.setFlight(flight);
        m.setapp(a);
        m.setcov(c);
        m.setwant(w);
        m.setmin(mn);
        return "MIGRATION" + EDIT_SUCCESS;
    }

    /**
     * Deletes a Migration Instance in Airport destined list.
     * 
     * @param m != null
     * @return An String message for notification purposes.
     */
    public String deleteMigration(Migration m) {
        migration.remove(m);
        return "MIGRATION" + DELETE_SUCCESS;
    }

    /**
     * Creates an airline with a name and a logo.
     * 
     * @param airlineName String representing the airline name.
     * @param logo        String containing the logo image path.
     * @return Returns string with the result of the operation.
     */
    public String createAirline(String airlineName, String logo) {
        String msg = "";
        if (airlines.size() == 0) {
            Airline newAirline = new Airline(airlineName, logo);
            airlines.add(newAirline);
            msg = airlineName + SUCCESS;
        } else {
            int i = 0;
            Airline newAirline = new Airline(airlineName, logo);
            while (i < airlines.size() && newAirline.compareTo(airlines.get(i)) > 0) {
                i++;
            }
            if (searchBynaryAirline(airlineName) == null) {
                airlines.add(i, newAirline);
                msg = airlineName + SUCCESS;
            } else {
                msg = REPEATED;
            }
        }
        return msg;
    }

    /**
     * Binary search for an airline based on its name.
     * @param airlineName String containing the name of the airline to be searched.
     * @return Returns the airline if found. Returns null if not found.
     */
    public Airline searchBynaryAirline(String airlineName) {
        Airline render = null;
        int i = 0;
        int j = airlines.size() - 1;
        while (i <= j && render == null) {
            int m = (i + j) / 2;
            if (airlines.get(m).getAirlineName().equalsIgnoreCase(airlineName)) {
                render = airlines.get(m);
            } else if (airlines.get(m).getAirlineName().compareTo(airlineName) > 0) {
                j = m - 1;
            } else {
                i = m + 1;
            }
        }
        return render;
    }

    /**
     * Binary search for a flight based on its code.
     * @param code String containing the code of the flight to be searched.
     * @return Returns the flight if found. Returns null if not found.
     */
    public Flight searchBynaryFlight(String code) {
        Flight render = null;
        int i = 0;
        int j = getFlights().size() - 1;
        while (i <= j && render != null) {
            int m = (i + j) / 2;
            if (getFlights().get(m).getId().equals(code)) {
                render = getFlights().get(m);
            } else if (getFlights().get(m).getId().compareTo(code) > 0) {
                j = m - 1;
            } else {
                i = m + 1;
            }
        }
        return render;
    }

    /**
     * Creates a new user and adds it to the airport users list.
     * @param name String containing the user's name.
     * @param lastName String containing the user's last name.
     * @param id Long type variable containing the user's identification number.
     * @param email String containing the user's email address.
     * @param password String containing the user's password.
     * @param role User role from enumeration.
     * @return Returns string with the result of the operation.
     */
    public String createUser(String name, String lastName, long id, String email, String password, UserRole role) {
        String msg = "";
        if (!searchUserId(id)) {
            User newUser = new User(name, lastName, id, email, password, role);
            users.add(newUser);
            msg = name + SUCCESS;
        } else {
            msg = name + USER_ERROR;
        }
        return msg;
    }

    /**
     * Edits an existing user's data.
     * @param user User to be edited.
     * @param name String containing the user's name.
     * @param lastName String containing the user's last name.
     * @param id Long type variable containing the user's identification number.
     * @param email String containing the user's email address.
     * @param password String containing the user's password.
     * @param role User role from enumeration.
     * @return Returns string with the result of the operation.
     */
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

    /**
     * Creates a new user and adds it to the airport users list.
     * @param name String containing the user's name.
     * @param lastName String containing the user's last name.
     * @param id Long type variable containing the user's identification number.
     * @param email String containing the user's email address.
     * @param password String containing the user's password.
     * @return Returns string with the result of the operation.
     */
    public String createUser(String name, String lastName, long id, String email, String password, Airline airline) {
        String msg = "";
        if (!searchUserId(id)) {
            AirlineUser newAirlineUser = new AirlineUser(name, lastName, id, email, password, airline);
            users.add(newAirlineUser);
            msg = name + SUCCESS;
        } else {
            msg = name + USER_ERROR;
        }
        return msg;
    }

    /**
     * Edits an existing user's data.
     * @param user User to be edited.
     * @param name String containing the user's name.
     * @param lastName String containing the user's last name.
     * @param id Long type variable containing the user's identification number.
     * @param email String containing the user's email address.
     * @param password String containing the user's password.
     * @return Returns string with the result of the operation.
     */
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

    /**
     * Creates a new normal (No defined user type) user and adds it to the airport users list.
     * @param name String containing the user's name.
     * @param lastName String containing the user's last name.
     * @param id Long type variable containing the user's identification number.
     * @param email String containing the user's email address.
     * @param password String containing the user's password.
     * @return Returns string with the result of the operation.
     */
    public String createUser(String name, String lastName, long id, String email, String password) {
        String msg = "";
        if (!searchUserId(id)) {
            Costumer newCostumer = new Costumer(name, lastName, id, email, password);
            users.add(newCostumer);
            msg = name + SUCCESS;
        } else {
            msg = name + USER_ERROR;
        }
        return msg;
    }

    /**
     * Creates a new normal (No defined user type) user and adds it to the airport users list.
     * @param name String containing the user's name.
     * @param lastName String containing the user's last name.
     * @param id Long type variable containing the user's identification number.
     * @return Returns string with the result of the operation.
     */
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

    /**
     * Deletes a user from the users list.
     * 
     * @param user User to be removed.
     * @return Returns a String with the result of the operation.
     */
    public String deleteUser(User user) {
        String msg = "";
        if (getAdminLogged().getId() != user.getId()) {
            users.remove(user);
            msg = user.getName() + DELETE_SUCCESS;
        } else {
            msg = DELETE_ERROR;
        }
        return msg;
    }

    /**
     * Searches if a new identification number already exists.
     * 
     * @param newId Identification number entered by the user.
     * @return Returns true if the id already exists. Else returns false.
     */
    public boolean searchUserId(long newId) {
        boolean render = false;
        for (int i = 0; i < users.size() && !render; i++) {
            if (newId == users.get(i).getId()) {
                render = true;
            }
        }
        return render;
    }

    /**
     * Searches for a user based on an identification number.
     * 
     * @param id Identification number to be searched.
     * @return Returns the user if founded. Else returns null.
     */
    public User searchUser(long id) {
        User user = null;
        for (User u : users) {
            if (u.getId() == id) {
                user = u;
            }
        }
        return user;
    }

    /**
     * Verifies the id and the password entered by the user and sets the active user
     * depending on the user type.
     * 
     * @param id       Identification number entered by the user.
     * @param password Password entered by the user.
     * @return Returns true if the credentials are correct. Else returns false.
     */
    public boolean userVerification(long id, String password) {
        boolean found = false;
        for (int i = 0; i < users.size() && !found; i++) {
            if (users.get(i).getId() == (id) && users.get(i).getPassword().equals(password)) {
                if (users.get(i) instanceof Costumer) {
                    setLogged((Costumer) users.get(i));
                } else if (users.get(i) instanceof AirlineUser) {
                    setAirlineLogged((AirlineUser) users.get(i));
                } else {
                    setAdminLogged(users.get(i));
                }
                found = true;
            }
        }
        return found;
    }

    /**
     * Adds a track from the tracks linked list.
     * 
     * @param newTrack Track to be added.
     */
    public void addTrack(Track newTrack) {
        if (firstTrack == null) {
            firstTrack = newTrack;
        } else {
            addTrack(firstTrack, newTrack);
        }
    }

    /**
     * Adds track to the last position of the tracks linked list.
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
     *
     * @return
     */
    public List<Track> getActiveTracks() {
        List<Track> actives = new ArrayList<>();
        for (Track t : tracksToList()) {
            if (t.getState().equalsIgnoreCase("ACTIVE")) {
                actives.add(t);
            }
        }
        return actives;
    }

    /**
     * Removes a track from the tracks linked list.
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

    /**
     * Sets the id for each track.
     */
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

    /**
     * Edits the information of a track.
     * 
     * @param track Track to be edited.
     * @param gate  Updated gate information.
     * @param state Updated state information.
     * @return Returns String with the result of the edit.
     */
    public String editTrack(Track track, String gate, boolean state) {
        track.setGate(gate);
        if (state) {
            track.inMaintenance();
        } else {
            track.setInMaintenance(state);
        }
        return "Track " + track.getId() + EDIT_SUCCESS;
    }

    /**
     * Orders users list.
     * @return Returns an order list of users.
     */
    public List<User> orderUseList() {

        List<User> list = users;

        for (int i = 0; i < list.size(); i++) {
            User minor = list.get(i);
            int pos = i;

            for (int j = i + 1; j < list.size(); j++) {
                if (minor.compareTo(list.get(j)) > 0) {
                    minor = list.get(j);
                    pos = j;
                } else if (minor.compareTo(list.get(j)) == 0) {
                    if (minor.compareByRole(list.get(j)) > 0) {
                        minor = list.get(j);
                        pos = j;
                    }
                }
            }
            User temp = list.get(i);
            list.set(i, minor);
            list.set(pos, temp);
        }

        return list;
    }

    /**
     * Orders flights list.
     * @return Returns ordered list of flights.
     */
    public List<Flight> flightList() {
        boolean inserted = false;

        for (int i = 1; i < flights.size(); i++) {
            for (int j = i; j > 0 && !inserted; j--) {
                if (flights.get(j - 1).compareTo(flights.get(j)) == 0) {
                    if (flights.get(j - 1).compareById(flights.get(j)) < 0) {
                        Flight temp = flights.get(j);
                        flights.set(j, flights.get(j - 1));
                        flights.set(j - 1, temp);
                        inserted = true;
                    }
                }
            }
            if (inserted == false) {
                for (int j = i; j > 0 && flights.get(j - 1).compareTo(flights.get(j)) < 0; j--) {
                    Flight temp = flights.get(j);
                    flights.set(j, flights.get(j - 1));
                    flights.set(j - 1, temp);
                    inserted = true;
                }
            }
        }

        return flights;
    }

}
