package com.filreas.gosthlm;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartureListItemByDepartureTimeComparator implements Comparator<DepartureListItem> {

    private final LocalDateTime now;
    private static Pattern leavingNowPattern = Pattern.compile("Nu");
    private static Pattern leavingSoonPattern = Pattern.compile("(\\d+)\\smin");
    private static Pattern leavingLaterPattern = Pattern.compile("\\d{0,2}:\\d{0,2}");

    public DepartureListItemByDepartureTimeComparator(LocalDateTime now){
        this.now = now;
    }

    @Override
    public int compare(DepartureListItem lhs, DepartureListItem rhs) {
        LocalDateTime lhsTime = parseTime(lhs.getDepartureTimeText());
        LocalDateTime rhsTime = parseTime(rhs.getDepartureTimeText());

        if(lhsTime.isBefore(now)){
            return 1;
        }

        return lhsTime.compareTo(rhsTime);
    }

    private LocalDateTime parseTime(String text) {
        Matcher leavingNow = leavingNowPattern.matcher(text);
        if(leavingNow.matches()){
            return now;
        }

        Matcher leavingSoon = leavingSoonPattern.matcher(text);
        if (leavingSoon.matches()){
            String minutesAsString = leavingSoon.group(1);
            return now.plusMinutes(Integer.parseInt(minutesAsString));
        }

        Matcher leavingLater = leavingLaterPattern.matcher(text);
        if (leavingLater.matches()){
            LocalTime departureTime = new LocalTime(text);
            if(now.toLocalTime().isAfter(departureTime)){
                return now.plusDays(1).withHourOfDay(departureTime.getHourOfDay()).withMinuteOfHour(departureTime.getMinuteOfHour());
            }

            return now.withHourOfDay(departureTime.getHourOfDay()).withMinuteOfHour(departureTime.getMinuteOfHour());
        }

        return now; // fallback
    }
}
