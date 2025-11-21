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
    public ArrayList<ParkingViolation> readData() throws IOException {
        ArrayList<ParkingViolation> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            JSONParser parser = new JSONParser();
            JSONArray violations = (JSONArray) parser.parse(br);

            for (Object obj : violations) {
                JSONObject violationJSON = (JSONObject) obj;
                try {
                    String timeStamp = (String) violationJSON.get("timeStamp");
                    float fine = Float.parseFloat(violationJSON.get("fine").toString());
                    String description = (String) violationJSON.get("description");
                    int vehID = Integer.parseInt(violationJSON.get("vehID").toString());
                    String state = (String) violationJSON.get("state");
                    int violID = Integer.parseInt(violationJSON.get("violID").toString());
                    int zip = Integer.parseInt(violationJSON.get("zip").toString());

                    ParkingViolation violation = new ParkingViolation(timeStamp, fine, description, vehID, state, violID, zip);
                    data.add(violation);
                } catch (Exception e) {
                    System.err.println("Error parsing violation entry: " + e.getMessage());
                    continue;
                }
            }
        } catch (Exception e) {
            System.err.println("Error opening or reading JSON file: " + filePath);
            return data;
        }

        return data;
    }
}