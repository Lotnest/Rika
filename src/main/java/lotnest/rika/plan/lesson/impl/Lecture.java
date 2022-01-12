package lotnest.rika.plan.lesson.impl;

import lotnest.rika.plan.lesson.AbstractLesson;
import lotnest.rika.plan.lesson.LessonType;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class Lecture extends AbstractLesson {

    public Lecture(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull String code, @NotNull String room) {
        super(startTime, endTime, code, LessonType.LECTURE, room);
    }
}
