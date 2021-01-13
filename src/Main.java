import fileio.Consumer;
import fileio.Distributor;
import filesinteractions.Input;
import filesinteractions.InputLoader;
import filesinteractions.Writer;
import sorts.DistributorSorts;
import updates.MonthlyUpdates;

/**
 * Entry point to the simulation
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        //String inputFilePath = args[0];

        String inputFilePath = "checker/resources/in/basic_1.json";
        InputLoader inputLoader = new InputLoader(inputFilePath);

        Input input = inputLoader.readData();

        // Initial round

        // Consumer's action in the initial round
        for (Consumer consumer : input.getConsumersData()) {
            consumer.searchContract(input);
            consumer.addBudget();
            consumer.payBills();
        }

        // Distributor's action in the initial round
        for (Distributor distributor : input.getDistributorsData()) {
            distributor.searchProducer(input);
            distributor.setPreviousNumberOfConsumers(distributor.getConsumers().size());
            distributor.addIncomeToBudget();
            distributor.payBills();
        }

        /*
        for (Producer producer : input.getProducersData()) {

        }
         */

        // iterate through rounds
        for (int i = 0; i < input.getNumberOfTurns(); i++) {
            // read the updates
            MonthlyUpdates update = input.getMonthlyUpdates().get(i);

            // change the costs if there are any changes to be made
            for (Distributor distributor : input.getDistributorsData()) {
                assert update.getCostsChanges() != null;
                distributor.changeCost(update.getCostsChanges());
            }

            /*
            for (Producer producer : input.getProducersData()) {

            }
*/
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
        }

        // sort distributors by their id
        DistributorSorts distribuitorSorts = new DistributorSorts();
        distribuitorSorts.sortDistributorsById(input);

        // write in output file
        //String outputFilePath = args[1];
        String outputFilePath = "results.out";
        Writer writer = new Writer();
        writer.writeFile(outputFilePath, input);
    }
}

