import fileio.Consumer;
import fileio.Distributor;
import fileio.Producer;
import filesinteractions.Input;
import filesinteractions.InputLoader;
import filesinteractions.Writer;
import formulas.Formulas;
import sorts.DistributorSorts;
import sorts.ProducerSorts;
import updates.MonthlyUpdates;

/**
 * Entry point to the simulation
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     */
    public static void main(final String[] args) {
        String inputFilePath = args[0];
        InputLoader inputLoader = new InputLoader(inputFilePath);
        Input input = inputLoader.readData();

        // Initial round

        // Distributor's action in the initial round
        for (Distributor distributor : input.getDistributorsData()) {
            distributor.searchProducer(input);
            Formulas formulas = new Formulas();
            distributor.setInitialProductionCost(formulas.productionCost(distributor));
            distributor.setContractCost(formulas.finalContractPrice(distributor));
            distributor.setPreviousNumberOfConsumers(distributor.getConsumers().size());
        }

        // Consumer's action in the initial round
        for (Consumer consumer : input.getConsumersData()) {
            consumer.searchContract(input);
            consumer.addBudget();
            consumer.payBills();
        }

        for (Distributor distributor : input.getDistributorsData()) {
            distributor.addIncomeToBudget();
            distributor.payBills();
        }

        for (Producer producer : input.getProducersData()) {
            producer.addCurrentDistributorsToArchive(1);
        }

        // iterate through rounds
        for (int i = 0; i < input.getNumberOfTurns(); i++) {
            // read the updates
            MonthlyUpdates update = input.getMonthlyUpdates().get(i);

            // change the costs if there are any changes to be made
            for (Distributor distributor : input.getDistributorsData()) {
                assert update.getCostsChanges() != null;
                distributor.changeCost(update.getCostsChanges());
            }

            // adding new consumers to the simulation
            if (update.getNewConsumers() != null) {
                for (Consumer newConsumer : update.getNewConsumers()) {
                    input.getConsumersData().add(newConsumer);
                }
            }

            // Consumer's actions this round
            for (Consumer consumer : input.getConsumersData()) {
                if (!consumer.isBankrupt()) {
                    consumer.searchContract(input);
                    consumer.addBudget();
                    consumer.payBills();
                }
            }

            // Distributor's actions this round
            for (Distributor distributor : input.getDistributorsData()) {
                distributor.setPreviousNumberOfConsumers(distributor.getConsumers().size());
                distributor.addIncomeToBudget();
                distributor.payBills();
            }

            for (Producer producer : input.getProducersData()) {
                producer.addCurrentDistributorsToArchive(i + 2);
            }
        }

        // sort distributors & producers by their id
        DistributorSorts distribuitorSorts = new DistributorSorts();
        ProducerSorts producerSorts = new ProducerSorts();
        distribuitorSorts.sortDistributorsById(input);
        producerSorts.sortProducersById(input);

        // write in output file
        String outputFilePath = args[1];
        Writer writer = new Writer();
        writer.writeFile(outputFilePath, input);
    }
}

