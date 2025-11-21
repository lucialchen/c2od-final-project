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

        totalPopulation = populationReader.getPopulationData()
                .values()
                .stream()
                .map(i -> i)
                .reduce(0, (a, b) -> a + b);
        return totalPopulation;
    }
}
