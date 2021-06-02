package model;

public class Flight {
    private String departureDate;
    private String departureHour;
    private String arrivalDate;
    private String arrivalHour;
    private Places origin;
    private Places destination;
    private Aircraft plane;
    private FlightState flightStatus;
    private String id;

    public Flight(String id, String departureDate, String departureHour, String arrivalDate, String arrivalHour, Places origin,
            Places destination) {
        this.departureDate = departureDate;
        this.departureHour = departureHour;
        this.arrivalDate = arrivalDate;
        this.arrivalHour = arrivalHour;
        this.origin = origin;
        this.destination = destination;
        // this.plane = plane;
        this.flightStatus = FlightState.AIRBORNE;
        this.id = id;
    }

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
    public FlightState getFlightStatus() {
        return this.flightStatus;
    }

    /**
     * @param flightStatus
     */
    public void setFlightStatus(FlightState flightStatus) {
        this.flightStatus = flightStatus;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    
}
