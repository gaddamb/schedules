package com.gs.schedules.config;

import java.util.Arrays;

public enum WeekDay {
    MONDAY(true),
    TUESDAY(true),
    WEDNESDAY(true),
    THURSDAY(true),
    FRIDAY(true),
    SATURDAY(false),
    SUNDAY(false);

    private final boolean isWorkingDay;

    WeekDay(boolean isWorkingDay) {
        this.isWorkingDay = isWorkingDay;
    }

    public static WeekDay[] getDays(boolean isWorkingDay) {
        return Arrays.stream(values()).filter(i -> i.isWorkingDay == isWorkingDay).toArray(WeekDay[]::new);
    }
}
