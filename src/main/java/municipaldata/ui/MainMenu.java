package municipaldata.ui;

import municipaldata.common.ResidentialMode;
import municipaldata.processor.*;

import java.util.*;

public class MainMenu {

    protected TotalPopulation totalPopulation;
    protected FinesPerCapita finesPerCapita;
    protected AverageResidential averageResidential;
    protected ResidentialPerCapita residentialMarketValuePerCapita;

    public MainMenu(TotalPopulation tp, FinesPerCapita fpc, AverageResidential ar, ResidentialPerCapita rmvpc) {
        totalPopulation = tp;
        finesPerCapita = fpc;
        averageResidential = ar;
        residentialMarketValuePerCapita = rmvpc;
    }

    public void start() {

        Scanner in = new Scanner(System.in);

        while (true) {

            System.out.print("Please enter...\n" +
                    "\"1\" for Total Population for All Zip Codes\n" +
                    "\"2\" for Fines Per Capita\n" +
                    "\"3\" for Average Residential Market Value\n" +
                    "\"4\" for Average Residential Total Livable Area\n" +
                    "\"5\" for Residential Market Value Per Capita\n" +
                    "\"6\" for Residential Total Livable Area Per Capita\n" +
                    "\"0\" to end the program\n");

            String response = in.nextLine();
            int selection;

            try {
                selection = Integer.parseInt(response);
            } catch (NumberFormatException nfe) {
                continue;
            }

            if (selection == 0) {
                System.exit(0);
            } else if (selection == 1) {
                System.out.println(totalPopulation.getTotalPopulation());
            } else if (selection == 2) {
                TreeSet<String> finesPerZip = finesPerCapita.getFinesPerZip();
                for (String line : finesPerZip) {
                    System.out.println(line);
                }
            } else if (selection == 3 || selection == 4 || selection == 5 || selection == 6) {
                System.out.println("Please enter a ZIP code:");
                String zip = in.nextLine();

                if (zip == null || zip.isBlank() || zip.isEmpty()) {
                    continue;
                }
                try {
                    int i = Integer.parseInt(zip);
                    if (i <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException nfe) {
                    continue;
                }

                if (selection == 3) {
                    System.out.println(averageResidential.getAverageResidential(ResidentialMode.MARKET_VALUE, zip));
                } else if (selection == 4) {
                    System.out.println(averageResidential.getAverageResidential(ResidentialMode.TOTAL_LIVABLE_AREA, zip));
                } else if (selection == 5) {
                    System.out.println(residentialMarketValuePerCapita.getPerCapita(ResidentialMode.MARKET_VALUE, zip));
                } else if (selection == 6) {
                    System.out.println(residentialMarketValuePerCapita.getPerCapita(ResidentialMode.TOTAL_LIVABLE_AREA, zip));
                }
            }

        }
    }
}

