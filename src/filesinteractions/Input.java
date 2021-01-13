package filesinteractions;

import fileio.Consumer;
import fileio.Distributor;
import fileio.Producer;
import updates.MonthlyUpdates;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains information about input
 * <p>
 * DO NOT MODIFY
 */
public final class Input {

    /**
     * List of consumers
     */
    private final List<Consumer> consumersData;
    /**
     * List of distributors
     */
    private final List<Distributor> distributorsData;
    private final List<Producer> producersData;
    private final List<MonthlyUpdates> monthlyUpdates;
    private long numberOfTurns;

    public Input() {
        this.numberOfTurns = 0;
        this.consumersData = new ArrayList<>();
        this.distributorsData = new ArrayList<>();
        this.producersData = new ArrayList<>();
        this.monthlyUpdates = new ArrayList<>();
    }

    public Input(final long numberOfTurns,
                 final List<Consumer> consumers,
                 final List<Distributor> distributors,
                 final List<Producer> producers,
                 final List<MonthlyUpdates> monthlyUpdates) {

        this.numberOfTurns = numberOfTurns;
        this.consumersData = consumers;
        this.distributorsData = distributors;
        this.producersData = producers;
        this.monthlyUpdates = monthlyUpdates;
    }

    public long getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(long numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public List<Consumer> getConsumersData() {
        return consumersData;
    }

    public List<Distributor> getDistributorsData() {
        return distributorsData;
    }

    public List<Producer> getProducersData() {
        return producersData;
    }

    public List<MonthlyUpdates> getMonthlyUpdates() {
        return monthlyUpdates;
    }
}
