package model;

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

        airport.getUsers().add(test1);
        airport.getUsers().add(test2);
    }

    private void setupScenary5() { // Tow Tower Controllers
        airport = new Airport();

        User test1 = new User("Test1", "Test1", 567, "test1@gmail.com", "123", UserRole.values()[3]);
        User test2 = new User("Test2", "Test2", 765, "test2@gmail.com", "123", UserRole.values()[3]);

        airport.getUsers().add(test1);
        airport.getUsers().add(test2);
    }

    private void setupScenary7() { // Two Migration Agents
        airport = new Airport();

        User test1 = new User("Test1", "Test1", 567, "test1@gmail.com", "123", UserRole.values()[4]);
        User test2 = new User("Test2", "Test2", 765, "test2@gmail.com", "123", UserRole.values()[4]);

        airport.getUsers().add(test1);
        airport.getUsers().add(test2);
    }

    private void setupScenary9() { // Two Customers
        airport = new Airport();

        Costumer test1 = new Costumer("Test2", "Test2", 765, "test2@gmail.com", "123");
        Costumer test2 = new Costumer("Test1", "Test1", 765, "test1@gmail.com", "123");

        airport.getUsers().add(test1);
        airport.getUsers().add(test2);
    }

    private void setupScenary10() {
        airport = new Airport();

        Flight fly1 = new Flight("V21", "02/07/21", "4:00", "02/07/21", "14:00", Places.MADRID, Places.CALI, null, null, null);
       // Flight fly2 = new Flight("V12", "10/07/21", "3:00", "02/07/21", "14:00", Places.MADRID, Places.MOSCOW, null, null, null);

        Track track1 = new Track(1, "A30");
        Track track2 = new Track(2, "A30");

        airport.getFlights().add(fly1);
        airport.getFlights().add(fly2);

        airport.setFirstTrack(track1);
        track1.setNext(track2);
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

       // Flight fly1 = new Flight("V21", "02/07/21", "4:00", "02/07/21", "14:00", Places.MADRID, Places.CALI, null, null, null);
     //   Flight fly2 = new Flight("V12", "10/07/21", "3:00", "02/07/21", "14:00", Places.MADRID, Places.MOSCOW, null, null, null);

        airport.getFlights().add(fly1);
        airport.getFlights().add(fly2);
    }

    private void setupScenary14() {
        airport = new Airport();

        Costumer test2 = new Costumer("Test2", "Test2", 765, "test2@gmail.com", "123");
        Costumer test1 = new Costumer("Test1", "Test1", 765, "test1@gmail.com", "123");
        Costumer test3 = new Costumer("Test3", "Test3", 125, "test3@gmail.com", "123");

        airport.getUsers().add(test1);
        airport.getUsers().add(test2);
        airport.getUsers().add(test3);
    }

    // ----------------------------------------------------------------- cases

    @Test
    public void createMainUser() {
        setupScenary1();

    }

    @Test
    public void createAirlineManager1() {
        setupScenary1();

    }

    @Test
    public void createAirlineManager2() {
        setupScenary3();

    }

    @Test
    public void createTowerCntrl1() {
        setupScenary1();

    }

    @Test
    public void createTowerCntrl2() {
        setupScenary5();

    }

    @Test
    public void createMigrationAgent1() {
        setupScenary1();

    }

    @Test
    public void createMigrationAgent2() {
        setupScenary7();

    }

    @Test
    public void eliminateAirlineManager() {
        setupScenary3();

    }

    @Test
    public void eliminateTowerCntrl() {
        setupScenary5();

    }

    @Test
    public void eliminateMigrationAgent() {
        setupScenary7();

    }

    @Test
    public void createClient1() {
        setupScenary1();

    }

    @Test
    public void createClient2() {
        setupScenary9();

    }

    @Test
    public void eliminateClient() {
        setupScenary9();

    }

    @Test
    public void assignFlyTrack() {
        setupScenary10();

    }

    @Test
    public void maintenance() {
        setupScenary11();

    }

    @Test
    public void getActualFlys() {
        setupScenary13();

    }

    @Test
    public void validateClientPass() {
        setupScenary14();

    }
}