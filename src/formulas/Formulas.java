package formulas;

import fileio.Consumer;
import fileio.Distributor;

import static common.Constants.PENALTY_COEF;
import static common.Constants.PROFIT_COEF;

@SuppressWarnings("ALL")
public class Formulas {

    /**
     * Method that calculates the eventual financial penalties of a @param consumer
     */
    public long penaltyPayment(final Consumer consumer) {
        return Math.round(Math.floor(PENALTY_COEF * consumer.getPastDuePayment()))
                + consumer.getContractPrice();
    }

    /**
     * Method that calculates the contract price offered by a @param distributor
     */
    public long finalContractPrice(final Distributor distributor) {
        if (distributor.getPreviousNumberOfConsumers() != 0) {
            return Math.round(Math.floor(distributor.getInitialInfrastructureCost()
                    / (distributor.getPreviousNumberOfConsumers())
                    + distributor.getInitialProductionCost() + profit(distributor)));
        }
        return Math.round(Math.floor(distributor.getInitialInfrastructureCost()
                + distributor.getInitialProductionCost() + profit(distributor)));
    }

    /**
     * Method that calculates the profit parameter of a @param distributor that will
     * be used in other formulas
     */
    public long profit(final Distributor distributor) {
        return Math.round(Math.floor(PROFIT_COEF * distributor.getInitialProductionCost()));
    }

    /**
     * Method that calculates the monthly spendings of a @param distributor
     */
    public long monthlySpendings(final Distributor distributor) {
        return distributor.getInitialInfrastructureCost() + distributor.getInitialProductionCost()
                * distributor.getConsumers().size();
    }

    /**
     * Method that calculates the cost of a @param distributor based on producer's costs
     */
    public long cost(final Distributor distributor) {
        long sum = 0;
        for (int i = 0; i < distributor.getProducers().size(); i++) {
            sum += distributor.getProducers().get(i).getEnergyPerDistributor()
                    * distributor.getProducers().get(i).getPriceKW();
        }
        return sum;
    }

    /**
     * Method that calculates the production cost of a @param distributor
     */
    public long productionCost(final Distributor distributor) {
        return Math.round(Math.floor(cost(distributor) / 10));
    }
}
