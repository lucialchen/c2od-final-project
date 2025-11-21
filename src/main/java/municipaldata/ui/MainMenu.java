package municipaldata.ui;

import municipaldata.processor.*;

import java.util.*;

public class MainMenu {

    protected TotalPopulation totalPopulation;
    protected FinesPerCapita finesPerCapita;
    protected AverageResidential averageResidential;
    protected ResidentialMarketValuePerCapita residentialMarketValuePerCapita;

    public MainMenu(TotalPopulation tp, FinesPerCapita fpc, AverageResidential ar, ResidentialMarketValuePerCapita rmvpc) {
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
                    "\"0\" to end the program\n");

            String response = in.nextLine();
            int selection;

            try {
                selection = Integer.parseInt(response);
            } catch (NumberFormatException nfe) {
                continue;
            }

            switch (selection) {

            }

        }
    }
}

