package us.redshift.timesheet;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtility {
    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return Instant
                // get the millis value to build the Instant
                .ofEpochMilli(dateToConvert.getTime())
                // convert to JVM default timezone
                .atZone(ZoneId.systemDefault())
                // convert to LocalDate
                .toLocalDate();
    }

    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
