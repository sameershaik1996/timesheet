package us.redshift.timesheet.domain.timesheet;

import java.util.HashMap;
import java.util.Map;

public enum TimeSheetStatus {
    PENDING("PENDING"), SUBMITTED("SUBMITTED"), APPROVED("APPROVED"), REJECTED("REJECTED");

    //Lookup table
    private static final Map<String, TimeSheetStatus> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (TimeSheetStatus status : TimeSheetStatus.values()) {
            lookup.put(status.getStatus(), status);
        }
    }

    private String status;


    //****** Reverse Lookup Implementation************//

    TimeSheetStatus(String status) {
        this.status = status;
    }

    //This method can be used for reverse lookup purpose
    public static TimeSheetStatus get(String status) {
        return lookup.get(status);
    }

    public static Map<String, TimeSheetStatus> getLookup() {
        return lookup;
    }

    public String getStatus() {
        return status;
    }
}
