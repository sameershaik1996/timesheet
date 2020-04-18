package us.redshift.auth.domain;

import java.util.HashMap;
import java.util.Map;

public enum RoleName {
    ADMIN("ADMIN"), EMPLOYEE("EMPLOYEE"), MANAGER("MANAGER"), PM("PM");


    private static final Map<String, RoleName> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (RoleName status : RoleName.values()) {
            lookup.put(status.getStatus(), status);
        }
    }

    private String status;


    //****** Reverse Lookup Implementation************//

    RoleName(String status) {
        this.status = status;
    }

    //This method can be used for reverse lookup purpose
    public static RoleName get(String status) {
        return lookup.get(status);
    }

    public static Map<String, RoleName> getLookup() {
        return lookup;
    }

    public String getStatus() {
        return status;
    }


}
