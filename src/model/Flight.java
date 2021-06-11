package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class Flight implements  Comparable<Flight>, Serializable {
    private static final long serialVersionUID = 1L;
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
    private String takesOff; // Joins departure date and hour. For table view purposes.
    private String arrives; // Joins arrival date and hour. For table view purposes.
    private Airline airline;

    private long position;
    private int progress;
    private double slope;
    private double journey;
    private long duration;

    public Flight(String id) {
        this.id = id;
    }

    public Flight(String id, String departureDate, String departureHour, String arrivalDate, String arrivalHour,
            Places origin, Places destination, Track track, Airline airline, Aircraft aircraft) {
        this.id = id;
        this.departureDate = departureDate;
        this.departureHour = departureHour;
        this.arrivalDate = arrivalDate;
        this.arrivalHour = arrivalHour;
        this.origin = origin;
        this.destination = destination;
        this.track = track;
        this.plane = aircraft;
        this.flightStatus = FlightState.SCHEDULED;
        this.position = 125;
        this.progress = 0;
        this.airline = airline;
        updateFullDates();
        flightDuration();
    }

    /**
     *
     */
    private void updateFullDates() {
        takesOff = departureDate + " - " + departureHour;
        arrives = arrivalDate + " - " + arrivalHour;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
        updateFullDates();
    }

    public Pilot getPilot() {
        return plane.getPilot();
    }

    public String getDepartureHour() {
        return this.departureHour;
    }

    public void setDepartureHour(String departureHour) {
        this.departureHour = departureHour;
        updateFullDates();
    }

    public String getArrivalDate() {
        return this.arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
        updateFullDates();
    }

    public String getArrivalHour() {
        return this.arrivalHour;
    }

    public void setArrivalHour(String arrivalHour) {
        this.arrivalHour = arrivalHour;
        updateFullDates();
    }

    public Places getOrigin() {
        return this.origin;
    }

    public void setOrigin(Places origin) {
        this.origin = origin;
    }

    public Places getDestination() {
        return this.destination;
    }

    public void setDestination(Places destination) {
        this.destination = destination;
    }

    public Aircraft getPlane() {
        return this.plane;
    }

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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public FlightState getFlightStatus() {
        return this.flightStatus;
    }

    public void setFlightStatus(FlightState flightStatus) {
        this.flightStatus = flightStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public long getPosition() {
        return this.position;
    }

    public void setPosition(long increase) {
        position += increase;
    }

    public double getSlope() {
        return this.slope;
    }

    public void setSlope(double slope) {
        this.slope = slope;
    }

    public double getJourney() {
        return this.journey;
    }

    public void setJourney(double journey) {
        this.journey = journey;
    }

    public String getTakesOff() {
        return takesOff;
    }

    public void setTakesOff(String takesOff) {
        this.takesOff = takesOff;
    }

    public String getArrives() {
        return arrives;
    }

    public void setArrives(String arrives) {
        this.arrives = arrives;
    }

    /**
     *
     * @return
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

    /**
     *
     */
    public void flightDuration() {
        int[] render = getDateFormat();
        LocalDateTime dep = LocalDateTime.of(render[0], render[1], render[2], render[3], render[4]);
        LocalDateTime arr = LocalDateTime.of(render[5], render[6], render[7], render[8], render[9]);
        Duration d = Duration.between(dep, arr);
        duration = Math.abs(d.getSeconds());
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    /**
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(Flight other) {
        return departureDate.compareTo(other.getDepartureDate());
    }

    /**
     *
     * @param other
     * @return
     */
    public int compareById(Flight other){
        return id.compareTo(other.getId());
    }

}
