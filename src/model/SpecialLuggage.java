package model;

public class SpecialLuggage extends Luggage {
    private String luggageType;

    public SpecialLuggage(double height, double weight, String luggageType) {
        super(height, weight);
        this.luggageType = luggageType;
        calculateLuggagePrice(35);
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
