package model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class AirportTest {

    private Airport airport;

    private void setupScenary1() { // Empty Airport
        airport = new Airport();
    }

    private void setupScenary3() { // Two Airline administrators
        airport = new Airport();

        User test1 = new User("Test1", "Test1", 567, "test1@gmail.com", "123", UserRole.values()[1]);
        User test2 = new User("Test2", "Test2", 765, "test2@gmail.com", "123", UserRole.values()[1]);
        User admin = airport.getUsers().get(0);
        airport.setAdminLogged(admin);
        airport.getUsers().add(test1);
        airport.getUsers().add(test2);
    }

    private void setupScenary5() { // Tow Tower Controllers
        airport = new Airport();

        User test1 = new User("Test1", "Test1", 567, "test1@gmail.com", "123", UserRole.values()[3]);
        User test2 = new User("Test2", "Test2", 765, "test2@gmail.com", "123", UserRole.values()[3]);
        User admin = airport.getUsers().get(0);
        airport.setAdminLogged(admin);
        airport.getUsers().add(test1);
        airport.getUsers().add(test2);
    }

    private void setupScenary7() { // Two Migration Agents
        airport = new Airport();

        User test1 = new User("Test1", "Test1", 567, "test1@gmail.com", "123", UserRole.values()[4]);
        User test2 = new User("Test2", "Test2", 765, "test2@gmail.com", "123", UserRole.values()[4]);
        User admin = airport.getUsers().get(0);
        airport.setAdminLogged(admin);
        airport.getUsers().add(test1);
        airport.getUsers().add(test2);
    }

    private void setupScenary9() { // Two Customers
        airport = new Airport();

        Costumer test1 = new Costumer("Test2", "Test2", 765, "test2@gmail.com", "123");
        Costumer test2 = new Costumer("Test1", "Test1", 765, "test1@gmail.com", "123");
        User admin = airport.getUsers().get(0);
        airport.setAdminLogged(admin);
        airport.getUsers().add(test1);
        airport.getUsers().add(test2);
    }

    private void setupScenary11() {
        airport = new Airport();

        Track track1 = new Track(1, "A30");
        Track track2 = new Track(2, "A30");

        airport.setFirstTrack(track1);
        track1.setNext(track2);
    }

    private void setupScenary13() {
        airport = new Airport();
        Airline viva = new Airline("viva", null);
        Airline avianca = new Airline("avianca", null);

        airport.getAirlines().add(viva);
        airport.getAirlines().add(avianca);

        Flight fly1 = new Flight("K940", "2021-09-06", "23:45", "2021-10-06", "17:00", Places.MADRID, Places.MIAMI,
                null, avianca, null);
        Flight fly2 = new Flight("G940", "2021-09-06", "19:45", "2021-10-06", "10:00", Places.MADRID, Places.MOSCOW,
                null, viva, null);

        viva.getFlights().add(fly1);
        avianca.getFlights().add(fly2);

    }

    // ----------------------------------------------------------------- cases

    @Test
    public void createMainUser() {
        setupScenary1();

        int previousSize = airport.getUsers().size();

        airport.createUser("test", "testL", 1234, "test@test.com", "1234", UserRole.AIRPORT_ADMIN);
        int last = airport.getUsers().size() - 1;
        User temp = airport.getUsers().get(last);

        assertEquals("test", temp.getName());
        assertEquals("testL", temp.getLastName());
        assertEquals(1234, temp.getId());
        assertEquals("test@test.com", temp.getEmail());
        assertEquals("1234", temp.getPassword());
        assertEquals(UserRole.AIRPORT_ADMIN, temp.getRole());

        assertEquals(previousSize + 1, airport.getUsers().size());

    }

    @Test
    public void createAirlineManager1() {
        setupScenary1();

        int previousSize = airport.getUsers().size();

        Airline testAir = new Airline("Test Air", null);
        airport.createUser("test", "testL", 1234, "test@test.com", "1234", testAir);
        int last = airport.getUsers().size() - 1;
        User temp = airport.getUsers().get(last);

        assertEquals("test", temp.getName());
        assertEquals("testL", temp.getLastName());
        assertEquals(1234, temp.getId());
        assertEquals("test@test.com", temp.getEmail());
        assertEquals("1234", temp.getPassword());
        assertEquals(UserRole.AIRLINE_ADMIN, temp.getRole());
        AirlineUser tem = (AirlineUser) temp;
        assertEquals(testAir, tem.getAirline());

        assertEquals(previousSize + 1, airport.getUsers().size());
    }

    @Test
    public void createAirlineManager2() {
        setupScenary3();

        int previousSize = airport.getUsers().size();

        Airline testAir = new Airline("Test Air", null);
        airport.createUser("test", "testL", 1234, "test@test.com", "1234", testAir);
        int last = airport.getUsers().size() - 1;
        User temp = airport.getUsers().get(last);

        assertEquals("test", temp.getName());
        assertEquals("testL", temp.getLastName());
        assertEquals(1234, temp.getId());
        assertEquals("test@test.com", temp.getEmail());
        assertEquals("1234", temp.getPassword());
        assertEquals(UserRole.AIRLINE_ADMIN, temp.getRole());
        AirlineUser tem = (AirlineUser) temp;
        assertEquals(testAir, tem.getAirline());

        assertEquals(previousSize + 1, airport.getUsers().size());
    }

    @Test
    public void createTowerCntrl1() {
        setupScenary1();

        int previousSize = airport.getUsers().size();

        airport.createUser("test", "testL", 1234, "test@test.com", "1234", UserRole.TOWER_SUPERVISOR);
        int last = airport.getUsers().size() - 1;
        User temp = airport.getUsers().get(last);

        assertEquals("test", temp.getName());
        assertEquals("testL", temp.getLastName());
        assertEquals(1234, temp.getId());
        assertEquals("test@test.com", temp.getEmail());
        assertEquals("1234", temp.getPassword());
        assertEquals(UserRole.TOWER_SUPERVISOR, temp.getRole());

        assertEquals(previousSize + 1, airport.getUsers().size());

    }

    @Test
    public void createTowerCntrl2() {
        setupScenary5();

        int previousSize = airport.getUsers().size();

        airport.createUser("test", "testL", 1234, "test@test.com", "1234", UserRole.TOWER_SUPERVISOR);
        int last = airport.getUsers().size() - 1;
        User temp = airport.getUsers().get(last);

        assertEquals("test", temp.getName());
        assertEquals("testL", temp.getLastName());
        assertEquals(1234, temp.getId());
        assertEquals("test@test.com", temp.getEmail());
        assertEquals("1234", temp.getPassword());
        assertEquals(UserRole.TOWER_SUPERVISOR, temp.getRole());

        assertEquals(previousSize + 1, airport.getUsers().size());

    }

    @Test
    public void createMigrationAgent1() {
        setupScenary1();

        int previousSize = airport.getUsers().size();

        airport.createUser("test", "testL", 1234, "test@test.com", "1234", UserRole.MIGRATION_AGENT);
        int last = airport.getUsers().size() - 1;
        User temp = airport.getUsers().get(last);

        assertEquals("test", temp.getName());
        assertEquals("testL", temp.getLastName());
        assertEquals(1234, temp.getId());
        assertEquals("test@test.com", temp.getEmail());
        assertEquals("1234", temp.getPassword());
        assertEquals(UserRole.MIGRATION_AGENT, temp.getRole());

        assertEquals(previousSize + 1, airport.getUsers().size());

    }

    @Test
    public void createMigrationAgent2() {
        setupScenary7();

        int previousSize = airport.getUsers().size();

        airport.createUser("test", "testL", 1234, "test@test.com", "1234", UserRole.MIGRATION_AGENT);
        int last = airport.getUsers().size() - 1;
        User temp = airport.getUsers().get(last);

        assertEquals("test", temp.getName());
        assertEquals("testL", temp.getLastName());
        assertEquals(1234, temp.getId());
        assertEquals("test@test.com", temp.getEmail());
        assertEquals("1234", temp.getPassword());
        assertEquals(UserRole.MIGRATION_AGENT, temp.getRole());

        assertEquals(previousSize + 1, airport.getUsers().size());

    }

    @Test
    public void eliminateAirlineManager() {
        setupScenary3();
        int initialSize = airport.getUsers().size();
        int lastPos = initialSize - 1;
        User user = airport.getUsers().get(lastPos);

        airport.deleteUser(user);

        assertEquals(initialSize - 1, airport.getUsers().size());

    }

    @Test
    public void eliminateTowerCntrl() {
        setupScenary5();

        int initialSize = airport.getUsers().size();
        int lastPos = initialSize - 1;
        User user = airport.getUsers().get(lastPos);

        airport.deleteUser(user);

        assertEquals(initialSize - 1, airport.getUsers().size());
    }

    @Test
    public void eliminateMigrationAgent() {
        setupScenary7();

        int initialSize = airport.getUsers().size();
        int lastPos = initialSize - 1;
        User user = airport.getUsers().get(lastPos);

        airport.deleteUser(user);

        assertEquals(initialSize - 1, airport.getUsers().size());
    }

    @Test
    public void createClient1() {
        setupScenary1();

        int previousSize = airport.getUsers().size();

        airport.createUser("test", "testL", 1234, "test@test.com", "1234", UserRole.COSTUMER_USER);
        int last = airport.getUsers().size() - 1;
        User temp = airport.getUsers().get(last);

        assertEquals("test", temp.getName());
        assertEquals("testL", temp.getLastName());
        assertEquals(1234, temp.getId());
        assertEquals("test@test.com", temp.getEmail());
        assertEquals("1234", temp.getPassword());
        assertEquals(UserRole.COSTUMER_USER, temp.getRole());

        assertEquals(previousSize + 1, airport.getUsers().size());

    }

    @Test
    public void createClient2() {
        setupScenary9();

        int previousSize = airport.getUsers().size();

        airport.createUser("test", "testL", 1234, "test@test.com", "1234", UserRole.COSTUMER_USER);
        int last = airport.getUsers().size() - 1;
        User temp = airport.getUsers().get(last);

        assertEquals("test", temp.getName());
        assertEquals("testL", temp.getLastName());
        assertEquals(1234, temp.getId());
        assertEquals("test@test.com", temp.getEmail());
        assertEquals("1234", temp.getPassword());
        assertEquals(UserRole.COSTUMER_USER, temp.getRole());

        assertEquals(previousSize + 1, airport.getUsers().size());

    }

    @Test
    public void eliminateClient() {
        setupScenary9();

        int initialSize = airport.getUsers().size();
        int lastPos = initialSize - 1;
        User user = airport.getUsers().get(lastPos);

        airport.deleteUser(user);

        assertEquals(initialSize - 1, airport.getUsers().size());
    }

    @Test
    public void maintenance() {
        setupScenary11();
        List<Track> tempList = airport.tracksToList();

        tempList.get(tempList.size() - 1).inMaintenance();

        assertEquals(true, tempList.get(tempList.size() - 1).isInMaintenance());

    }

    @Test
    public void getActualFlys() {
        setupScenary13();

        assertEquals(2, airport.getFlights().size());

    }
}