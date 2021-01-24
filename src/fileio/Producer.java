package fileio;

import entities.EnergyType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Producer {
    private final long id;
    private final EnergyType energyType;
    private final long maxDistributor;
    private final double priceKW;
    private final ArrayList<Distributor> currentDistributors;
    private final HashMap<Integer, ArrayList<Distributor>> distributors;
    private long energyPerDistributor;

    public Producer(long id, EnergyType energyType, long maxDistributor, double priceKW, long
            energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributor = maxDistributor;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
        this.currentDistributors = new ArrayList<>();
        this.distributors = new HashMap<>();
    }

    public final long getId() {
        return id;
    }

    public final EnergyType getEnergyType() {
        return energyType;
    }

    public final long getMaxDistributor() {
        return maxDistributor;
    }

    public final double getPriceKW() {
        return priceKW;
    }

    public final long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public final void setEnergyPerDistributor(long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public final ArrayList<Distributor> getCurrentDistributors() {
        return currentDistributors;
    }

    public final Map<Integer, ArrayList<Distributor>> getDistributors() {
        return distributors;
    }

    /**
     * adds distributor to producer's database by month
     */
    public final void addCurrentDistributorsToArchive(int month) {
        getDistributors().put(month - 1, getCurrentDistributors());
    }
}
