package com.gs.schedules.utils;

import com.gs.schedules.config.WeekDay;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;

public class GSUtils {

    public static DayOfWeek getDayOfWeek(LocalDateTime date) {
        return date.getDayOfWeek();
    }


    public static LocalDateTime formatDateInUTC(String input) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "uuuuMMddHHmmss" ) ;
        LocalDateTime ldt = null;
        try {
            ldt = LocalDateTime.parse( input , f );
        } catch ( DateTimeParseException e ) {
            System.out.println( "ERROR - invalid input for date-time. input: " + input ) ;
            e.printStackTrace();  // Handle invalid input.
            return null;
        }
        return ldt.atOffset(ZoneOffset.UTC).toLocalDateTime();
    }

    public static LocalDateTime formatDateToUTC(String date, String time){
        if(date == null) {
            return null;
        }
        if(time == null){
            time = "00:00";
        }
        LocalDate datePart = LocalDate.parse(date);
        LocalTime timePart = LocalTime.parse(time);
        return LocalDateTime.of(datePart, timePart).atOffset(ZoneOffset.UTC).toLocalDateTime();
    }

    public static String formatDateToString(LocalDateTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX");
        return dateTime.atOffset(ZoneOffset.UTC).format(dtf);
    }

    public static Boolean isWithinDateRange(LocalDateTime givenInputDate, String schedule) {
        // assuming the message : "from 2021-02-02 to 2022-08-23"
        String[] scheduleSplitString = schedule.split(" ");
        LocalDateTime fromDate = formatDateToUTC(scheduleSplitString[1], "00:00");
        LocalDateTime toDate = formatDateToUTC(scheduleSplitString[3], "00:00");

        return givenInputDate.isAfter(fromDate) && givenInputDate.isBefore(toDate);

    }

    public static Boolean isOnTheDay(LocalDateTime givenInputDate, String schedule){
        // assuming the message : "from 10:00 to 11:00 each Monday"
        String[] scheduleSplitString = schedule.split(" ");
        String scheduledDay = scheduleSplitString[5];
        LocalTime fromTime = LocalTime.parse(scheduleSplitString[1]);
        LocalTime toTime = LocalTime.parse(scheduleSplitString[3]);

        return (givenInputDate.getDayOfWeek().name().equalsIgnoreCase(scheduledDay) &&
                givenInputDate.toLocalTime().isAfter(fromTime) &&
                givenInputDate.toLocalTime().isBefore(toTime)) ;
    }

    public static boolean isOnWeekDay(LocalDateTime inputDate, String schedule) {
        // assuming the message :  "from 13:00 to 13:30 each weekday"
        WeekDay[] weekdays = WeekDay.getDays(Boolean.TRUE);
        String[] scheduleSplitString = schedule.split(" ");
        LocalTime fromTime = LocalTime.parse(scheduleSplitString[1]);
        LocalTime toTime = LocalTime.parse(scheduleSplitString[3]);

       long count = Arrays.stream(weekdays)
               .filter(weekday -> inputDate.getDayOfWeek().name().equalsIgnoreCase(weekday.name()))
               .count();

       return (count == 1 &&
               inputDate.toLocalTime().isAfter(fromTime) &&
               inputDate.toLocalTime().isBefore(toTime));
    }

    public static boolean isOnWeekends(LocalDateTime inputDate, String schedule) {
        // assuming the message :  "from 17:00 to 17:15 only weekends"
        WeekDay[] weekends = WeekDay.getDays(Boolean.FALSE);
        String[] scheduleSplitString = schedule.split(" ");
        LocalTime fromTime = LocalTime.parse(scheduleSplitString[1]);
        LocalTime toTime = LocalTime.parse(scheduleSplitString[3]);

        long count = Arrays.stream(weekends)
                .filter(weekday -> inputDate.getDayOfWeek().name().equalsIgnoreCase(weekday.name()))
                .count();

        return (count == 1 &&
                inputDate.toLocalTime().isAfter(fromTime) &&
                inputDate.toLocalTime().isBefore(toTime));
    }

    public static boolean isOnEveryDay(LocalDateTime inputDate, String schedule) {
        return (isOnWeekDay(inputDate, schedule) || isOnWeekends(inputDate, schedule));
    }

    public static boolean isOnDayWithInterval(LocalDateTime inputDate, String schedule, Date scheduelCreatedDate) {

        // assuming the message :  "from 20:00 to 21:00, only Wednesday, each 2 weeks"
        String[] scheduleSplitString = schedule.split(" ");
        Boolean isOnDay = Boolean.FALSE;
        LocalTime fromTime = LocalTime.parse(scheduleSplitString[1]);
        LocalTime toTime = LocalTime.parse(scheduleSplitString[3].split(",")[0]);
        String scheduledDay = scheduleSplitString[5].split(",")[0];
        int interval = Integer.parseInt(scheduleSplitString[7]);

        if(inputDate.getDayOfWeek().name().equalsIgnoreCase(scheduledDay)) {
            //TBD
            // get the startDate from schedule created date lying ont the scheduleDay.
            // from that date onwards do{ nextDateOfInterval = next date of interval from startDate}while(inputDate > nextDateOfInterval)
            isOnDay = Boolean.FALSE;

        }
        return isOnDay;
    }
}
