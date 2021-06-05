package model;

public class Seat {
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

    /**
     * @return int
     */
    public int getSeatNumber() {
        return this.seatNumber;
    }

    /**
     * @param seatNumber
     */
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * @return char
     */
    public char getSeatLetter() {
        return this.seatLetter;
    }

    /**
     * @param seatLetter
     */
    public void setSeatLetter(char seatLetter) {
        this.seatLetter = seatLetter;
    }

    /**
     * @return boolean
     */
    public boolean isSeatState() {
        return this.seatState;
    }

    /**
     * @param seatState
     */
    public void setSeatState(boolean seatState) {
        this.seatState = seatState;
    }

    /**
     * @return boolean
     */
    public boolean getSeatState() {
        return this.seatState;
    }

    /**
     * @return boolean
     */
    public boolean isEconomic() {
        return this.economic;
    }

    /**
     * @param economic
     */
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
