package fileio;

import filesinteractions.Input;

public interface ConsumerInterface {
    /**
     * Consumer method that updates the contract and all the associated parameters
     */
    void changeContract(Distributor distributor);

    /**
     * Consumer method that searches contract in Distributor database
     */
    void searchContract(Input input);

    /**
     * Consumer method that adds the monthly income to the budget
     */
    void addBudget();

    /**
     * Consumer method that subtracts this turn's payments from consumer's budget
     */
    void payBills();
}

