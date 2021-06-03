package model;

import java.io.Serializable;

public class Seat implements Serializable {
    private int seatNumber;
    private char seatLetter;
    private boolean seatState;
    private boolean economic;

    public Seat(int seatNumber, char seatLetter, boolean economic) {
        this.seatNumber = seatNumber;
        this.seatLetter = seatLetter;
        this.seatState = false;
        this.economic = economic;
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

}
