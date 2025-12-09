package municipaldata.data;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import municipaldata.common.ParkingViolation;

public class JSONReader implements ParkViolationFileType {
    private String filePath;

    public JSONReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public ArrayList<ParkingViolation> readData() {
        ArrayList<ParkingViolation> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            JSONParser parser = new JSONParser();
            JSONArray violations = (JSONArray) parser.parse(br);

            for (Object obj : violations) {
                JSONObject violationJSON = (JSONObject) obj;
                String timeStamp = null;
                Float fine = null;
                String description = null;
                Integer vehID = null;
                String state = null;
                Integer violID = null;
                String zip = null;

                if (violationJSON.containsKey("timeStamp")) {
                    timeStamp = (String) violationJSON.get("timeStamp");
                }

                if (violationJSON.containsKey("fine")) {
                    try {
                        fine = Float.parseFloat(violationJSON.get("fine").toString());
                        if (fine < 0) {
                            fine = null;
                        }
                    } catch (NumberFormatException e) {
                        fine = null;
                    }
                }

                if (violationJSON.containsKey("description")) {
                    description = (String) violationJSON.get("description");
                }

                if (violationJSON.containsKey("vehID")) {
                    try {
                        vehID = Integer.parseInt(violationJSON.get("vehID").toString());
                        if (vehID < 0) {
                            vehID = null;
                        }
                    } catch (NumberFormatException e) {
                        vehID = null;
                    }
                }

                if (violationJSON.containsKey("state")) {
                    state = (String) violationJSON.get("state");
                }

                if (violationJSON.containsKey("violID")) {
                    try {
                        violID = Integer.parseInt(violationJSON.get("violID").toString());
                        if (violID < 0) {
                            violID = null;
                        }
                    } catch (NumberFormatException e) {
                        violID = null;
                    }
                }

                if (violationJSON.containsKey("zip")) {
                    zip = (String) violationJSON.get("zip");
                }

                ParkingViolation violation = new ParkingViolation(timeStamp, fine, description, vehID, state, violID, zip);
                data.add(violation);
            }
        } catch (Exception e) {
            System.err.println("Error opening or reading JSON file: " + filePath);
            return data;
        }

        return data;
    }
}