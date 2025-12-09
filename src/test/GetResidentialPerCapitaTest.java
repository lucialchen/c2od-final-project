package test;

import municipaldata.common.ResidentialMode;
import municipaldata.data.PopulationReader;
import municipaldata.data.PropertyValueReader;

import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

public class GetResidentialPerCapitaTest {

    @Test
    void testNullZip() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("98999", 100);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MEDIAN, null);
        assertEquals(0, result);
    }

    @Test
    void testEmptyZip() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("98999", 100);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MEDIAN, "");
        assertEquals(0, result);
    }

    @Test
    void testShortZip() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("98999", 100);
                return map; 
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MEDIAN, "9899");
        assertEquals(0, result);
    }

    @Test
    void testLongZip() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("98999", 100);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MEDIAN, "989999099");
        assertEquals(2, result);    
    }

    @Test
    void testRecordCtNotZero() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("98999", 100);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                values.add(new PropertyValue(300.0, 250.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MEDIAN, "98999");
        assertEquals(5, result);    
    }

    @Test
    void testPopulationNull() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MEDIAN, "98999");
        assertEquals(0, result);    
    }

    @Test
    void testPopulationZero() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("98999", 0);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MEDIAN, "98999");
        assertEquals(0, result);    
    }

    @Test
    void testRecordCtZero() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("98999", 100);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MEDIAN, "98999");
        assertEquals(0, result);    
    }

    @Test
    void testValidRecordAndPop () {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("98999", 50);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                values.add(new PropertyValue(300.0, 250.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MEDIAN, "98999");
        assertEquals(10, result);    
    }

    @Test
    void testCaching() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("98999", 50);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                values.add(new PropertyValue(300.0, 250.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result1 = rpc.getPerCapita(ResidentialMode.MEDIAN, "98999");
        int result2 = rpc.getPerCapita(ResidentialMode.MEDIAN, "98999");
        assertEquals(10, result1);
        assertEquals(10, result2);
    }

}
