package municipaldata.processor;
import municipaldata.data.PopulationReader;

public class TotalPopulation {
    private PopulationReader populationReader;

    public TotalPopulation(PopulationReader populationReader) {
        this.populationReader = populationReader;
    }

    public int getTotalPopulation() {
        int totalPopulation = 0;
        try {
            for (int population : populationReader.readPopulationData().values()) {
                totalPopulation += population;
            }
        } catch (Exception e) {
            System.err.println("Error calculating total population: " + e.getMessage());
        }
        return totalPopulation;
    }
}
