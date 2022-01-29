package dev.lotnest.rika.plan.lesson;

import dev.lotnest.rika.Rika;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import dev.lotnest.rika.plan.lesson.impl.ExerciseLesson;
import dev.lotnest.rika.plan.lesson.impl.LanguageLesson;
import dev.lotnest.rika.plan.lesson.impl.LectureLesson;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public abstract class AbstractLesson {

    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    @NotNull
    private String code;
    @NotNull
    private LessonType type;
    @NotNull
    private String room;

    public static AbstractLesson getLessonFromSummary(@NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull String code, @NotNull LessonType type, @NotNull String room) {
        switch (type) {
            case LECTURE:
                return new LectureLesson(startTime, endTime, code, room);
            case EXERCISE:
                return new ExerciseLesson(startTime, endTime, code, room);
            case LANGUAGE:
                return new LanguageLesson(startTime, endTime, code, room);
            default:
                return new AbstractLesson(startTime, endTime, code, type, room) {
                    @Override
                    public @NotNull LocalDateTime getStartTime() {
                        return startTime;
                    }

                    @Override
                    public @NotNull LocalDateTime getEndTime() {
                        return endTime;
                    }

                    @Override
                    public @NotNull String getCode() {
                        return code;
                    }

                    @Override
                    public @NotNull LessonType getType() {
                        return type;
                    }

                    @Override
                    public @NotNull String getRoom() {
                        return room;
                    }
                };
        }
    }

    @Override
    public String toString() {
        return "**Przedmiot:** " + code
                + "\n**Typ zajęć:** " + type
                + "\n**Czas rozpoczęcia:** " + startTime.format(Rika.DEFAULT_DATE_TIME_FORMATTER)
                + "\n**Czas zakończenia:** " + endTime.format(Rika.DEFAULT_DATE_TIME_FORMATTER)
                + "\n**Sala:** " + room;
    }
}