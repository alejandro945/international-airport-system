package model;

public class Premium extends Ticket {

    private Meal[] meals;

    /**
     * The constructor method of a Premium Ticket Object<br>
     */
    public Premium(Flight flight, Seat fligthSeat, int premiumPrice) {
        super(flight, fligthSeat, premiumPrice);
        meals = new Meal[2];
    }

    public Meal[] getMeals() {
        return this.meals;
    }

    public void setMeals(Meal[] meals) {
        this.meals = meals;
    }

}
