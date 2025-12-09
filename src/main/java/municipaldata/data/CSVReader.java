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

                String timeStamp = null;
                Float fine = null;
                String description = null;
                Integer vehID = null;
                String state = null;
                Integer violID = null;
                String zip = null;

                if (!fields[0].isEmpty()) {
                    timeStamp = fields[0];
                } 

                if (!fields[1].isEmpty()) {
                    try {
                        fine = Float.parseFloat(fields[1]);
                        if (fine < 0) {
                            fine = null;
                        }
                    } catch (NumberFormatException e) {
                        fine = null;
                    }
                }
                    
                if (!fields[2].isEmpty()) {
                    description = fields[2];
                }
                    
                if (!fields[3].isEmpty()) {
                    try {
                        vehID = Integer.parseInt(fields[3]);
                        if (vehID < 0) {
                            vehID = null;
                        }
                    } catch (NumberFormatException e) {
                        vehID = null;
                    }
                }

                if (!fields[4].isEmpty()) {
                    state = fields[4];
                }

                if (!fields[5].isEmpty()) {
                    try {
                        violID = Integer.parseInt(fields[5]);
                        if (violID < 0) {
                            violID = null;
                        }
                    } catch (NumberFormatException e) {
                        violID = null;
                    }
                }
                    
                if (fields[6] != null && !fields[6].isEmpty()) {
                    zip = fields[6];
                } else {
                    continue;
                }

                ParkingViolation violation = new ParkingViolation(timeStamp, fine, description, vehID, state, violID, zip);
                data.add(violation);
                
            }
        } catch (IOException e) {
            System.err.println("Error opening or reading CSV file: " + filePath);
            return data;
        }
        return data;
    }
}