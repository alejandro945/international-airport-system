package model;

public class SpecialLuggage extends Luggage {
    private String luggageType;

    public SpecialLuggage(double height, double width, double weight, String luggageType) {
        super(height, width, weight);
        this.luggageType = luggageType;
        calculatePrice(30);
    }

    /**
     * @return String
     */
    public String getLuggageType() {
        return this.luggageType;
    }

    /**
     * @param luggageType
     */
    public void setLuggageType(String luggageType) {
        this.luggageType = luggageType;
    }

}
