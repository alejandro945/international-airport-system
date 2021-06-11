package model;

import java.io.Serializable;

public class Luggage implements Price, Serializable {
    private static final long serialVersionUID = 1L;
    private int luggagePrice;
    private double height;
    private double width;
    private double weight;

    private Luggage nextLuggage;
    private Luggage previousLuggage;

    public Luggage(double height, double width, double weight) {
        this.height = height;
        this.width = width;
        this.weight = weight;
        calculatePrice(1);
    }

    public int getLuggagePrice() {
        return this.luggagePrice;
    }

    /**
     *
     * @param increase
     */
    public void calculatePrice(int increase) {
        this.luggagePrice = (int) (weight * height) * increase;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
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

    public Luggage getPreviousLuggage() {
        return previousLuggage;
    }

    public void setPreviousLuggage(Luggage previousLuggage) {
        this.previousLuggage = previousLuggage;
    }

}
