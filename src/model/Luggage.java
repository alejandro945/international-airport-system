package model;

public class Luggage {
    private int luggagePrice;
    private double height;
    private double weight;
    private Luggage nextLuggage;

    public Luggage(double height, double weight) {
        this.height = height;
        this.weight = weight;
        calculateLuggagePrice(25);
    }

    public int getLuggagePrice() {
        return this.luggagePrice;
    }

    public void calculateLuggagePrice(int increase) {
        this.luggagePrice = (int) (weight * height) * increase;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setLuggagePrice(int luggagePrice) {
        this.luggagePrice = luggagePrice;
    }

    public Luggage getNextLuggage() {
        return this.nextLuggage;
    }

    public void setNextLuggage(Luggage nextLuggage) {
        this.nextLuggage = nextLuggage;
    }

}
