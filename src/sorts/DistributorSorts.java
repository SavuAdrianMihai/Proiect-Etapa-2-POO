package sorts;

import filesinteractions.Input;
import formulas.Formulas;

import java.util.Collections;

public class DistributorSorts {

    /**
     * Sorting method intended for the Distributor class sorts given @param input in asc order,
     * based on the contract prices
     */
    public final void sortDistribuitorsByContractPrice(final Input input) {
        Formulas formulas = new Formulas();
        for (int i = 0; i < input.getDistributorsData().size() - 1; i++) {
            for (int j = 0; j < input.getDistributorsData().size() - i - 1; j++) {
                if (formulas.finalContractPrice(input.getDistributorsData().get(j))
                        > formulas.finalContractPrice(input.getDistributorsData().get(j + 1))) {
                    Collections.swap(input.getDistributorsData(), j, j + 1);
                }
            }
        }
    }

    /**
     * Sorting method intended for the Distributor class sorts given @param input in asc order,
     * based on id. Method created for printing purposes
     */
    public final void sortDistributorsById(final Input input) {
        for (int i = 0; i < input.getDistributorsData().size() - 1; i++) {
            for (int j = 0; j < input.getDistributorsData().size() - i - 1; j++) {
                if (input.getDistributorsData().get(j).getId()
                        > input.getDistributorsData().get(j + 1).getId()) {
                    Collections.swap(input.getDistributorsData(), j, j + 1);
                }
            }
        }
    }
}
