package fileio;

import entities.EnergyType;
import updates.EnergyPerDistributorChanges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Producer {
    private final long id;
    private final EnergyType energyType;
    private final long maxDistributor;
    private final double priceKW;
    private long energyPerDistributor;
    private final ArrayList<Distributor> currentDistributors;
    private final HashMap<Integer, ArrayList<Distributor>> distributors;

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

    public long getId() {
        return id;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public long getMaxDistributor() {
        return maxDistributor;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public ArrayList<Distributor> getCurrentDistributors() {
        return currentDistributors;
    }

    public Map<Integer, ArrayList<Distributor>> getDistributors() {
        return distributors;
    }

    public final void changeEnergyPerDistributor(final ArrayList<EnergyPerDistributorChanges>
                                                         changes) {
        for (EnergyPerDistributorChanges change : changes) {
            if (change.getId() == this.getId()) {
                setEnergyPerDistributor(change.getEnergyPerDistributor());
            }
        }
    }

    public final void removeDisributor(final Distributor distributor) {
        if (this.getCurrentDistributors() != null) {
            this.getCurrentDistributors().remove(distributor);
        }
    }

    public final void addCurrentDistributorsToArchive(int month) {
        getDistributors().put(month - 1, getCurrentDistributors());
    }
}
