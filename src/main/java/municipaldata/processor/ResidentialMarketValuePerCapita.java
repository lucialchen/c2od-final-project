package municipaldata.processor;

import municipaldata.common.PropertyValue;
import municipaldata.common.ResidentialMode;
import municipaldata.data.PopulationReader;
import municipaldata.data.PropertyValueReader;

import java.util.TreeMap;

public class ResidentialMarketValuePerCapita {

    private PopulationReader populationReader;
    private PropertyValueReader propertyValueReader;

    public ResidentialMarketValuePerCapita(PopulationReader pr, PropertyValueReader pvr) {
        populationReader = pr;
        propertyValueReader = pvr;
    }

    public int getMarketValuePerCapita(String zip) {
        if (zip.length() < 5) {
            return 0;
        }
        else if (zip.length() > 5) {
            zip = zip.substring(0, 5);
        }

        TotalResidentialCache cache = TotalResidentialCache.getInstance(ResidentialMode.MARKET_VALUE);
        CacheRecord marketValue = cache.get(zip, propertyValueReader);

        TreeMap<String, Integer> populationsMap = (TreeMap<String, Integer>) populationReader.getPopulationData();
        Integer pop = populationsMap.get(zip);

        if (marketValue.getTotal() != 0 && pop != null && pop != 0) {
            return (int) Math.ceil(marketValue.getTotal() / pop);
        } else {
            return 0;
        }
    }

}


