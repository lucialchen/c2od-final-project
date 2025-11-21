package municipaldata.common;

public class PropertyValue {

    double market_value;
    double total_livable_area;
    String zip_code;

    public PropertyValue(double market_value, double total_livable_area, String zip_code) {
        this.market_value = market_value;
        this.total_livable_area = total_livable_area;
        this.zip_code = zip_code;
    }

    public String getZipCode() {
        return zip_code;
    }

    public double getMarketValue() {
        return market_value;
    }

    public double getTotalLivableArea() {
        return total_livable_area;
    }
}
