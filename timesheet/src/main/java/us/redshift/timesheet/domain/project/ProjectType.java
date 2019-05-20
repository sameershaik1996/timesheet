package us.redshift.timesheet.domain.project;

import java.util.HashMap;
import java.util.Map;

public enum ProjectType {
    FIXED_BID("FIXED_BID"), TIME_AND_MONEY("TIME_AND_MONEY");


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

    ProjectType(String type) {
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
