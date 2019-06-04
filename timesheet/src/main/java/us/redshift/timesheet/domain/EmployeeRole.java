package us.redshift.timesheet.domain;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeRole {

    DEVELOPER("DEVELOPER"), TESTER("TESTER"), ARCHITECT("ARCHITECT"), PROJECT_MANAGEMENT("PROJECT_MANAGEMENT"), CONSULTANT("CONSULTANT");

    //Lookup table
    private static final Map<String, EmployeeRole> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (EmployeeRole role : EmployeeRole.values()) {
            lookup.put(role.getStatus(), role);
        }
    }

    private String role;


    //****** Reverse Lookup Implementation************//

    EmployeeRole(String role) {
        this.role = role;
    }

    //This method can be used for reverse lookup purpose
    public static EmployeeRole get(String role) {
        return lookup.get(role);
    }

    public static Map<String, EmployeeRole> getLookup() {
        return lookup;
    }

    public String getStatus() {
        return role;
    }


}
