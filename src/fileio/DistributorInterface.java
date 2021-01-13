package fileio;

import updates.CostsChanges;

import java.util.ArrayList;

public interface DistributorInterface {
    /**
     * Distributor method that removes costumer from database
     */
    void removeConsumer(Consumer consumer);
    /**
     * Distributor method that changes the distributor's contract parameters based on this turn's
     * given updates
     */
    void changeCost(ArrayList<CostsChanges> changes);
    /**
     * Distributor method that adds to the distributor's budget this turn's income from consumers
     */
    void addIncomeToBudget();
    /**
     * Distributor method that substracts from distributor's budget this turn's spendings
     */
    void payBills();
}
