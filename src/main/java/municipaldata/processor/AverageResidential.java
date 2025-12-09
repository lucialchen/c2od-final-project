package municipaldata.processor;

import municipaldata.common.PropertyValue;
import municipaldata.common.ResidentialMode;
import municipaldata.data.PropertyValueReader;

import java.util.*;

public class AverageResidential {

    private PropertyValueReader propertyValueReader;

    public AverageResidential(PropertyValueReader pvr) {
        propertyValueReader = pvr;
    }

    public int getAverageResidential(ResidentialMode mode, String zip) {
        if (zip == null || zip.isEmpty()) {
            return 0;
        }
        
        if (zip.length() < 5) {
            return 0;
        }
        else if (zip.length() > 5) {
            zip = zip.substring(0, 5);
        }

        TotalResidentialCache cache = TotalResidentialCache.getInstance(mode);
        CacheRecord record = cache.get(zip, propertyValueReader);

        if (record.getCount() != 0) {
            return (int) Math.ceil(record.getTotal() / record.getCount());
        }
        else {
            return 0;
        }
    }
}
