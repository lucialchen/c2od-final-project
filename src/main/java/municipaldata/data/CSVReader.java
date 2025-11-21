package municipaldata.data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import municipaldata.common.ParkingViolation;

public class CSVReader implements ParkViolationFileType {
    private String filePath;

    public CSVReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public ArrayList<ParkingViolation> readData() {
        ArrayList<ParkingViolation> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",", -1);
                
                if (fields.length != 7) {
                    continue;
                }

                try {
                    String timeStamp = fields[0];
                    int fine = Integer.parseInt(fields[1]);
                    String description = fields[2];
                    int vehID = Integer.parseInt(fields[3]);
                    String state = fields[4];
                    int violID = Integer.parseInt(fields[5]);
                    String zip = fields[6];
                    ParkingViolation violation = new ParkingViolation(timeStamp, fine, description, vehID, state, violID, zip);
                    data.add(violation);
                } catch (NumberFormatException e) {
                    continue;
                }
                
            }
        } catch (IOException e) {
            System.err.println("Error opening or reading CSV file: " + filePath);
            return data;
        }
        return data;
    }
}