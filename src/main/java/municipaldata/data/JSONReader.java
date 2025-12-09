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

                if (violationJSON.containsKey("date")) {
                    timeStamp = (String) violationJSON.get("date");
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

                if (violationJSON.containsKey("violation")) {
                    description = (String) violationJSON.get("violation");
                }

                if (violationJSON.containsKey("plate_id")) {
                    try {
                        vehID = Integer.parseInt(violationJSON.get("plate_id").toString());
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

                if (violationJSON.containsKey("ticket_number")) {
                    try {
                        violID = Integer.parseInt(violationJSON.get("ticket_number").toString());
                        if (violID < 0) {
                            violID = null;
                        }
                    } catch (NumberFormatException e) {
                        violID = null;
                    }
                }

                if (violationJSON.containsKey("zip_code")) {
                    zip = (String) violationJSON.get("zip_code");
                }

                if (zip == null || zip.isEmpty()) {
                    continue;
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