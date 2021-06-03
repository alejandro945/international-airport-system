package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class Flight implements Serializable {
    private String id;
    private String departureDate;
    private String departureHour;
    private String arrivalDate;
    private String arrivalHour;
    private Places origin;
    private Places destination;
    private Aircraft plane;
    private Track track;
    private FlightState flightStatus;
    private long position;
    private long duration;

    public Flight(String id, String departureDate, String departureHour, String arrivalDate, String arrivalHour,
            Places origin, Places destination, Track track) {
        this.id = id;
        this.departureDate = departureDate;
        this.departureHour = departureHour;
        this.arrivalDate = arrivalDate;
        this.arrivalHour = arrivalHour;
        this.origin = origin;
        this.destination = destination;
        this.track = track;
        // this.plane = plane;
        this.flightStatus = FlightState.SCHEDULED;
        this.position = 125;
        flightDuration();
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

    public Track getTrack() {
        return this.track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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

    /**
     * @return long
     */
    public long getDuration() {
        return duration;
    }

    /**
     * @return long
     */
    public long getPosition() {
        return this.position;
    }

    /**
     * @param increase
     */
    public void setPosition(long increase) {
        position += increase;
    }

    /**
     * @return int[]
     */
    public int[] getDateFormat() {
        int[] render = new int[10];
        render[0] = Integer.parseInt(departureDate.split("-")[0]);
        render[1] = Integer.parseInt(departureDate.split("-")[1]);
        render[2] = Integer.parseInt(departureDate.split("-")[2]);
        render[3] = Integer.parseInt(departureHour.split(":")[0]);
        render[4] = Integer.parseInt(departureHour.split(":")[1]);
        render[5] = Integer.parseInt(arrivalDate.split("-")[0]);
        render[6] = Integer.parseInt(arrivalDate.split("-")[1]);
        render[7] = Integer.parseInt(arrivalDate.split("-")[2]);
        render[8] = Integer.parseInt(arrivalHour.split(":")[0]);
        render[9] = Integer.parseInt(arrivalHour.split(":")[1]);
        return render;
    }

    public void flightDuration() {
        int[] render = getDateFormat();
        LocalDateTime dep = LocalDateTime.of(render[0], render[1], render[2], render[3], render[4]);
        LocalDateTime arr = LocalDateTime.of(render[5], render[6], render[7], render[8], render[9]);
        Duration d = Duration.between(dep, arr);
        duration = Math.abs(d.getSeconds());
    }

}
