package us.redshift.timesheet.domain.task;


import java.util.HashMap;
import java.util.Map;


public enum TaskStatus {
    UNASSIGNED("UNASSIGNED"), ACTIVE("ACTIVE"), ON_HOLD("ON_HOLD"), COMPLETE("COMPLETE");

    //Lookup table
    private static final Map<String, TaskStatus> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (TaskStatus status : TaskStatus.values()) {
            lookup.put(status.getStatus(), status);
        }
    }

    private String status;


    //****** Reverse Lookup Implementation************//

    TaskStatus(String status) {
        this.status = status;
    }

    //This method can be used for reverse lookup purpose
    public static TaskStatus get(String status) {
        return lookup.get(status);
    }

    public static Map<String, TaskStatus> getLookup() {
        return lookup;
    }

    public String getStatus() {
        return status;
    }


}
