package municipaldata.data;

import municipaldata.common.*;
import java.util.*;

public class ParkingViolationReader {

    protected Map<Integer, ArrayList<ParkingViolation>> map;
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
        Map map = new HashMap<>();

        for (ParkingViolation pv : violations) {
            int zip = pv.getZip();
            ArrayList<ParkingViolation> zipViolations = map.getOrDefault(zip, new ArrayList<>());
            zipViolations.add(pv);
            map.put(zip, zipViolations);
        }
    }

    public Map<Integer, ArrayList<ParkingViolation>> getViolations() {
        return map;
    }
}
