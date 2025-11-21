package municipaldata.processor;
import municipaldata.data.PopulationReader;

public class TotalPopulation {
    private PopulationReader populationReader;
    private Integer totalPopulation;

    public TotalPopulation(PopulationReader populationReader) {
        this.populationReader = populationReader;
    }

    public int getTotalPopulation() {
        if (totalPopulation != null) {
            return totalPopulation;
        }

        int total = 0;
        try {
            for (int population : populationReader.getPopulationData().values()) {
                total += population;
            }
        } catch (Exception e) {
            System.err.println("Error calculating total population: " + e.getMessage());
        }
        totalPopulation = total;
        return totalPopulation;
    }
}
