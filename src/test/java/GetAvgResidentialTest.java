import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import municipaldata.common.*;
import municipaldata.data.*;
import municipaldata.processor.*;

import java.util.*;

public class GetAvgResidentialTest {

    @Test
    void testNullZip() {
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(100.0, 50.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        AverageResidential avgRes = new AverageResidential(pvr);
        int result = avgRes.getAverageResidential(municipaldata.common.ResidentialMode.MEDIAN, null);
        assertEquals(0, result);
    }

    @Test
    void testEmptyZip() {
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(100.0, 50.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        AverageResidential avgRes = new AverageResidential(pvr);
        int result = avgRes.getAverageResidential(municipaldata.common.ResidentialMode.MEDIAN, "");
        assertEquals(0, result);
    }

    @Test
    void testShortZip() {
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(100.0, 50.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        AverageResidential avgRes = new AverageResidential(pvr);
        int result = avgRes.getAverageResidential(municipaldata.common.ResidentialMode.MEDIAN, "9899");
        assertEquals(0, result);
    }

    @Test
    void testLongZip() {
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(100.0, 50.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        municipaldata.processor.AverageResidential avgRes = new municipaldata.processor.AverageResidential(pvr);
        int result = avgRes.getAverageResidential(municipaldata.common.ResidentialMode.MEDIAN, "989999099");
        assertEquals(100, result);
    }

    @Test
    void testRecordCtNotZero() {
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(100.0, 50.0, "89888"));
                values.add(new PropertyValue(200.0, 75.0, "89888"));
                map.put("89888", values);
                return map;
            }
        };
        municipaldata.processor.AverageResidential avgRes = new municipaldata.processor.AverageResidential(pvr);
        int result = avgRes.getAverageResidential(municipaldata.common.ResidentialMode.MEDIAN, "89888");
        assertEquals(150, result);
    }

    @Test
    void testRecordCtZero() {
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                return map;
            }
        };
        municipaldata.processor.AverageResidential avgRes = new municipaldata.processor.AverageResidential(pvr);
        int result = avgRes.getAverageResidential(municipaldata.common.ResidentialMode.MEDIAN, "90000");
        assertEquals(0, result);
    }
            
}
