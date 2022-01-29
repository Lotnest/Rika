package dev.lotnest.rika.plan.lesson;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum LessonType {

    LECTURE("wykład"),
    EXERCISE("ćwiczenia"),
    LANGUAGE("lek"),
    EXAM("egzamin"),
    UNKNOWN("nieznany");

    @NotNull
    private final String identifyingKeyword;

    @NotNull
    public static Optional<LessonType> parseFromText(@NotNull String text) {
        text = text.toLowerCase(Locale.ROOT);
        for (LessonType lessonType : LessonType.values()) {
            if (text.contains(lessonType.identifyingKeyword)) {
                return Optional.of(lessonType);
            }
        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        if (this.equals(LessonType.LANGUAGE)) {
            return "lektorat";
        }
        return identifyingKeyword;
    }
}
