package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TripTest {

	private Trip trip;

	private void setupScenary1() {
		Flight fly1 = new Flight("V21", "2021-09-06", "23:45", "2021-10-06", "17:00", Places.MADRID, Places.MILAN, null , null, null);
		Seat seat1 = new Seat(23, 'C', true, 150000);
		Ticket ticket1 = new Ticket(fly1, seat1, 200000);

		Luggage luggage1 = new Luggage(70, 20, 20);
		Luggage luggage2 = new Luggage(70, 20, 20);

		luggage1.setNextLuggage(luggage2);
		luggage2.setPreviousLuggage(luggage1);

		trip = new Trip("T231", ticket1, luggage1, seat1);

		
		
	}

	@Test
	public void testAddLuggage() throws Exception {
		setupScenary1();

		Luggage luggage = new Luggage(120, 40, 20);
		trip.addLuggage(luggage);

		assertEquals(2, trip.getLuggages().size());
	}

}
