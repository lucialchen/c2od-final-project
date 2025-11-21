package municipaldata.processor;

import municipaldata.common.ParkingViolation;
import municipaldata.data.ParkingViolationReader;
import municipaldata.data.PopulationReader;

import java.util.*;

public class FinesPerCapita {

    private PopulationReader populationReader;
    private ParkingViolationReader parkingViolationReader;
    private TreeSet<String> finesPerZip;

    public FinesPerCapita(PopulationReader pr, ParkingViolationReader pvr) {
        populationReader = pr;
        parkingViolationReader = pvr;
    }

    public TreeSet<String> getFinesPerZip() {
        if (finesPerZip != null) {
            return finesPerZip;
        }

        TreeMap<String, ArrayList<ParkingViolation>> violationsMap = (TreeMap<String, ArrayList<ParkingViolation>>) parkingViolationReader.getViolations();
        TreeMap<String, Integer> populationsMap = (TreeMap<String, Integer>) populationReader.getPopulationData();

        TreeSet<String> fpz = new TreeSet<>();

        for (Map.Entry<String, Integer> populationEntry : populationsMap.entrySet()) {
            String zip = populationEntry.getKey();
            int pop = populationEntry.getValue();

            List<ParkingViolation> violations = violationsMap.get(zip);
            if (violations == null) {
                continue;
            }

            double totalFines = 0.0;

            for (ParkingViolation violation : violations) {
                if (!violation.getState().equals("PA")) {
                    continue;
                }

                totalFines += violation.getFine();
            }

            if (totalFines == 0.0 || pop == 0) {
                continue;
            }

            double fineAtZip = totalFines / pop;

            fpz.add(zip + " " + String.format("%.4f", fineAtZip));
        }

        finesPerZip = fpz;
        return finesPerZip;
    }
}
