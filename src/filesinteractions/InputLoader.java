package filesinteractions;

import entities.EnergyType;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import strategies.EnergyChoiceStrategyType;
import updates.CostsChanges;
import updates.MonthlyUpdates;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class reads and parses the data from the tests
 * <p>
 * DO NOT MODIFY
 */
@SuppressWarnings("unchecked")
public final class InputLoader {
    /**
     * The path to the input file
     */
    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * The method reads the database
     *
     * @return an Input object
     */
    public Input readData() {
        JSONParser jsonParser = new JSONParser();
        List<Consumer> consumers = new ArrayList<>();
        List<Distributor> distributors = new ArrayList<>();
        List<Producer> producers = new ArrayList<>();
        List<MonthlyUpdates> monthlyUpdates = new ArrayList<>();
        long numberOfTurns;

        try {
            // Parsing the contents of the JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(inputPath));
            numberOfTurns = (long) (jsonObject.get("numberOfTurns"));
            JSONObject initialData = (JSONObject) jsonObject.get("initialData");
            JSONArray jsonConsumers = (JSONArray) initialData.get("consumers");
            JSONArray jsonDistributors = (JSONArray) initialData.get("distributors");
            JSONArray jsonProducers = (JSONArray) initialData.get("producers");

            if (jsonConsumers != null) {
                ConsumerFactory consumerFactory = ConsumerFactory.getInstance();
                for (Object jsonConsumer : jsonConsumers) {
                    consumers.add(consumerFactory.createConsumer(
                            (long) ((JSONObject) jsonConsumer).get("id"),
                            (long) ((JSONObject) jsonConsumer).get("initialBudget"),
                            (long) ((JSONObject) jsonConsumer).get("monthlyIncome")));
                }
            }

            if (jsonDistributors != null) {
                DistributorFactory distributorFactory = DistributorFactory.getInstance();
                for (Object jsonDistributor : jsonDistributors) {
                    distributors.add(distributorFactory.createDistributor(
                            (long) ((JSONObject) jsonDistributor).get("id"),
                            (long) ((JSONObject) jsonDistributor).get("contractLength"),
                            (long) ((JSONObject) jsonDistributor).get("initialBudget"),
                            (long) ((JSONObject) jsonDistributor).get("initialInfrastructureCost"),
                            (long) ((JSONObject) jsonDistributor).get("energyNeededKW"),
                            EnergyChoiceStrategyType.valueOf(((JSONObject) jsonDistributor).
                                    get("producerStrategy").toString())));
                }
            }

            if(jsonProducers != null) {
                for (Object jsonProducer : jsonProducers) {
                    producers.add(new Producer(((long) ((JSONObject) jsonProducer).get("id")),
                            EnergyType.valueOf(((JSONObject) jsonProducer).
                                    get("energyType").toString()),
                            (long) ((JSONObject) jsonProducer).get("maxDistributors"),
                            (double) ((JSONObject) jsonProducer).get("priceKW"),
                            (long) ((JSONObject) jsonProducer).get("energyPerDistributor")));
                }
            }

            JSONArray jsonUpdates = (JSONArray) jsonObject.get("monthlyUpdates");

            for (JSONObject monthlyUpdate : (Iterable<JSONObject>) jsonUpdates) {
                MonthlyUpdates update = new MonthlyUpdates();
                JSONArray newConsumers = (JSONArray) monthlyUpdate.get("newConsumers");
                for (JSONObject newConsumer : (Iterable<JSONObject>) newConsumers) {
                    update.getNewConsumers().add(new Consumer(Long.parseLong(newConsumer.get("id").
                            toString()),
                            Long.parseLong(newConsumer.get("initialBudget").toString()),
                            Long.parseLong(newConsumer.get("monthlyIncome").toString())));
                }
                org.json.simple.JSONArray costsChanges = (JSONArray) monthlyUpdate.
                        get("distributorChanges");
                for (JSONObject costsChange : (Iterable<JSONObject>) costsChanges) {
                    update.getCostsChanges().add(new CostsChanges(Long.parseLong(costsChange.
                            get("id").toString()),
                            Long.parseLong(costsChange.get("infrastructureCost").toString())));
                }
                monthlyUpdates.add(update);
            }
            return new Input(numberOfTurns, consumers, distributors, producers, monthlyUpdates);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return new Input(0, consumers, distributors, producers, monthlyUpdates);

    }
}
