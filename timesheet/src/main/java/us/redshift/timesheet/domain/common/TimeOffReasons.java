package us.redshift.timesheet.domain.common;

import us.redshift.timesheet.domain.project.ProjectType;

import java.util.HashMap;
import java.util.Map;

public enum TimeOffReasons {
    LEAVE("LEAVE"), MEETING("MEETING"),TRAINING("TRAINING"),OFFSITE("OFFSITE"),OTHERS("OTHERS");


    //Lookup table
    private static final Map<String, ProjectType> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (ProjectType type : ProjectType.values()) {
            lookup.put(type.getStatus(), type);
        }
    }

    private String type;


    //****** Reverse Lookup Implementation************//

    TimeOffReasons(String type) {
        this.type = type;
    }

    //This method can be used for reverse lookup purpose
    public static ProjectType get(String type) {
        return lookup.get(type);
    }

    public static Map<String, ProjectType> getLookup() {
        return lookup;
    }

    public String getStatus() {
        return type;
    }

}
