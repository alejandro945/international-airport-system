package model;

import java.io.Serializable;

public class Seat implements Serializable {
    private static final long serialVersionUID = 1L;
    private int seatNumber;
    private char seatLetter;
    private boolean seatState;
    private boolean economic;

    private int price;

    public Seat(int seatNumber, char seatLetter, boolean economic, int price) {
        this.seatNumber = seatNumber;
        this.seatLetter = seatLetter;
        this.seatState = false;
        this.economic = economic;

        if(economic){
            this.price = price;
        } else {
            this.price = (int)((price*0.3)+price);
        }       
    }

    public int getSeatNumber() {
        return this.seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public char getSeatLetter() {
        return this.seatLetter;
    }

    public void setSeatLetter(char seatLetter) {
        this.seatLetter = seatLetter;
    }

    public boolean isSeatState() {
        return this.seatState;
    }

    public void setSeatState(boolean seatState) {
        this.seatState = seatState;
    }

    public boolean getSeatState() {
        return this.seatState;
    }

    public boolean isEconomic() {
        return this.economic;
    }

    public void setEconomic(boolean economic) {
        this.economic = economic;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
}   
