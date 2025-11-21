package municipaldata.common;

public class PropertyValue {

    float market_value;
    float total_livable_area;
    String zip_code;

    public PropertyValue(float market_value, float total_livable_area, String zip_code) {
        this.market_value = market_value;
        this.total_livable_area = total_livable_area;
        this.zip_code = zip_code;
    }

    public String getZipCode() {
        return zip_code;
    }

    public float getMarketValue() {
        return market_value;
    }

    public float getTotalLivableArea() {
        return total_livable_area;
    }
}
