package strategypattern;

import fileio.Distributor;
import filesinteractions.Input;

public interface ChoosingProducerStrategy {
    /**
     * Strategy method that searches for producer, designed for distributor class
     */
    void searchProducer(Input input, Distributor distributor);
}
