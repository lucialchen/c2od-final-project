package municipaldata.data;

import municipaldata.common.*;
import java.util.*;

public class ParkingViolationReader {

    protected Map<String, ArrayList<ParkingViolation>> map;
    protected ParkViolationFileType parkViolationFileType;

    public ParkingViolationReader(String csvOrJson, String filename) {

        switch (csvOrJson) {
            case "csv":
                parkViolationFileType = new CSVReader(filename);
                break;

            case "json":
                parkViolationFileType = new JSONReader(filename);
                break;
        }

        ArrayList<ParkingViolation> violations = parkViolationFileType.readData();
        Map map = new TreeMap<>();

        for (ParkingViolation pv : violations) {
            String zip = pv.getZip();
            if (zip == null || zip.isEmpty() || zip.length() < 5) {
                continue;
            }

            if (zip.length() != 5) {
                zip = zip.substring(0, 5);
            }

            ArrayList<ParkingViolation> zipViolations = (ArrayList<ParkingViolation>) map.getOrDefault(zip, new ArrayList<>());
            zipViolations.add(pv);
            map.put(zip, zipViolations);
        }
    }

    public Map<String, ArrayList<ParkingViolation>> getViolations() {
        return map;
    }
}
