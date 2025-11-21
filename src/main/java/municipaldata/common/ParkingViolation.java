package municipaldata.common;

public class ParkingViolation {
    String timeStamp;
    float fine;
    String description;
    int vehID;
    String state;
    int violID;
    int zip;

    public ParkingViolation(String timeStamp, float fine, String description, int vehID, 
    String state, int violID, int zip) {
        this.timeStamp = timeStamp;
        this.fine = fine;
        this.description = description;
        this.vehID = vehID;
        this.state = state;
        this.violID = violID;
        this.zip = zip;
    }

    public int getZip() {
        return zip;
    }

    public float getFine() {
        return fine;
    }

    public String getState() {
        return state;
    }
}
