package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import exeption.CovidException;
import exeption.MinorException;
import exeption.WantedException;

public class MigrationTest {

	Migration migration;

	private void setupScenary14() {
		Airline avianca = new Airline("Avianca", "");
		Flight fly = new Flight("K940", "2021-09-06", "23:45", "2021-10-06", "17:00", Places.MADRID, Places.MIAMI,
                null, avianca, null);

        migration = new Migration(fly);
    }

	// ----------------------------------------------------------------- cases

	@Test
    public void validateClientPass2() {
        setupScenary14();

        Costumer c = new Costumer(0);

		try {
			migration.costumerTypes(c);
		} catch (WantedException e) {
			assertEquals(1, migration.getWanted());
		} catch (CovidException e) {
			fail();
		} catch (MinorException e) {
			fail();
		}

    }

	@Test
    public void validateClientPass1() {
        setupScenary14();

        Costumer c = new Costumer(1);

		try {
			migration.costumerTypes(c);
		} catch (WantedException e) {
			fail();
		} catch (CovidException e) {
			fail();
		} catch (MinorException e) {
			fail();
		}

		assertEquals(1, migration.getApproved());

    }

	@Test
    public void validateClientPass3() {
        setupScenary14();

        Costumer c = new Costumer(2);

		try {
			migration.costumerTypes(c);
		} catch (WantedException e) {
			fail();
		} catch (CovidException e) {
			assertEquals(1, migration.getCovid());
		} catch (MinorException e) {
			fail();
		}
    }

	@Test
    public void validateClientPass4() {
        setupScenary14();

		Costumer c = new Costumer(3);

		try {
			migration.costumerTypes(c);
		} catch (WantedException e) {
			fail();
		} catch (CovidException e) {
			fail();
		} catch (MinorException e) {
			assertEquals(1, migration.getMinor());
		}
    }

}
