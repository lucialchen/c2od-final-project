package municipaldata.processor;

import municipaldata.common.ResidentialMode;
import municipaldata.data.PopulationReader;
import municipaldata.data.PropertyValueReader;

import java.util.TreeMap;

public class ResidentialPerCapita {

    private PopulationReader populationReader;
    private PropertyValueReader propertyValueReader;

    public ResidentialPerCapita(PopulationReader pr, PropertyValueReader pvr) {
        populationReader = pr;
        propertyValueReader = pvr;
    }

    public int getPerCapita(ResidentialMode mode, String zip) {
        if (zip.length() < 5) {
            return 0;
        }
        else if (zip.length() > 5) {
            zip = zip.substring(0, 5);
        }

        TotalResidentialCache cache = TotalResidentialCache.getInstance(mode);
        CacheRecord record = cache.get(zip, propertyValueReader);

        TreeMap<String, Integer> populationsMap = (TreeMap<String, Integer>) populationReader.getPopulationData();
        Integer pop = populationsMap.get(zip);

        if (record.getTotal() != 0 && pop != null && pop != 0) {
            return (int) Math.ceil(record.getTotal() / pop);
        } else {
            return 0;
        }
    }

}


