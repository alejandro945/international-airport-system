package model;

import org.junit.Test;

public class CostumerTest {

	private Costumer costumer;

	private void setupScenary1() { // Empty
		costumer = new Costumer();
	}

	private void setupScenary2() { // Two rootTicket
		costumer = new Costumer();

		Flight fly1 = new Flight("V21", "02/07/21", "4:00", "02/07/21", "14:00", Places.MADRID, Places.CALI, null);
		Seat seat1 = new Seat(23, 'C', true);
		Ticket ticket1 = new Ticket(fly1, seat1, 200000);
		Luggage luggage1 = new Luggage(70, 20, 20);

		Trip test1 = new Trip("T231", ticket1, luggage1, 250000);

		Flight fly2 = new Flight("V23", "02/07/21", "4:00", "02/07/21", "14:00", Places.MADRID, Places.CALI, null, null, null);
		Seat seat2 = new Seat(20, 'B', true);
		Ticket ticket2 = new Ticket(fly2, seat2, 210000);
		Luggage luggage2 = new Luggage(70, 20, 20);

		Trip test2 = new Trip("T123", ticket2, luggage2, 260000);

		costumer.addTrip(test1);
		costumer.addTrip(test2);

	}

	private void setupScenary3() { // Two Baggage in trip
		Flight fly1 = new Flight("V21", "02/07/21", "4:00", "02/07/21", "14:00", Places.MADRID, Places.CALI, null);
		Seat seat1 = new Seat(23, 'C', true);
		Ticket ticket1 = new Ticket(fly1, seat1, 200000);
		Luggage luggage1 = new Luggage(70, 20, 20);
		Trip trip = new Trip("T231", ticket1, luggage1, 250000);

		Luggage luggage2 = new Luggage(70, 20, 20);
		costumer.addLuggage(luggage2, trip);
	}

	// ---------------------------- Cases

	@Test
	public void createTicket1() {
		setupScenary1();

	}

	@Test
	public void createTicket2() {
		setupScenary2();

	}

	@Test
	public void eliminateTicket() {
		setupScenary2();

	}

	@Test
	public void addLuggage() {
		setupScenary3();

	}

}
