import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import municipaldata.common.*;
import municipaldata.data.*;
import municipaldata.processor.*;

import java.util.*;

public class GetFinesPerZipTest {

    @Test
    void testEmptyViolations() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 100);
                map.put("88999", 200);
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
    void testNoViolationsForZip() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 100);
                return map;
            }
        };
        
        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", 50.0f, "desc", 123, "PA", 1, "88999"));
                map.put("88999", violations);
                return map;
            }
        };
        
        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertTrue(result.isEmpty());
    }

    @Test
    void testSingleViolationPA() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 100);
                return map;
            }
        };
        
        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", 50.0f, "desc", 123, "PA", 1, "89999"));
                map.put("89999", violations);
                return map;
            }
        };
        
        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertEquals(1, result.size());
        assertTrue(result.contains("89999 0.5000"));
    }

    @Test
    void testViolationNotPA() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 100);
                return map;
            }
        };

        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", 50.0f, "desc", 123, "NY", 1, "89999"));
                map.put("89999", violations);
                return map;
            }
        };

        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertTrue(result.isEmpty());
    }

    @Test
    void testMixedStates() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 100);
                return map;
            }
        };

        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", 50.0f, "desc", 123, "PA", 1, "89999"));
                violations.add(new ParkingViolation("2023-01-02", 30.0f, "desc", 124, "CA", 2, "89999"));
                violations.add(new ParkingViolation("2023-01-03", 20.0f, "desc", 125, "PA", 3, "89999"));
                map.put("89999", violations);
                return map;
            }
        };

        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertEquals(1, result.size());
        assertTrue(result.contains("89999 0.7000"));
    }

    @Test
    void testNullFines() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 100);
                return map;
            }
        };

        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", null, "desc", 123, "PA", 1, "89999"));
                violations.add(new ParkingViolation("2023-01-02", 50.0f, "desc", 124, "PA", 2, "89999"));
                map.put("89999", violations);
                return map;
            }
        };

        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertEquals(1, result.size());
        assertTrue(result.contains("89999 0.5000"));
    }

    @Test
    void testAllNullFines() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 100);
                return map;
            }
        };

        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", null, "desc", 123, "PA", 1, "89999"));
                map.put("89999", violations);
                return map;
            }
        };

        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertTrue(result.isEmpty());
    }

    @Test
    void testZeroPopulation() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 0);
                return map;
            }
        };

        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", 50.0f, "desc", 123, "PA", 1, "89999"));
                map.put("89999", violations);
                return map;
            }
        };

        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertTrue(result.isEmpty());
    }

    @Test
    void testMultipleZips() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 100);
                map.put("88999", 200);
                map.put("88899", 50);
                return map;
            }
        };

        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                
                ArrayList<ParkingViolation> v1 = new ArrayList<>();
                v1.add(new ParkingViolation("2023-01-01", 50.0f, "desc", 123, "PA", 1, "89999"));
                map.put("89999", v1);
                
                ArrayList<ParkingViolation> v2 = new ArrayList<>();
                v2.add(new ParkingViolation("2023-01-02", 100.0f, "desc", 124, "PA", 2, "88999"));
                map.put("88999", v2);
                
                return map;
            }
        };

        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertEquals(2, result.size());
        assertTrue(result.contains("89999 0.5000"));
        assertTrue(result.contains("88999 0.5000"));
    }

    @Test
    void testCaching() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 100);
                return map;
            }
        };

        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", 50.0f, "desc", 123, "PA", 1, "89999"));
                map.put("89999", violations);
                return map;
            }
        };

        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result1 = fpc.getFinesPerZip();
        TreeSet<String> result2 = fpc.getFinesPerZip();
        
        assertSame(result1, result2);
        assertEquals(1, result1.size());
        assertTrue(result1.contains("89999 0.5000"));
    }

    @Test
    void testLargeFine() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 1000);
                return map;
            }
        };

        ParkingViolationReader pvr = new ParkingViolationReader("csv", "file") {
            @Override
            public Map<String, ArrayList<ParkingViolation>> getViolations() {
                Map<String, ArrayList<ParkingViolation>> map = new TreeMap<>();
                ArrayList<ParkingViolation> violations = new ArrayList<>();
                violations.add(new ParkingViolation("2023-01-01", 500.0f, "desc", 123, "PA", 1, "89999"));
                map.put("89999", violations);
                return map;
            }
        };

        FinesPerCapita fpc = new FinesPerCapita(pr, pvr);
        TreeSet<String> result = fpc.getFinesPerZip();
        assertEquals(1, result.size());
        assertTrue(result.contains("89999 0.5000"));
    }

}
