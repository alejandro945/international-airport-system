package model;

public class Luggage {
    private int luggagePrice;
    private double height;
    private double weight;

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

}
