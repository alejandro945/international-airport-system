package model;

public class Luggage implements Price {
    private int luggagePrice;
    private double height;
    private double width;
    private double weight;
    private Luggage nextLuggage;

    public Luggage(double height, double width, double weight) {
        this.height = height;
        this.width = width;
        this.weight = weight;
        calculatePrice(0);
    }

    /**
     * @return int
     */
    public int getLuggagePrice() {
        return this.luggagePrice;
    }

    /**
     * @param increase
     */
    public void calculatePrice(int increase) {
        this.luggagePrice = (int) (weight * height) * increase;
    }

    /**
     * @return double
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return double
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @param width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return double
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * @param luggagePrice
     */
    public void setLuggagePrice(int luggagePrice) {
        this.luggagePrice = luggagePrice;
    }

    /**
     * @return Luggage
     */
    public Luggage getNextLuggage() {
        return this.nextLuggage;
    }

    /**
     * @param nextLuggage
     */
    public void setNextLuggage(Luggage nextLuggage) {
        this.nextLuggage = nextLuggage;
    }

}
