package municipaldata.processor;

import municipaldata.common.PropertyValue;
import municipaldata.data.PropertyValueReader;

import java.util.*;

public class TotalMarketValueCache {

    private static TotalMarketValueCache instance;
    private Map<String, Float> map;

    public TotalMarketValueCache() {
        map = new HashMap<>();
    }

    public static TotalMarketValueCache getInstance() {
        if (instance == null) {
            instance = new TotalMarketValueCache();
        }
        return instance;
    }

    public Float checkMarketValue(String zip) {
        return map.get(zip);
    }

    public void updateMarketValue(String zip, float totalValue) {
        map.put(zip, totalValue);
    }

    public Float calculateMarketValue(String zip, PropertyValueReader pvr) {
        Map<String, ArrayList<PropertyValue>> map = pvr.getPropertyValues();
        ArrayList<PropertyValue> propertyValues = map.get(zip);
        float total = 0.0F;

        if (propertyValues != null) {
            for (PropertyValue value : propertyValues) {
                total += value.getMarketValue();
            }
        }

        updateMarketValue(zip, total);
        return total;
    }
}
