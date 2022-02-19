package dev.lotnest.rika.utils;

import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static final DateTimeFormatter PLAN_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");

    private TimeUtils() {
    }
}
