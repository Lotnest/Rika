package lotnest.rika.plan;

import lombok.Getter;
import lombok.Setter;
import lotnest.rika.plan.lesson.AbstractLesson;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Getter
@Setter
public class Plan {

    @NotNull
    private final String planFileName;
    private final int semester;

    @NotNull
    private List<AbstractLesson> lessons;

    public Plan(@NotNull String planFileName, int semester) {
        this.planFileName = PlanManager.DEFAULT_PATH + planFileName;
        this.semester = semester;

        if (!planFileName.endsWith(".json")) {
            throw new IllegalArgumentException("File extension must be '.json'");
        }

        lessons = new LinkedList<>();
    }

    private boolean isStartTimeAfterOrEqual(@NotNull LocalDateTime startTime1, @NotNull LocalDateTime startTime2) {
        return startTime1.isAfter(startTime2) || startTime1.isEqual(startTime2);
    }

    private boolean isEndTimeBeforeOrEqual(@NotNull LocalDateTime endTime1, @NotNull LocalDateTime endTime2) {
        return endTime1.isBefore(endTime2) || endTime1.isEqual(endTime2);
    }

    public CompletableFuture<List<AbstractLesson>> getLessonsDuringPeriod(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) {
        return CompletableFuture.supplyAsync(() -> lessons.stream()
                .filter(lesson -> isStartTimeAfterOrEqual(lesson.getStartTime(), startTime) &&
                        isEndTimeBeforeOrEqual(lesson.getEndTime(), endTime))
                .collect(Collectors.toList()), PlanManager.DEFAULT_EXECUTOR_SERVICE);
    }
}
