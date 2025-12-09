package municipaldata.data;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PopulationReader {

    private String filePath;
    private Map<String, Integer> map;

    public PopulationReader(String filePath) {
        this.filePath = filePath;
        map = readPopulationData();
    }

    private Map<String, Integer> readPopulationData()  {
        Map<String, Integer> populationData = new TreeMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(" ", -1);
                
                if (fields.length != 2) {
                    continue;
                }

                try {
                    String zip = fields[0];
                    int population = Integer.parseInt(fields[1]);
                    if (population <= 0 || zip.isEmpty() || zip.length() != 5) {
                        continue;
                    }
                    populationData.put(zip, population);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Error opening or reading population file: " + filePath);
            return populationData;
        }
        return populationData;
    }

    public Map<String, Integer> getPopulationData() {
        return map;
    }

}
