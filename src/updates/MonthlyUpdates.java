package updates;

import fileio.Consumer;

import java.util.ArrayList;

public class MonthlyUpdates {

    private final ArrayList<Consumer> newConsumers;
    private final ArrayList<CostsChanges> costsChanges;

    public MonthlyUpdates() {
        newConsumers = new ArrayList<>();
        costsChanges = new ArrayList<>();
    }

    public final ArrayList<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public final ArrayList<CostsChanges> getCostsChanges() {
        return costsChanges;
    }
}
