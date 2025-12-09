package test;
import municipaldata.data.PopulationReader;
import municipaldata.processor.TotalPopulation;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GetTotPopulationTest {

    @Test
    void testTotPopNull() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public java.util.Map<String, Integer> getPopulationData() {
                return new java.util.TreeMap<>();
            }
        };
        TotalPopulation tp = new TotalPopulation(pr);
        int result = tp.getTotalPopulation();
        assertEquals(0, result);
    }

    @Test
    void testTotPopNotNull() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public java.util.Map<String, Integer> getPopulationData() {
                java.util.Map<String, Integer> map = new java.util.TreeMap<>();
                map.put("99909", 100);
                map.put("80009", 200);
                return map;
            }
        };
        TotalPopulation tp = new TotalPopulation(pr);
        int result = tp.getTotalPopulation();
        int result2 = tp.getTotalPopulation();
        assertEquals(result, result2);
        assertEquals(300, result);
    }

    @Test
    void testNullPopEntries() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public java.util.Map<String, Integer> getPopulationData() {
                java.util.Map<String, Integer> map = new java.util.TreeMap<>();
                map.put("99909", null);
                map.put("80009", 200);
                return map;
            }
        };
        TotalPopulation tp = new TotalPopulation(pr);
        int result = tp.getTotalPopulation();
        assertEquals(200, result);
    }
}
