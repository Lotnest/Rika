package lotnest.rika.plan.lesson;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public abstract class AbstractLesson {

    @NotNull LocalDateTime startTime;
    @NotNull LocalDateTime endTime;
    @NotNull String code;
    @NotNull LessonType type;
    @NotNull String room;
}
