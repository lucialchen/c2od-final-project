package municipaldata.common;

public class ParkingViolation {
    String timeStamp;
    Float fine;
    String description;
    Integer vehID;
    String state;
    Integer violID;
    String zip;

    public ParkingViolation(String timeStamp, Float fine, String description, Integer vehID, 
    String state, Integer violID, String zip) {
        this.timeStamp = timeStamp;
        this.fine = fine;
        this.description = description;
        this.vehID = vehID;
        this.state = state;
        this.violID = violID;
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public Float getFine() {
        return fine;
    }

    public String getState() {
        return state;
    }
}
