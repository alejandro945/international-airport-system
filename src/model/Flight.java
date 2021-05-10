package model;

public class Flight {
    private String departureDate;
    private String departureHour;
    private String arrivalDate;
    private String arrivalHour;
    private Places origin;
    private Places destination;
    private Aircraft plane;
    private FlightStatus flightStatus;

    /**
     * @return String
     */
    public String getDepartureDate() {
        return this.departureDate;
    }

    /**
     * @param departureDate
     */
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * @return String
     */
    public String getDepartureHour() {
        return this.departureHour;
    }

    /**
     * @param departureHour
     */
    public void setDepartureHour(String departureHour) {
        this.departureHour = departureHour;
    }

    /**
     * @return String
     */
    public String getArrivalDate() {
        return this.arrivalDate;
    }

    /**
     * @param arrivalDate
     */
    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    /**
     * @return String
     */
    public String getArrivalHour() {
        return this.arrivalHour;
    }

    /**
     * @param arrivalHour
     */
    public void setArrivalHour(String arrivalHour) {
        this.arrivalHour = arrivalHour;
    }

    /**
     * @return Places
     */
    public Places getOrigin() {
        return this.origin;
    }

    /**
     * @param origin
     */
    public void setOrigin(Places origin) {
        this.origin = origin;
    }

    /**
     * @return Places
     */
    public Places getDestination() {
        return this.destination;
    }

    /**
     * @param destination
     */
    public void setDestination(Places destination) {
        this.destination = destination;
    }

    /**
     * @return Aircraft
     */
    public Aircraft getPlane() {
        return this.plane;
    }

    /**
     * @param plane
     */
    public void setPlane(Aircraft plane) {
        this.plane = plane;
    }

    /**
     * @return FlightStatus
     */
    public FlightStatus getFlightStatus() {
        return this.flightStatus;
    }

    /**
     * @param flightStatus
     */
    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }

}
