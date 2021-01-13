package updates;

public class EnergyPerDistributorChanges {
    private final long id;
    private final long energyPerDistributor;

    public EnergyPerDistributorChanges(long id, long energyPerDistributor) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
    }

    public long getId() {
        return id;
    }

    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }
}
