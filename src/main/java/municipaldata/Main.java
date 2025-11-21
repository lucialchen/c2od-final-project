package municipaldata;

import municipaldata.data.ParkingViolationReader;
import municipaldata.data.PopulationReader;
import municipaldata.data.PropertyValueReader;
import municipaldata.processor.*;
import municipaldata.ui.MainMenu;

public class Main {
    public static void main(String[] args) {

        if (args.length != 4) {
            System.out.println("Usage: java Main [\"csv\" or \"json\"] [parking violations file] [property values file] [population file]");
            System.exit(1);
        }

        String csvOrJson = args[0];

        if (!(csvOrJson.equals("csv") || csvOrJson.equals("json"))) {
            System.out.println("Usage: java Main [\"csv\" or \"json\"] [parking violations file] [property values file] [population file]");
            System.exit(1);
        }

        String parkingViolationsFile = args[1];
        String propertyValuesFile = args[2];
        String populationFile = args[3];

        // Data tier
        ParkingViolationReader violations = new ParkingViolationReader(csvOrJson, parkingViolationsFile);
        PropertyValueReader properties = new PropertyValueReader(propertyValuesFile);
        PopulationReader populations = new PopulationReader(populationFile);

        // Processing tier
        TotalMarketValueCache tmvc = TotalMarketValueCache.getInstance();
        TotalPopulation tp = new TotalPopulation(populations);
        FinesPerCapita fpc = new FinesPerCapita(populations, violations);
        AverageResidential ar = new AverageResidential(properties, tmvc);
        ResidentialMarketValuePerCapita rmvpc = new ResidentialMarketValuePerCapita(populations, properties, tmvc);

        // UI tier
        MainMenu menu = new MainMenu(tp, fpc, ar, rmvpc);

        menu.start();

    }
}
