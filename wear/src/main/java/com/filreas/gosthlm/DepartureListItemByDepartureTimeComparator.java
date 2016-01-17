package com.filreas.gosthlm;

import org.joda.time.LocalTime;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartureListItemByDepartureTimeComparator implements Comparator<DepartureListItem> {

    private final LocalTime now;
    private static Pattern leavingNowPattern = Pattern.compile("Nu");
    private static Pattern leavingSoonPattern = Pattern.compile("(\\d+)\\smin");
    private static Pattern leavingLaterPattern = Pattern.compile("\\d{0,2}:\\d{0,2}");

    public DepartureListItemByDepartureTimeComparator(LocalTime now){
        this.now = now;
    }

    @Override
    public int compare(DepartureListItem lhs, DepartureListItem rhs) {
        LocalTime lhsTime = parseTime(lhs.getDepartureTimeText());
        LocalTime rhsTime = parseTime(rhs.getDepartureTimeText());

        if(lhsTime.isBefore(now)){
            return 1;
        }

        return lhsTime.compareTo(rhsTime);
    }

    private LocalTime parseTime(String text) {
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
            return new LocalTime(text);
        }

        return now; // fallback
    }
}
