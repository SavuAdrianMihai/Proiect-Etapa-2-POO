package fileio;

import filesinteractions.Input;
import formulas.Formulas;
import sorts.DistributorSorts;

public class Consumer implements ConsumerInterface {
    private final long id;
    private long initialBudget;
    private long monthlyIncome;
    private Distributor currentDistributor;
    private Distributor distributorToBePayed;
    private long contractPrice;
    private long contractLength;
    private long pastDuePayment;
    private boolean isBankrupt;

    public Consumer(final long id, long initialBudget, long monthlyIncome) {
        this.id = id;
        this.initialBudget = initialBudget;
        this.monthlyIncome = monthlyIncome;
        // current distributor
        this.currentDistributor = new Distributor();
        // distributor that has to be payed if the consumer has a past due payment
        // and has no longer contract with him
        this.distributorToBePayed = new Distributor();
        this.contractPrice = 0;
        this.contractLength = 0;
        this.pastDuePayment = 0;
        this.isBankrupt = false;
    }

    public final long getId() {
        return id;
    }

    public final long getInitialBudget() {
        return initialBudget;
    }

    public final void setInitialBudget(final long initialBudget) {
        this.initialBudget = initialBudget;
    }

    public final long getMonthlyIncome() {
        return monthlyIncome;
    }

    public final void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public final Distributor getCurrentDistributor() {
        return currentDistributor;
    }

    public final void setCurrentDistributor(Distributor currentDistributor) {
        this.currentDistributor = currentDistributor;
    }

    public final Distributor getDistributorToBePayed() {
        return distributorToBePayed;
    }

    public final void setDistributorToBePayed(Distributor distributorToBePayed) {
        this.distributorToBePayed = distributorToBePayed;
    }

    public final long getContractPrice() {
        return contractPrice;
    }

    public final void setContractPrice(final long contractPrice) {
        this.contractPrice = contractPrice;
    }

    public final long getContractLength() {
        return contractLength;
    }

    public final void setContractLength(final long contractLength) {
        this.contractLength = contractLength;
    }

    public final long getPastDuePayment() {
        return pastDuePayment;
    }

    public final void setPastDuePayment(final long pastDuePayment) {
        this.pastDuePayment = pastDuePayment;
    }

    public final boolean isBankrupt() {
        return isBankrupt;
    }

    public final void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    /**
     * Consumer method that updates the contract and all the associated parameters
     */
    public final void changeContract(Distributor distributor) {
        Formulas formulas = new Formulas();
        setContractPrice(formulas.finalContractPrice(distributor));
        setContractLength(distributor.getContractLength());
        if (getCurrentDistributor().getConsumers() != null) {
            getCurrentDistributor().removeConsumer(this);
        }
        setCurrentDistributor(distributor);
        distributor.getConsumers().add(this);
    }

    /**
     * Consumer method that searches contract in Distributor database
     */
    public final void searchContract(final Input input) {
        if (contractLength == 0) {
            DistributorSorts distribuitorSorts = new DistributorSorts();
            distribuitorSorts.sortDistribuitorsByContractPrice(input);
            for (int i = 0; i < input.getDistributorsData().size(); i++) {
                if (!input.getDistributorsData().get(i).isBankrupt()) {
                    changeContract(input.getDistributorsData().get(i));
                    return;
                }
            }
        }
    }

    /**
     * Consumer method that adds the monthly income to the budget
     */
    public final void addBudget() {
        setInitialBudget(getInitialBudget() + getMonthlyIncome());
    }

    /**
     * Consumer method that subtracts this turn's payments from consumer's budget
     */
    public final void payBills() {
        if (this.isBankrupt) {
            return;
        }
        Formulas formulas = new Formulas();
        if (this.getPastDuePayment() > 0) {
            if (getInitialBudget() - formulas.penaltyPayment(this) >= 0) {
                setInitialBudget(getInitialBudget() - formulas.penaltyPayment(this));
            } else {
                if (!distributorToBePayed.equals(new Distributor())) {
                    // costs for the following month
                    distributorToBePayed.setInitialBudget(distributorToBePayed.getInitialBudget()
                            - distributorToBePayed.getInitialProductionCost());
                    setBankrupt(true);
                    getCurrentDistributor().removeConsumer(this);
                    if (getCurrentDistributor() != getDistributorToBePayed()) {
                        getDistributorToBePayed().getPastDueConsumers().remove(this);
                    }
                    return;
                }
            }
        }
        if ((getInitialBudget() - getContractPrice()) >= 0) {
            setInitialBudget(getInitialBudget() - getContractPrice());
        } else {
            setPastDuePayment(getContractPrice());
            setDistributorToBePayed(getCurrentDistributor());
            getDistributorToBePayed().getPastDueConsumers().add(this);
        }
        setContractLength(getContractLength() - 1);
    }
}
