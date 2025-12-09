package municipaldata.common;

public class PropertyValue {

    Double market_value;
    Double total_livable_area;
    String zip_code;

    public PropertyValue(Double market_value, Double total_livable_area, String zip_code) {
        this.market_value = market_value;
        this.total_livable_area = total_livable_area;
        this.zip_code = zip_code;
    }

    public String getZipCode() {
        return zip_code;
    }

    public Double getMarketValue() {
        return market_value;
    }

    public Double getTotalLivableArea() {
        return total_livable_area;
    }
}
