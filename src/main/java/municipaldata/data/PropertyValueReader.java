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

    private Map<String, ArrayList<PropertyValue>> readData()  {
        Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine();

            if (header == null) {
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
                return map;
            }

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] dataFields = line.split(",", -1);
                Double marketValue = null;
                if (marketIdx < dataFields.length) {
                    String mv = dataFields[marketIdx].trim();
                    if (!mv.isEmpty()) {
                        try {
                            marketValue = Double.parseDouble(mv);
                            if (marketValue <= 0) {
                                marketValue = null;
                            }
                        } catch (NumberFormatException e) {
                            marketValue = null;
                        }
                    }
                }

                Double livableArea = null;
                if (livAreaIdx < dataFields.length) {
                    String la = dataFields[livAreaIdx].trim();
                    if (!la.isEmpty()) {
                        try {
                            livableArea = Double.parseDouble(la);
                            if (livableArea <= 0) {
                                livableArea = null;
                            }
                        } catch (NumberFormatException e) {
                            livableArea = null;
                        }
                    }
                }

                String zip = "";
                if (zipIdx < dataFields.length) {
                    zip = dataFields[zipIdx].trim();
                    if (zip.length() >= 5) {
                        zip = zip.substring(0, 5);
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
                    
                PropertyValue propertyValue = new PropertyValue(marketValue, livableArea, zip);

                ArrayList<PropertyValue> propertiesList = map.getOrDefault(zip, new ArrayList<>());
                propertiesList.add(propertyValue);
                map.put(zip, propertiesList);
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
