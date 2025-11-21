package municipaldata.ui;

import municipaldata.processor.*;

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

    }
}

