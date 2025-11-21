package municipaldata.processor;

import municipaldata.data.PropertyValueReader;

public class AverageResidential {

    private PropertyValueReader propertyValueReader;
    private TotalMarketValueCache totalMarketValueCache;

    public AverageResidential(PropertyValueReader pvr, TotalMarketValueCache tmvc) {
        propertyValueReader = pvr;
        totalMarketValueCache = tmvc;
    }

}
