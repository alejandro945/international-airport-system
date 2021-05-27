package model;

public class Premium extends Ticket {
    private Meal[] meals;

    public Premium(Flight flight, Seat fligthSeat, Luggage luggage, int premiumPrice) {
        super(flight, fligthSeat, luggage, premiumPrice);
        meals = new Meal[2];
    }

    /**
     * @return Meal[]
     */
    public Meal[] getMeals() {
        return this.meals;
    }

    /**
     * @param meals
     */
    public void setMeals(Meal[] meals) {
        this.meals = meals;
    }

}
