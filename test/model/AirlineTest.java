package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AirlineTest {

	private Airline airline;
	private Flight flight;
	private Advisor advisor;
	private Aircraft plane;
	private Pilot pilot;

	private void setupScenary1() {
		airline = new Airline("Avianca", "");
	}

	private void setupScenary2() {
		airline = new Airline("Avianca", "");

		Pilot p1 = new Pilot("Test1", "TestL1", 1234, airline);
		pilot =  new Pilot("Test2", "TestL2", 4321, airline);

		airline.getPilots().add(p1);
		airline.getPilots().add(pilot);
	}

	private void setupScenary4() {
		airline = new Airline("Avianca", "");

		Advisor a1 = new Advisor("Test1", "TestL1", 1234, airline);
		advisor = new Advisor("Test2", "TestL2", 4321, airline);

		airline.setAssistantRoot(a1);
		a1.setRight(advisor);
		advisor.setParent(a1);
	}

	private void setupScenary6() {
		airline = new Airline("Avianca", "");

		Aircraft plane1 = new Aircraft("AV-2312", 3000, 300, airline);
		plane = new Aircraft("AV-7343", 2000, 200, airline);

		airline.getAircraft().add(plane1);
		airline.getAircraft().add(plane);
		
	}

	private void setupScenary8() {
		airline = new Airline("Avianca", "");

		Flight fly1 = new Flight("K940", "2021-09-06", "23:45", "2021-10-06", "17:00", Places.MADRID, Places.MIAMI,
                null, airline, null);
        flight = new Flight("G940", "2021-09-06", "19:45", "2021-10-06", "10:00", Places.MADRID, Places.MOSCOW,
                null, airline, null);

		airline.getFlights().add(fly1);
		airline.getFlights().add(flight);
	}

	// ------------------------------- Cases

	@Test
	public void createPilot1() {
		setupScenary1();

		int size = airline.getPilots().size();

		airline.addPilot("Test", "TestL", 1234, airline);

		assertEquals(size+1, airline.getPilots().size());

		size = airline.getPilots().size();
		assertEquals("Test", airline.getPilots().get(size-1).getName());
		assertEquals("TestL", airline.getPilots().get(size-1).getLastName());
		assertEquals(1234, airline.getPilots().get(size-1).getId());
		assertEquals(airline, airline.getPilots().get(size-1).getAirline());

	}

	@Test
	public void createPilot2() {
		setupScenary2();

		int size = airline.getPilots().size();

		airline.addPilot("Test", "TestL", 1234, airline);

		assertEquals(size+1, airline.getPilots().size());
		
		size = airline.getPilots().size();
		assertEquals("Test", airline.getPilots().get(size-1).getName());
		assertEquals("TestL", airline.getPilots().get(size-1).getLastName());
		assertEquals(1234, airline.getPilots().get(size-1).getId());
		assertEquals(airline, airline.getPilots().get(size-1).getAirline());
	}

	@Test
	public void createAssistant1() {
		setupScenary1();

		int size = airline.getAdvisor().size();
		Advisor temp = new Advisor("Test", "TestL", 1234, airline);

		airline.addAdvisor(temp);

		assertEquals(size+1, airline.getAdvisor().size());
		
	}

	@Test
	public void createAssistant2() {
		setupScenary4();

		int size = airline.getAdvisor().size();
		Advisor temp = new Advisor("Test", "TestL", 1234, airline);

		airline.addAdvisor(temp);

		assertEquals(size+1, airline.getAdvisor().size());
	}

	@Test
	public void createPlane1() {
		setupScenary1();

		int size = airline.getAircraft().size();
		Aircraft plane = new Aircraft("AV-3468", 1000, 100, airline);
		airline.addAircraft(plane);

		assertEquals(size+1, airline.getAircraft().size());

		size = airline.getAircraft().size();
		assertEquals(1000, airline.getAircraft().get(size-1).getPlaneWeight());
		assertEquals(100, airline.getAircraft().get(size-1).getCapacity());
		assertEquals(airline, airline.getAircraft().get(size-1).getAirline());
	}

	@Test
	public void createPlane2() {
		setupScenary6();

		int size = airline.getAircraft().size();
		Aircraft plane = new Aircraft("AV-3468", 1000, 100, airline);
		airline.addAircraft(plane);

		assertEquals(size+1, airline.getAircraft().size());

		size = airline.getAircraft().size();
		assertEquals(1000, airline.getAircraft().get(size-1).getPlaneWeight());
		assertEquals(100, airline.getAircraft().get(size-1).getCapacity());
		assertEquals(airline, airline.getAircraft().get(size-1).getAirline());
	}

	@Test
	public void createFly1() {
		setupScenary1();

		int size = airline.getFlights().size();
		Aircraft plane = new Aircraft("AV-3468", 1000, 100, airline);
		Track track =new Track(32, "A2");
		airline.addFlight("AV-4122", "2021-10-07", "09:45", "2021-10-07", "12:00", Places.MADRID, Places.TOKIO, track, airline, plane);;

		assertEquals(size+1, airline.getFlights().size());

		size = airline.getFlights().size();
		assertEquals("AV-4122", airline.getFlights().get(size-1).getId());
		assertEquals(Places.TOKIO, airline.getFlights().get(size-1).getDestination());
		assertEquals(Places.MADRID, airline.getFlights().get(size-1).getOrigin());
		assertEquals(track, airline.getFlights().get(size-1).getTrack());
	}

	@Test
	public void createFly2() {
		setupScenary8();

		int size = airline.getFlights().size();
		Aircraft plane = new Aircraft("AV-3468", 1000, 100, airline);
		Track track =new Track(32, "A2");
		airline.addFlight("AV-4122", "2021-10-07", "09:45", "2021-10-07", "12:00", Places.MADRID, Places.TOKIO, track, airline, plane);;

		assertEquals(size+1, airline.getFlights().size());

		size = airline.getFlights().size();
		assertEquals("AV-4122", airline.getFlights().get(size-1).getId());
		assertEquals(Places.TOKIO, airline.getFlights().get(size-1).getDestination());
		assertEquals(Places.MADRID, airline.getFlights().get(size-1).getOrigin());
		assertEquals(track, airline.getFlights().get(size-1).getTrack());
	}

	@Test
	public void eliminatePilot() {
		setupScenary2();

		airline.removePilot(pilot);
		assertEquals(1, airline.getPilots().size());
	}

	@Test
	public void eliminateAssistant() {
		setupScenary4();

		airline.removeAdvisor(advisor);
		assertEquals(1, airline.getAdvisor().size());
	}

	@Test
	public void eliminatePlane() {
		setupScenary6();

		airline.removeAircraft(plane);
		assertEquals(1, airline.getAircraft().size());
	}

	@Test
	public void eliminateFly() {
		setupScenary8();

		airline.removeFlight(flight);
		assertEquals(1, airline.getFlights().size());
	}

}
