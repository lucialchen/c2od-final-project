package municipaldata.data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PropertyValueReader {

    public void readData(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine();

            if (header == null) {
                System.err.println("Empty property value file: " + filePath);
                return;
            }

            String[] fields = header.split(",", -1);
            int marketIdx = -1;
            int livAreaIdx = -1;
            int zipIdx = -1;

            for (int i = 0; i < fields.length; i++) {
                String field = fields[i].trim().toLowerCase();
                if (field.equals("market_value")) {
                    marketIdx = i;
                } else if (field.equals("living_area")) {
                    livAreaIdx = i;
                } else if (field.equals("zip_code")) {
                    zipIdx = i;
                }
            }

            if (marketIdx == -1 || livAreaIdx == -1 || zipIdx == -1) {
                System.err.println("Missing required fields in property value file header: " + filePath);
                return;
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] dataFields = line.split(",", -1);
                try {
                    float marketValue = Float.parseFloat(dataFields[marketIdx]);
                    float livableArea = Float.parseFloat(dataFields[livAreaIdx]);
                    String zip = dataFields[zipIdx];
                    String zipCode = "";
                    if (zip.length() >= 5) {
                        zipCode = zip.substring(0, 5);
                    } else {
                        zipCode = zip;
                    }
                    
                    PropertyValue propertyValue = new PropertyValue(marketValue, livableArea, zipCode);
                } catch (NumberFormatException e) {
                    System.err.println("Error: Number format issue in property value entry: " + e.getMessage());
                    continue;
                }
            }
        } catch (IOException e) {
            System.err.println("Error opening or reading property value file: " + filePath);
            return;
        }
    }
}
