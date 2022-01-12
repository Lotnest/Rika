package lotnest.rika.plan.lesson;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public enum LessonType {

    LECTURE("wykład"),
    EXERCISE("ćwiczenia"),
    LANGUAGE("lek");

    @NotNull
    private final String identifyingKeyword;
}
