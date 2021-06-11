package model;

import exeption.CovidException;
import exeption.MinorException;
import exeption.WantedException;

import java.io.Serializable;

public class Migration implements Serializable, Supplier {

    private static final long serialVersionUID = 1L;
    private Flight flight;
    private int capital, approved, wanted, covid, minor;

    public Migration(Flight flight) {
        this.flight = flight;
        capital = 0;
        approved = 0;
        wanted = 0;
        covid = 0;
        minor = 0;
    }

    public String getId() {
        return flight.getId();
    }

    public Flight getFlight() {
        return this.flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public int getCapital() {
        return this.capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public int getApproved() {
        return this.approved;
    }

    public void setApproved() {
        approved++;
    }

    public void setmin(int minor) {
        this.minor = minor;
    }

    public void setcov(int covid) {
        this.covid = covid;
    }

    public void setwant(int wanted) {
        this.wanted = wanted;
    }

    public void setapp(int approved) {
        this.approved = approved;
    }

    public int getWanted() {
        return this.wanted;
    }

    public void setWanted() {
        wanted++;
    }

    public int getCovid() {
        return this.covid;
    }

    public void setCovid() {
        covid++;
    }

    public int getMinor() {
        return this.minor;
    }

    public void setMinor() {
        minor++;
    }

    public int getReport() {
        return wanted + covid + minor + approved;
    }

    /**
     *
     * @param c
     * @throws WantedException
     * @throws CovidException
     * @throws MinorException
     */
    public void costumerTypes(Costumer c) throws WantedException, CovidException, MinorException {
        switch (c.getState()) {
            case "WANTED":
                setWanted();
                throw new WantedException();
            case "APPROVED":
                setApproved();
                break;
            case "COVID":
                setCovid();
                throw new CovidException();
            case "MINOR":
                setMinor();
                throw new MinorException();
            default:
                break;
        }
    }

    /**
     *
     */
    @Override
    public void airportCharges() {
        capital = (wanted + covid + minor) * 10;
    }

}
