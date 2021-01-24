package filesinteractions;

import fileio.Consumer;
import fileio.Distributor;
import fileio.Producer;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PrintEntities {

    /**
     * Method that adds consumers info to the consumer map
     */
    public void printConsumers(final Consumer consumer, final LinkedHashMap<String, Object> map) {
        map.put("id", consumer.getId());
        map.put("isBankrupt", consumer.isBankrupt());
        map.put("budget", consumer.getInitialBudget());
    }

    /**
     * Method that adds distributors info to the distributor map
     */
    public void printDistributors(final Distributor distributor, final LinkedHashMap<String,
            Object> map) {
        map.put("id", distributor.getId());
        map.put("energyNeededKW", distributor.getEnergyNeededKW());
        map.put("contractCost", distributor.getContractCost());
        map.put("budget", distributor.getInitialBudget());
        map.put("producerStrategy", distributor.getProducerStrategy());
        map.put("isBankrupt", distributor.isBankrupt());
    }

    /**
     * Method that adds producers info to the producers map
     */
    public void printProducers(final Producer producer, final LinkedHashMap<String,
            Object> map) {
        map.put("id", producer.getId());
        map.put("maxDistributors", producer.getMaxDistributor());
        map.put("priceKW", producer.getPriceKW());
        map.put("energyType", producer.getEnergyType());
        map.put("energyPerDistributor", producer.getEnergyPerDistributor());
    }

    /**
     * Method that adds distributors info to the producers map
     */
    public void printMonthlyStats(final Producer producer, final LinkedHashMap<String,
            Object> map, final int month) {
        map.put("month", month);
        ArrayList<Integer> distributorsIds = new ArrayList<>();
        for (int i = 0; i < producer.getDistributors().get(month - 1).size(); i++) {
            distributorsIds.add((int) producer.getDistributors().get(month - 1).get(i).getId());
        }
        map.put("distributorsIds", distributorsIds);
    }

    /**
     * Method that adds contract info to the contract map
     */
    public void printContracts(final Consumer consumer, final LinkedHashMap<String, Object> map) {
        map.put("consumerId", consumer.getId());
        map.put("price", consumer.getContractPrice());
        map.put("remainedContractMonths", consumer.getContractLength());
    }
}
