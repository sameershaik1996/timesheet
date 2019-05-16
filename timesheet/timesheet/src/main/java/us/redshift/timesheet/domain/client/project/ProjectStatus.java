package us.redshift.timesheet.domain.project;


import java.util.HashMap;
import java.util.Map;

public enum ProjectStatus {
    INACTIVE("INACTIVE"), ACTIVE("ACTIVE"), ON_HOLD("ON_HOLD"), COMPLETE("COMPLETE");

    //Lookup table
    private static final Map<String, ProjectStatus> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (ProjectStatus status : ProjectStatus.values()) {
            lookup.put(status.getStatus(), status);
        }
    }

    private String status;


    //****** Reverse Lookup Implementation************//

    ProjectStatus(String status) {
        this.status = status;
    }

    //This method can be used for reverse lookup purpose
    public static ProjectStatus get(String status) {
        return lookup.get(status);
    }

    public static Map<String, ProjectStatus> getLookup() {
        return lookup;
    }

    public String getStatus() {
        return status;
    }
}
