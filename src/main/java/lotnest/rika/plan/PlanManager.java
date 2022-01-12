package lotnest.rika.plan;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlanManager {

    public static final String DEFAULT_PATH = "src/main/resources/plans/";
    public static final ExecutorService DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    @NotNull
    private final List<Plan> plans;

    public PlanManager() {
        this(new LinkedList<>());
    }

    public PlanManager(@NotNull List<Plan> plans) {
        this.plans = plans;
    }

    public List<Plan> getPlans() {
        return new LinkedList<>(plans);
    }

    public boolean addPlan(@NotNull Plan plan) {
        return plans.add(plan);
    }

    public boolean removePlan(@NotNull Plan plan) {
        return plans.remove(plan);
    }

    public Plan get(int index) {
        return plans.get(index);
    }
}
