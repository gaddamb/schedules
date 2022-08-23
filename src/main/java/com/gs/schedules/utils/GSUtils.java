package com.gs.schedules.utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class GSUtils {

    public static DayOfWeek getDayOfWeek(LocalDateTime date) {
        return date.getDayOfWeek();
    }

    public static String formatDate(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX");
        return date.atOffset(ZoneOffset.UTC).format(dtf);
    }

}
