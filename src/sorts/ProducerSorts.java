package sorts;

import filesinteractions.Input;

import java.util.Collections;

public class ProducerSorts {
    public final void sortProducerGreen(final Input input) {
        sortProducersById(input);
        sortProducerQuantity(input);
        sortProducerPrice(input);
        for (int i = 0; i < input.getProducersData().size() - 1; i++) {
            for (int j = 0; j < input.getProducersData().size() - i - 1; j++) {
                if ((input.getProducersData().get(j).getEnergyType().isRenewable()) &&
                        !input.getProducersData().get(j).getEnergyType().isRenewable()) {
                    Collections.swap(input.getDistributorsData(), j, j + 1);
                }
            }
        }
    }

    public final void sortProducerPrice(final Input input) {
        sortProducersById(input);
        sortProducerQuantity(input);
        for (int i = 0; i < input.getProducersData().size() - 1; i++) {
            for (int j = 0; j < input.getProducersData().size() - i - 1; j++) {
                if (input.getProducersData().get(j).getPriceKW() > input.getProducersData().get(j).
                        getPriceKW())
                    Collections.swap(input.getProducersData(), j, j + 1);
            }
        }
    }

    public final void sortProducerQuantity(final Input input) {
        sortProducersById(input);
        for (int i = 0; i < input.getProducersData().size() - 1; i++) {
            for (int j = 0; j < input.getProducersData().size() - i - 1; j++) {
                if (input.getProducersData().get(j).getEnergyPerDistributor() <
                        input.getProducersData().get(j + 1).getEnergyPerDistributor())
                    Collections.swap(input.getProducersData(), j, j + 1);
            }
        }
    }

    public final void sortProducersById(final Input input) {
        for (int i = 0; i < input.getDistributorsData().size() - 1; i++) {
            for (int j = 0; j < input.getDistributorsData().size() - i - 1; j++) {
                if (input.getProducersData().get(j).getId()
                        > input.getProducersData().get(j + 1).getId()) {
                    Collections.swap(input.getProducersData(), j, j + 1);
                }
            }
        }
    }
}


