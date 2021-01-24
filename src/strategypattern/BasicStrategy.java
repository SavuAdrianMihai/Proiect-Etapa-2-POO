package strategypattern;

import fileio.Distributor;
import filesinteractions.Input;
import sorts.ProducerSorts;

public class BasicStrategy implements ChoosingProducerStrategy {
    @Override
    public final void searchProducer(Input input, Distributor distributor) {
        for (int i = 0; i < input.getProducersData().size(); i++) {
            if (distributor.getEnergyNeededCurrently() == 0) {
                return;
            }
            if (input.getProducersData().get(i).getMaxDistributor()
                    == input.getProducersData().get(i).getDistributors().size()) {
                i++;
            }
            ProducerSorts producerSorts = new ProducerSorts();
            producerSorts.sortProducersById(input);
            distributor.getProducers().add(input.getProducersData().get(i));
            distributor.addDistributorToProducer(input.getProducersData().get(i));
            if (distributor.getEnergyNeededCurrently() < input.getProducersData().get(i).
                    getEnergyPerDistributor()) {
                distributor.setEnergyNeededCurrently(0);
            } else {
                distributor.setEnergyNeededCurrently(distributor.getEnergyNeededCurrently()
                        - input.getProducersData().get(i).getEnergyPerDistributor());
            }
        }
    }
}
