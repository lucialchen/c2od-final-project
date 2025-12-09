package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import municipaldata.processor.FinesPerCapita;
import municipaldata.common.ParkingViolation;
import municipaldata.data.PopulationReader;
import municipaldata.data.ParkingViolationReader;

import java.util.*;

public class GetFinesPerZipTest {

    @Test
    void testEmptyViolations() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("19107", 100);
                map.put("19108", 200);
                return map;
            }
        };
        
        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                return new TreeMap<>();
            }
        };
        
        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertTrue(result.isEmpty());
    }

    @Test
    void testSingleViolation() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("19107", 100);
                return map;
            }
        };
        
        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", 50.0f, "desc", 123, "PA", 1, "19107"));
                map.put("19107", violations);
                return map;
            }
        };
        
        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertEquals(1, result.size());
        assertTrue(result.contains("19107 0.5000"));
    }

    @Test
    void testMultipleViolations() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("19107", 100);
                return map;
            }
        };
        
        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", 50.0f, "desc", 123, "PA", 1, "19107"));
                violations.add(new ParkingViolation("2023-01-02", 30.0f, "desc", 124, "PA", 2, "19107"));
                map.put("19107", violations);
                return map;
            }
        };
        
        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertEquals(1, result.size());
        assertTrue(result.contains("19107 0.8000"));
    }

    @Test
    void testNonPAViolationsIgnored() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("19107", 100);
                return map;
            }
        };
        
        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", 50.0f, "desc", 123, "NJ", 1, "19107"));
                violations.add(new ParkingViolation("2023-01-02", 30.0f, "desc", 124, "PA", 2, "19107"));
                map.put("19107", violations);
                return map;
            }
        };
        
        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertEquals(1, result.size());
        assertTrue(result.contains("19107 0.3000"));
    }

    @Test
    void testNullFinesIgnored() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("19107", 100);
                return map;
            }
        };
        
        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", null, "desc", 123, "PA", 1, "19107"));
                violations.add(new ParkingViolation("2023-01-02", 50.0f, "desc", 124, "PA", 2, "19107"));
                map.put("19107", violations);
                return map;
            }
        };
        
        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertEquals(1, result.size());
        assertTrue(result.contains("19107 0.5000"));
    }

    @Test
    void testMultipleZips() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("19107", 100);
                map.put("19108", 200);
                return map;
            }
        };
        
        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> v1 = new ArrayList<>();
                v1.add(new ParkingViolation("2023-01-01", 50.0f, "desc", 123, "PA", 1, "19107"));
                map.put("19107", v1);
                
                ArrayList<ParkingViolation> v2 = new ArrayList<>();
                v2.add(new ParkingViolation("2023-01-02", 100.0f, "desc", 124, "PA", 2, "19108"));
                map.put("19108", v2);
                
                return map;
            }
        };
        
        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertEquals(2, result.size());
        assertTrue(result.contains("19107 0.5000"));
        assertTrue(result.contains("19108 0.5000"));
    }

}
