package dev.lotnest.rika.plan.lesson.impl;

import dev.lotnest.rika.plan.lesson.AbstractLesson;
import dev.lotnest.rika.plan.lesson.LessonType;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class LanguageLesson extends AbstractLesson {

    public LanguageLesson(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull String code, @NotNull String room) {
        super(startTime, endTime, code, LessonType.LANGUAGE, room);
    }
}