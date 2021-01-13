package updates;

public class CostsChanges {
    private final long id;
    private final long infrastructureCost;

    public CostsChanges(final long id, final long infrastructureCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
    }

    public final long getId() {
        return id;
    }

    public final long getInfrastructureCost() {
        return infrastructureCost;
    }

}
