package fileio;

import strategies.EnergyChoiceStrategyType;

public final class DistributorFactory {
    private static DistributorFactory instance = null;

    private DistributorFactory() {
    }

    /**
     * Singleton getInstance Method
     */
    public static DistributorFactory getInstance() {
        if (instance == null) {
            instance = new DistributorFactory();
        }
        return instance;
    }

    /**
     * Factory Method used to create a new distributor
     */
    public Distributor createDistributor(final long id, final long contractLength,
                                         final long initialBudget,
                                         final long initialInfrastructureCost,
                                         final long energyNeededKW, EnergyChoiceStrategyType
                                                 producerStrategy) {
        return new Distributor(id, contractLength, initialBudget, initialInfrastructureCost,
                energyNeededKW, producerStrategy);
    }
}
