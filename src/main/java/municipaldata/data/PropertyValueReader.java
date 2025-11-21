package municipaldata.data;
import com.sun.source.tree.Tree;
import municipaldata.common.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PropertyValueReader {

    private String filePath;
    private Map<String, ArrayList<PropertyValue>> map;

    public PropertyValueReader(String fn) {
        filePath = fn;
        map = readData();
    }

    // TODO: Make sure error handling as specified works so that it properly ignores the missing/incorrect values while retaining the rest
    // I.e., make the PropertyValue object for each pass and fill in the blanks with nulls if needed while reading data
    private Map<String, ArrayList<PropertyValue>> readData()  {
        Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine();

            if (header == null) {
                System.err.println("Empty property value file: " + filePath);
                return map;
            }

            String[] fields = header.split(",", -1);
            int marketIdx = -1;
            int livAreaIdx = -1;
            int zipIdx = -1;

            for (int i = 0; i < fields.length; i++) {
                String field = fields[i].trim().toLowerCase();
                if (field.equals("market_value")) {
                    marketIdx = i;
                } else if (field.equals("total_livable_area")) {
                    livAreaIdx = i;
                } else if (field.equals("zip_code")) {
                    zipIdx = i;
                }
            }

            if (marketIdx == -1 || livAreaIdx == -1 || zipIdx == -1) {
                System.err.println("Missing required fields in property value file header: " + filePath);
                return map;
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] dataFields = line.split(",", -1);
                try {
                    double marketValue = Double.parseDouble(dataFields[marketIdx]);
                    double livableArea = Double.parseDouble(dataFields[livAreaIdx]);
                    String zip = dataFields[zipIdx];
                    String zipCode = "";
                    if (zip.length() >= 5) {
                        zipCode = zip.substring(0, 5);
                    } else {
                        zipCode = zip;
                    }
                    
                    PropertyValue propertyValue = new PropertyValue(marketValue, livableArea, zipCode);

                    ArrayList<PropertyValue> propertiesList = map.getOrDefault(zipCode, new ArrayList<>());
                    propertiesList.add(propertyValue);
                    map.put(zipCode, propertiesList);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Number format issue in property value entry: " + e.getMessage());
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Error opening or reading property value file: " + filePath);
            return map;
        }

        return map;
    }

    public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
        return map;
    }
}
