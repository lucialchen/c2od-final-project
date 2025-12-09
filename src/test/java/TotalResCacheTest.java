import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import municipaldata.common.*;
import municipaldata.data.*;
import municipaldata.processor.*;

import java.util.*;

public class TotalResCacheTest {

    @Test
    void testGetInstancesSame() {
        TotalResidentialCache cache1 = TotalResidentialCache.getInstance(ResidentialMode.TOTAL_LIVABLE_AREA);
        TotalResidentialCache cache2 = TotalResidentialCache.getInstance(ResidentialMode.TOTAL_LIVABLE_AREA);
        assertSame(cache1, cache2);
    }

    @Test
    void testGetInstancesDifferent() {
        TotalResidentialCache cache1 = TotalResidentialCache.getInstance(ResidentialMode.TOTAL_LIVABLE_AREA);
        TotalResidentialCache cache2 = TotalResidentialCache.getInstance(ResidentialMode.MARKET_VALUE);
        assertNotSame(cache1, cache2);
    }

    @Test
    void testGetMarketValue() {
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(100.0, 50.0, "98999"));
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        TotalResidentialCache cache = TotalResidentialCache.getInstance(ResidentialMode.MARKET_VALUE);
        CacheRecord record = cache.get("98999", pvr);
        assertNotNull(record);
        assertEquals(150.0, record.getTotal());
    }

    @Test
    void testGetTotalLivable() {
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(100.0, 50.0, "98999"));
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        TotalResidentialCache cache = TotalResidentialCache.getInstance(ResidentialMode.TOTAL_LIVABLE_AREA);
        CacheRecord record = cache.get("98999", pvr);
        assertNotNull(record);
        assertEquals(100.0, record.getTotal());
    }

    @Test
    void testRetrieveFromCacheMV() {
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(100.0, 50.0, "98999"));
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        TotalResidentialCache cache = TotalResidentialCache.getInstance(ResidentialMode.MARKET_VALUE);
        // First call to populate cache
        CacheRecord record1 = cache.get("98999", pvr);
        // Second call should retrieve from cache
        CacheRecord record2 = cache.get("98999", pvr);
        assertSame(record1, record2);
    }

    @Test
    void testRetrieveFromCacheTLA() {
        PropertyValueReader pvr = new PropertyValueReader("file") {
            @Override
            public Map<String, ArrayList<PropertyValue>> getPropertyValues() {
                Map<String, ArrayList<PropertyValue>> map = new TreeMap<>();
                ArrayList<PropertyValue> values = new ArrayList<>();
                values.add(new PropertyValue(100.0, 50.0, "98999"));
                values.add(new PropertyValue(200.0, 150.0, "98999"));
                map.put("98999", values);
                return map;
            }
        };
        TotalResidentialCache cache = TotalResidentialCache.getInstance(ResidentialMode.TOTAL_LIVABLE_AREA);
        // First call to populate cache
        CacheRecord record1 = cache.get("98999", pvr);
        // Second call should retrieve from cache
        CacheRecord record2 = cache.get("98999", pvr);
        assertSame(record1, record2);
    }

}
