package filesinteractions;

import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.Consumer;
import fileio.Distributor;
import fileio.Producer;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Writer {
    /**
     * method that writes in @param outputFilePath based on given @param input
     */
    public void writeFile(final String outputFilePath, final Input input) {
        LinkedHashMap<String, Object> mapFile = new LinkedHashMap<>();
        List<LinkedHashMap<String, Object>> listConsumers = new ArrayList<>();
        PrintEntities printEntities = new PrintEntities();
        for (Consumer consumer : input.getConsumersData()) {
            LinkedHashMap<String, Object> mapConsumer = new LinkedHashMap<>();
            printEntities.printConsumers(consumer, mapConsumer);
            listConsumers.add(mapConsumer);
        }
        mapFile.put("consumers", listConsumers);
        List<LinkedHashMap<String, Object>> listDistributors = new ArrayList<>();
        for (Distributor distributor : input.getDistributorsData()) {
            LinkedHashMap<String, Object> mapDistributor = new LinkedHashMap<>();
            printEntities.printDistributors(distributor, mapDistributor);

            List<LinkedHashMap<String, Object>> listContracts = new ArrayList<>();
            for (Consumer consumer : distributor.getConsumers()) {
                LinkedHashMap<String, Object> mapContract = new LinkedHashMap<>();
                printEntities.printContracts(consumer, mapContract);
                listContracts.add(mapContract);
            }
            mapDistributor.put("contracts", listContracts);
            listDistributors.add(mapDistributor);
        }
        mapFile.put("distributors", listDistributors);

        List<LinkedHashMap<String, Object>> listProducers = new ArrayList<>();
        for (Producer producer : input.getProducersData()) {
            List<LinkedHashMap<String, Object>> listMonthlyStats = new ArrayList<>();
            LinkedHashMap<String, Object> mapProducers = new LinkedHashMap<>();
            printEntities.printProducers(producer, mapProducers, input);

            /*
            LinkedHashMap<String, Object> mapMonthlyStats = new LinkedHashMap<>();
            printEntities.printMonthlyStats(producer, mapMonthlyStats, input);
            listMonthlyStats.add(mapMonthlyStats);
*/
            if (producer.getDistributors() != null) {
                for (int i = 0; i < producer.getDistributors().size(); i++) {
                    LinkedHashMap<String, Object> mapMonthlyStats = new LinkedHashMap<>();
                    printEntities.printMonthlyStats(producer, mapMonthlyStats, i);
                    listMonthlyStats.add(mapMonthlyStats);
                }
            }
            mapProducers.put("monthlyStats", listMonthlyStats );

            listProducers.add(mapProducers);
        }
        mapFile.put("energyProducers", listProducers);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FileWriter output = new FileWriter(outputFilePath);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(output, mapFile);
            output.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
