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

    public Double getData(ResidentialMode m) {
        if (m == ResidentialMode.MARKET_VALUE) {
            return market_value;
        } else {
            return total_livable_area;
        }
    }
}
