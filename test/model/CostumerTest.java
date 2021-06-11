package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CostumerTest {

	private Costumer costumer;
	private Trip trip;

	private void setupScenary1() { // Empty
		costumer = new Costumer(2);

		Seat seat = new Seat(2, (char)'A', true,15000);
		Flight flight = new Flight("V23", "2021-09-06", "23:45", "2021-10-06", "17:00", Places.MADRID, Places.SYDNEY, null, null, null);
		trip = new Trip("T-00234", new Ticket(flight, seat, 150000), seat);
	}

	private void setupScenary2() { // Two rootTicket
		costumer = new Costumer(2);

		Flight fly1 = new Flight("V21", "2021-09-06", "23:45", "2021-10-06", "17:00", Places.MADRID, Places.NEW_YORK, null, null, null);
		Seat seat1 = new Seat(23, 'C', true, 150000);
		Ticket ticket1 = new Ticket(fly1, seat1, 200000);
		Luggage luggage1 = new Luggage(70, 20, 20);

		Trip test1 = new Trip("T231", ticket1, luggage1, seat1);

		Flight fly2 = new Flight("V23", "2021-09-06", "23:45", "2021-10-06", "17:00", Places.MADRID, Places.MILAN, null, null, null);
		Seat seat2 = new Seat(20, 'B', true, 150000);
		Ticket ticket2 = new Ticket(fly2, seat2, 210000);
		Luggage luggage2 = new Luggage(70, 20, 20);

		Trip test2 = new Trip("T123", ticket2, luggage2, seat2);

		costumer.addTrip(test1);
		costumer.addTrip(test2);

		Seat seat = new Seat(2, (char)'A', true,15000);
		Flight flight = new Flight("V23", "2021-09-06", "23:45", "2021-10-06", "17:00", Places.MADRID, Places.SYDNEY, null, null, null);
		trip = new Trip("T-00234", new Ticket(flight, seat, 150000), seat);

	}

	private void setupScenary3() { // Two rootTicket
		costumer = new Costumer(2);

		Flight fly1 = new Flight("V21", "2021-09-06", "23:45", "2021-10-06", "17:00", Places.MADRID, Places.NEW_YORK, null, null, null);
		Seat seat1 = new Seat(23, 'C', true, 150000);
		Ticket ticket1 = new Ticket(fly1, seat1, 200000);
		Luggage luggage1 = new Luggage(70, 20, 20);

		Trip test1 = new Trip("T231", ticket1, luggage1, seat1);

		Flight fly2 = new Flight("V23", "2021-09-06", "23:45", "2021-10-06", "17:00", Places.MADRID, Places.MILAN, null, null, null);
		Seat seat2 = new Seat(20, 'B', true, 150000);
		Ticket ticket2 = new Ticket(fly2, seat2, 210000);
		Luggage luggage2 = new Luggage(70, 20, 20);

		Trip test2 = new Trip("T123", ticket2, luggage2, seat2);

		Seat seat = new Seat(2, (char)'A', true,15000);
		Flight flight = new Flight("V23", "2021-09-06", "23:45", "2021-10-06", "17:00", Places.MADRID, Places.SYDNEY, null, null, null);
		trip = new Trip("T-00234", new Ticket(flight, seat, 150000), seat);

		costumer.addTrip(test1);
		costumer.addTrip(test2);
		costumer.addTrip(trip);

	}

	// ---------------------------- Cases

	@Test
	public void createTicket1() {
		setupScenary1();

		costumer.addTrip(trip);
		assertEquals(1, costumer.getTrips().size());
	}

	@Test
	public void createTicket2() {
		setupScenary2();

		costumer.addTrip(trip);
		assertEquals(3, costumer.getTrips().size());
	}

	@Test
	public void eliminateTicket() {
		setupScenary3();

		costumer.deleteTrip(trip);
		
		assertEquals(2, costumer.getTrips().size());
	}

}
