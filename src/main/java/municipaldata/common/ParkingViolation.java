package municipaldata.common;

public class ParkingViolation {
    String timeStamp;
    int fine;
    String description;
    int vehID;
    String state;
    int violID;
    int zip;

    public ParkingViolation(String timeStamp, int fine, String description, int vehID, 
    String state, int violID, int zip) {
        this.timeStamp = timeStamp;
        this.fine = fine;
        this.description = description;
        this.vehID = vehID;
        this.state = state;
        this.violID = violID;
        this.zip = zip;
    }
}
