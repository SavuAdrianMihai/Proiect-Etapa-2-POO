package fileio;

import filesinteractions.Input;
import formulas.Formulas;
import sorts.ProducerSorts;
import strategies.EnergyChoiceStrategyType;
import updates.CostsChanges;

import java.util.ArrayList;

public class Distributor implements DistributorInterface {
    private final long id;
    private final long contractLength;
    private long contractCost;
    private long initialBudget;
    private long initialInfrastructureCost;
    private long initialProductionCost;
    private long previousNumberOfConsumers;
    private ArrayList<Consumer> consumers;
    private ArrayList<Consumer> pastDueConsumers;
    private long energyNeededKW;
    private long energyNeededCurrently;
    private EnergyChoiceStrategyType producerStrategy;
    private ArrayList<Producer> producers;
    private boolean isBankrupt;

    public Distributor() {
        id = 0;
        contractLength = 0;
        contractCost = 0;
        initialBudget = 0;
        initialInfrastructureCost = 0;
        initialProductionCost = 0;
        previousNumberOfConsumers = 0;
        energyNeededCurrently = 0;
    }

    public Distributor(long id, long contractLength, long initialBudget,
                       long initialInfrastructureCost, long energyNeededKW,
                       EnergyChoiceStrategyType producerStrategy) {
        this.id = id;
        this.contractLength = contractLength;
        this.contractCost = 0;
        this.initialBudget = initialBudget;
        this.initialInfrastructureCost = initialInfrastructureCost;
        this.energyNeededKW = energyNeededKW;
        this.energyNeededCurrently = energyNeededKW;
        this.producerStrategy = producerStrategy;
        this.previousNumberOfConsumers = 0;
        this.consumers = new ArrayList<>();
        this.pastDueConsumers = new ArrayList<>();
        this.producers = new ArrayList<>();
        this.isBankrupt = false;
    }

    public final long getId() {
        return id;
    }

    public final long getContractLength() {
        return contractLength;
    }

    public final long getInitialBudget() {
        return initialBudget;
    }

    public final void setInitialBudget(final long initialBudget) {
        this.initialBudget = initialBudget;
    }

    public final long getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public final void setInitialInfrastructureCost(final long initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
    }

    public final long getInitialProductionCost() {
        return initialProductionCost;
    }

    public final void setInitialProductionCost(final long initialProductionCost) {
        this.initialProductionCost = initialProductionCost;
    }

    public final long getPreviousNumberOfConsumers() {
        return previousNumberOfConsumers;
    }

    public final void setPreviousNumberOfConsumers(final long previousNumberOfConsumers) {
        this.previousNumberOfConsumers = previousNumberOfConsumers;
    }

    public final ArrayList<Consumer> getConsumers() {
        return consumers;
    }

    public final void setConsumers(final ArrayList<Consumer> consumers) {
        this.consumers = consumers;
    }

    public final ArrayList<Consumer> getPastDueConsumers() {
        return pastDueConsumers;
    }

    public final boolean isBankrupt() {
        return isBankrupt;
    }

    public final void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public long getEnergyNeededKW() {
        return energyNeededKW;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public long getContractCost() {
        return contractCost;
    }

    public void setContractCost(long contractCost) {
        this.contractCost = contractCost;
    }

    public ArrayList<Producer> getProducers() {
        return producers;
    }

    /**
     * Distributor method that removes costumer from database
     */
    public final void removeConsumer(final Consumer consumer) {
        if (this.getConsumers() != null) {
            this.getConsumers().remove(consumer);
        }
    }

    /**
     * Distributor method that changes the distributor's contract parameters based on this turn's
     * given updates
     */
    public final void changeCost(final ArrayList<CostsChanges> changes) {
        for (CostsChanges change : changes) {
            if (change.getId() == this.getId()) {
                setInitialInfrastructureCost(change.getInfrastructureCost());
            }
        }
    }

    /**
     * Distributor method that adds to the distributor's budget this turn's income from consumers
     */
    public final void addIncomeToBudget() {
        for (int i = 0; i < this.getConsumers().size(); i++) {
            if (!this.getPastDueConsumers().contains(this.getConsumers().get(i))) {
                setInitialBudget(getInitialBudget()
                        + this.getConsumers().get(i).getContractPrice());
            }
        }
    }

    /**
     * Distributor method that substracts from distributor's budget this turn's spendings
     */
    public final void payBills() {
        if (this.isBankrupt) {
            return;
        }
        Formulas formulas = new Formulas();
        if ((getInitialBudget() - formulas.monthlySpendings(this)) < 0) {
            setBankrupt(true);
        }
        setInitialBudget(getInitialBudget() - formulas.monthlySpendings(this));
    }

    /**
     * Distributor method that adds current distributor to a producer's list of active distribuitor
     * clients
     */
    public final void addDistributorToProducer(Producer producer) {
        producer.getCurrentDistributors().add(this);
    }

    /**
     * Distributor method that searches for a producer/producers
     */
    public final void searchProducer(Input input) {
        ProducerSorts producerSorts = new ProducerSorts();
        for (int i = 0; i < input.getProducersData().size(); i++) {
            if (this.energyNeededCurrently == 0) {
                return;
            }
            if (input.getProducersData().get(i).getMaxDistributor()
                    == input.getProducersData().get(i).getDistributors().size()) {
                i++;
            }
            producerSorts.sortProducerGreen(input);
            if (input.getProducersData().get(i).getEnergyType().isRenewable()
                    && getProducerStrategy().toString().equals("GREEN")) {
                this.getProducers().add(input.getProducersData().get(i));
                if (this.energyNeededCurrently < input.getProducersData().get(i).
                        getEnergyPerDistributor()) {
                    this.energyNeededCurrently = 0;
                } else {
                    this.energyNeededCurrently -= input.getProducersData().get(i).
                            getEnergyPerDistributor();
                }
                addDistributorToProducer(input.getProducersData().get(i));
            } else if (getProducerStrategy().toString().equals("PRICE")) {
                producerSorts.sortProducerPrice(input);
                this.getProducers().add(input.getProducersData().get(i));
                if (this.energyNeededCurrently < input.getProducersData().get(i).
                        getEnergyPerDistributor()) {
                    this.energyNeededCurrently = 0;
                } else {
                    this.energyNeededCurrently -= input.getProducersData().get(i).
                            getEnergyPerDistributor();
                }
                addDistributorToProducer(input.getProducersData().get(i));
            } else if (getProducerStrategy().toString().equals("QUANTITY")) {
                producerSorts.sortProducerQuantity(input);
                this.getProducers().add(input.getProducersData().get(i));
                if (this.energyNeededCurrently < input.getProducersData().get(i).
                        getEnergyPerDistributor()) {
                    this.energyNeededCurrently = 0;
                } else {
                    this.energyNeededCurrently -= input.getProducersData().get(i).
                            getEnergyPerDistributor();
                }
                addDistributorToProducer(input.getProducersData().get(i));
            } else {
                this.getProducers().add(input.getProducersData().get(i));
                addDistributorToProducer(input.getProducersData().get(i));
                if (this.energyNeededCurrently < input.getProducersData().get(i).
                        getEnergyPerDistributor()) {
                    this.energyNeededCurrently = 0;
                } else {
                    this.energyNeededCurrently -= input.getProducersData().get(i).
                            getEnergyPerDistributor();
                }
            }
        }
    }
}
