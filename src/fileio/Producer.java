package fileio;

import entities.EnergyType;
import updates.EnergyPerDistributorChanges;

import java.util.ArrayList;
import java.util.Map;

public class Producer {
    private final long id;
    private final EnergyType energyType;
    private final long maxDistributor;
    private final double priceKW;
    private long energyPerDistributor;
    private Map<Integer, Distributor> distributors;

    public Producer(long id, EnergyType energyType, long maxDistributor, double priceKW, long
                    energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributor = maxDistributor;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
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

    public Map<Integer, Distributor> getDistributors() {
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
        if (this.getDistributors() != null) {
            this.getDistributors().remove(distributor);
        }
    }
}
