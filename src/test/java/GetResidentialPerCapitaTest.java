import municipaldata.common.*;
import municipaldata.data.*;
import municipaldata.processor.*;

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
        int result = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, null);
        assertEquals(0, result);
    }

    @Test
    void testEmptyZip() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("89999", 100);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "89999"));
                map.put("89999", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, "");
        assertEquals(0, result);
    }

    @Test
    void testShortZip() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("88899", 100);
                return map; 
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "88899"));
                map.put("88899", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, "8889");
        assertEquals(0, result);
    }

    @Test
    void testLongZip() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("10101", 100);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                System.out.println("getPropertyValues() called!"); 
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "10101"));
                map.put("10101", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, "10101011111");
        assertEquals(2, result);  
    }

    @Test
    void testRecordCtNotZero() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("29292", 100);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "29292"));
                values.add(new PropertyValue(300.0, 250.0, "29292"));
                map.put("29292", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, "29292");
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
                values.add(new PropertyValue(200.0, 150.0, "30303"));
                map.put("30303", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, "30303");
        assertEquals(0, result);    
    }

    @Test
    void testPopulationZero() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("40404", 0);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "40404"));
                map.put("40404", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, "40404");
        assertEquals(0, result);    
    }

    @Test
    void testRecordCtZero() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("50505", 100);
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
        int result = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, "50505");
        assertEquals(0, result);    
    }

    @Test
    void testValidRecordAndPop () {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("59595", 50);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "59595"));
                values.add(new PropertyValue(300.0, 250.0, "59595"));
                map.put("59595", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, "59595");
        assertEquals(10, result);    
    }

    @Test
    void testCaching() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("12121", 50);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(200.0, 150.0, "12121"));
                values.add(new PropertyValue(300.0, 250.0, "12121"));
                map.put("12121", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result1 = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, "12121");
        int result2 = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, "12121");
        assertEquals(10, result1);
        assertEquals(10, result2);
    }

    @Test
    void testRecordTotalZeroWithValidPop() {
        PopulationReader pr = new PopulationReader("file") {
            @Override
            public Map<String, Integer> getPopulationData() {
                Map<String, Integer> map = new TreeMap<>();
                map.put("79797", 100);
                return map;
            }
        };
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(null, null, "79797"));
                map.put("79797", values);
                return map;
            }
        };
        ResidentialPerCapita rpc = new ResidentialPerCapita(pr, pvr);
        int result = rpc.getPerCapita(ResidentialMode.MARKET_VALUE, "79797");
        assertEquals(0, result);
    }

}