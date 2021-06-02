package model;

import org.junit.Test;

public class AirportTest {

    private Airport airport;

    private void setupScenary1() { // Empty Airport
        airport = new Airport();
    }

    private void setupScenary2() {
        airport = new Airport();


    }

    private void setupScenary3() { // Two Airline administrators
        airport = new Airport();

        User test1 =  new User("Test1", "Test1", 567, "test1@gmail.com", "123", UserRole.values()[1]);
        User test2 =  new User("Test2", "Test2", 765, "test2@gmail.com", "123", UserRole.values()[1]);
    }

    private void setupScenary4() {
        airport = new Airport();
    }

    private void setupScenary5() { // Tow Tower Controllers
        airport = new Airport();

        User test1 =  new User("Test1", "Test1", 567, "test1@gmail.com", "123", UserRole.values()[3]);
        User test2 =  new User("Test2", "Test2", 765, "test2@gmail.com", "123", UserRole.values()[3]);
    }

    private void setupScenary6() {
        airport = new Airport();
    }

    private void setupScenary7() { // Two Migration Agents
        airport = new Airport();

        User test1 =  new User("Test1", "Test1", 567, "test1@gmail.com", "123", UserRole.values()[4]);
        User test2 =  new User("Test2", "Test2", 765, "test2@gmail.com", "123", UserRole.values()[4]);
    }

    private void setupScenary8() {
        airport = new Airport();
    }

    private void setupScenary9() { // Two Customers
        airport = new Airport();

        User test1 =  new User("Test1", "Test1", 567, "test1@gmail.com", "123", UserRole.values()[2]);
        User test2 =  new User("Test2", "Test2", 765, "test2@gmail.com", "123", UserRole.values()[2]);
    }

    private void setupScenary10() {
        airport = new Airport();

        Flight fly1 = new Flight("02/07/21", "4:00", "02/07/21", "14:00", Places.MADRID, Places.CALI);
        Flight fly2 = new Flight("10/07/21", "3:00", "02/07/21", "14:00", Places.MADRID, Places.MOSCOW);

        Track track1 = new Track(1, "A30");
        Track track2 = new Track(2, "A30");

        airport.getFlights().add(fly1);
        airport.getFlights().add(fly2);

        airport.setFirstTrack(track1);
        track1.setNext(track2);
    }

    private void setupScenary11() {
        airport = new Airport();
    }

    private void setupScenary12() {
        airport = new Airport();
    }

    private void setupScenary13() {
        airport = new Airport();
    }

    private void setupScenary14() {
        airport = new Airport();
    }

    // ----------------------------------------------------------------- cases

    @Test
    public void createMainUser() {

    }

    @Test
    public void createAirlineManager1() {

    }

    @Test
    public void createAirlineManager2() {

    }

    @Test
    public void createTowerCntrl1() {

    }

    @Test
    public void createTowerCntrl2() {

    }

    @Test
    public void createMigrationAgent1() {

    }

    @Test
    public void createMigrationAgent2() {

    }

    @Test
    public void eliminateAirlineManager() {

    }

    @Test
    public void eliminateTowerCntrl() {

    }

    @Test
    public void eliminateMigrationAgent() {

    }

    @Test
    public void createClient1() {

    }

    @Test
    public void createClient2() {

    }

    @Test
    public void eliminateClient() {

    }

    @Test
    public void assignFlyTrack() {

    }

    @Test
    public void maintenance() {

    }

    @Test
    public void getActualFlys() {

    }

    @Test
    public void validateClientPass() {

    }
}