package fileio;

public final class ConsumerFactory {

    private static ConsumerFactory instance = null;

    private ConsumerFactory() {
    }

    /**
     * Singleton getInstance Method
     */
    public static ConsumerFactory getInstance() {
        if (instance == null) {
            instance = new ConsumerFactory();
        }
        return instance;
    }

    /**
     * Factory Method used to create a new consumer
     */
    public Consumer createConsumer(final long id, final long initialBudget,
                                   final long monthlyIncome) {
        return new Consumer(id, initialBudget, monthlyIncome);
    }
}
