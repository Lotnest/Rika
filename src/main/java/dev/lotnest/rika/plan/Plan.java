package dev.lotnest.rika.plan;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import dev.lotnest.rika.plan.lesson.AbstractLesson;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Getter
@Setter
public class Plan {

    public static final String FILE_FORMAT = "PlanSem\\d\\.json";
    private static final String FILE_DOES_NOT_MATCH_THE_FORMAT = String.format("File name does not match the format: %s", FILE_FORMAT);

    @NotNull
    private final String planFileName;
    private final int semester;
    @NotNull
    private List<AbstractLesson> lessons;

    public Plan(@NotNull SemesterEnum semesterEnum) {
        this.semester = semesterEnum.getNumber();
        this.planFileName = PlanManager.DEFAULT_PATH + "PlanSem{0}.json".replace("{0}", String.valueOf(semester));

        lessons = new LinkedList<>();
    }

    private boolean isStartTimeAfterOrEqual(@NotNull LocalDateTime startTime1, @NotNull LocalDateTime startTime2) {
        return startTime1.isAfter(startTime2) || startTime1.isEqual(startTime2);
    }

    private boolean isEndTimeBeforeOrEqual(@NotNull LocalDateTime endTime1, @NotNull LocalDateTime endTime2) {
        return endTime1.isBefore(endTime2) || endTime1.isEqual(endTime2);
    }

    @NotNull
    public CompletableFuture<List<AbstractLesson>> getLessonsDuringPeriod(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime) {
        return getLessonsDuringPeriod(startTime, endTime, Long.MAX_VALUE);
    }

    @NotNull
    public CompletableFuture<List<AbstractLesson>> getLessonsDuringPeriod(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, long limit) {
        return CompletableFuture.supplyAsync(() -> lessons.stream()
                .filter(lesson -> isStartTimeAfterOrEqual(lesson.getStartTime(), startTime) &&
                        isEndTimeBeforeOrEqual(lesson.getEndTime(), endTime))
                .limit(limit)
                .collect(Collectors.toList()), PlanManager.DEFAULT_EXECUTOR_SERVICE);
    }

    @SneakyThrows
    @NotNull
    public AbstractLesson getNextLesson() {
        LocalDateTime now = LocalDateTime.now();
        return getLessonsDuringPeriod(now, now.plusMonths(1), 1)
                .get(10L, TimeUnit.SECONDS)
                .get(0);
    }
}
