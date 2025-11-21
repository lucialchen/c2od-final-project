package municipaldata.data;

import municipaldata.common.ParkingViolation;

import java.io.IOException;
import java.util.ArrayList;

public interface ParkViolationFileType {
    public ArrayList<ParkingViolation> readData();
}
