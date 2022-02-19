package dev.lotnest.rika.plan;

import dev.lotnest.rika.plan.lesson.AbstractLesson;
import dev.lotnest.rika.plan.lesson.LessonType;
import dev.lotnest.rika.utils.TimeUtils;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PlanMapper {

    @SneakyThrows
    public Plan mapFromSemester(SemesterEnum semesterEnum) {
        Plan result = new Plan(semesterEnum);
        File planFile = new File(result.getPlanFileName());

        if (!planFile.exists()) {
            throw new IOException(String.format("File does not exist: '%s'", result.getPlanFileName()));
        }

        JSONTokener tokener = new JSONTokener(new InputStreamReader(new FileInputStream(planFile)));
        JSONObject root = new JSONObject(tokener);

        JSONArray vCalendar = root.getJSONArray("VCALENDAR");
        JSONObject vCalendarObject = vCalendar.getJSONObject(0);
        JSONArray vEvents = vCalendarObject.getJSONArray("VEVENT");
        List<AbstractLesson> lessons = new LinkedList<>();

        for (int i = 0; i < vEvents.length(); i++) {
            JSONObject lessonJson = vEvents.getJSONObject(i);

            LocalDateTime dtStart = LocalDateTime.parse(lessonJson.getString("DTSTART"), TimeUtils.PLAN_DATE_TIME_FORMATTER).plusHours(1);
            LocalDateTime dtEnd = LocalDateTime.parse(lessonJson.getString("DTEND"), TimeUtils.PLAN_DATE_TIME_FORMATTER).plusHours(1);

            String[] summary = lessonJson.getString("SUMMARY").split(" ");
            String lessonCode = summary[0];
            LessonType lessonType = LessonType.parseFromText(summary[1]).orElse(findExamKeyword(summary));

            if (lessonType.equals(LessonType.EXERCISE)) {
                lessonType = LessonType.parseFromText(summary[0]).orElse(LessonType.EXERCISE);
            }

            String lessonRoom = getLessonRoomFromSummary(summary);

            AbstractLesson lesson = AbstractLesson.getLessonFromSummary(dtStart, dtEnd, lessonCode, lessonType, lessonRoom);
            lessons.add(lesson);
        }

        result.setLessons(lessons);

        return result;
    }

    private LessonType findExamKeyword(@NotNull String[] summary) {
        int examKeywordIndex = -1;
        for (int i = 0; i < summary.length; i++) {
            if (summary[i].equals(LessonType.EXAM.getIdentifyingKeyword())) {
                examKeywordIndex = i;
            }
        }

        if (examKeywordIndex == -1) {
            return LessonType.UNKNOWN;
        }

        return LessonType.EXAM;
    }

    @NotNull
    private String getLessonRoomFromSummary(@NotNull String[] summary) {
        if (summary.length == 4) {
            return summary[3];
        } else {
            StringBuilder lessonRoomBuilder = new StringBuilder();
            for (int i = 3; i < summary.length; i++) {
                if (!Objects.equals(summary[i], LessonType.LANGUAGE.getIdentifyingKeyword())
                        && !Objects.equals(summary[i], LessonType.EXAM.getIdentifyingKeyword())
                        && !summary[i].equals("s.")) {
                    lessonRoomBuilder.append(summary[i])
                            .append(" ");
                }
            }

            return lessonRoomBuilder.toString();
        }
    }
}
