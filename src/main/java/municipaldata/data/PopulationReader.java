package municipaldata.data;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PopulationReader {

    private String filePath;
    protected Map<Integer, Integer> populationData;

    public PopulationReader(String filePath) {
        this.filePath = filePath;
        this.populationData = new HashMap<>();
    }

    public Map<Integer, Integer> readPopulationData() throws IOException {
        Map<Integer, Integer> populationData = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",", -1);
                
                if (fields.length != 2) {
                    continue;
                }

                try {
                    int zip = Integer.parseInt(fields[0]);
                    int population = Integer.parseInt(fields[1]);
                    populationData.put(zip, population);
                } catch (NumberFormatException e) {
                    continue;
                }
            }
        } catch (IOException e) {
            System.err.println("Error opening or reading population file: " + filePath);
            return populationData;
        }
        return populationData;
    }
}
