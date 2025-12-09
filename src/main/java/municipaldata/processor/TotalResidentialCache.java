package municipaldata.processor;

import municipaldata.common.PropertyValue;
import municipaldata.common.ResidentialMode;
import municipaldata.data.PropertyValueReader;

import java.util.*;

public class TotalResidentialCache {

    private static TotalResidentialCache areaInstance;
    private static TotalResidentialCache valueInstance;

    private ResidentialMode mode;
    private Map<String, CacheRecord> map;

    private TotalResidentialCache(ResidentialMode m) {
        mode = m;
        map = new HashMap<>();
    }

    public static TotalResidentialCache getInstance(ResidentialMode m) {
        if (m == ResidentialMode.TOTAL_LIVABLE_AREA) {
            if (areaInstance == null) {
                areaInstance = new TotalResidentialCache(m);
            }
            return areaInstance;
        }
        else {
            if (valueInstance == null) {
                valueInstance = new TotalResidentialCache(m);
            }
            return valueInstance;
        }
    }

    public CacheRecord check(String zip) {
        return map.get(zip);
    }

    public void update(String zip, CacheRecord val) {
        map.put(zip, val);
    }

    public CacheRecord get(String zip, PropertyValueReader pvr) {

        if (check(zip) != null) {
            return check(zip);
        }

        Map<String, ArrayList<PropertyValue>> map = pvr.getPropertyValues();
        ArrayList<PropertyValue> propertyValues = map.get(zip);

        CacheRecord record;

        // Referenced https://www.geeksforgeeks.org/java/java-util-doublesummarystatistics-class-with-examples/
        if (propertyValues != null) {
            DoubleSummaryStatistics dss;

            dss = propertyValues
                    .stream()
                    .map(pv -> pv.getData(mode))
                    .mapToDouble(d -> d)
                    .summaryStatistics();

            record = new CacheRecord(dss.getCount(), dss.getSum());
        }
        else {
            record = new CacheRecord(0, 0);
        }

        update(zip, record);
        return record;
    }
}
