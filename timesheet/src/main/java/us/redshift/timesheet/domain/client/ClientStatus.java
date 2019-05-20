package us.redshift.timesheet.domain.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum ClientStatus implements Serializable {
    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    //Lookup table
    private static final Map<String, ClientStatus> lookup = new HashMap<>();

    //Populate the lookup table on loading time
    static {
        for (ClientStatus status : ClientStatus.values()) {
            lookup.put(status.getStatus(), status);
        }
    }

    private String status;


    //****** Reverse Lookup Implementation************//

    ClientStatus(String status) {
        this.status = status;
    }

    //This method can be used for reverse lookup purpose
    public static ClientStatus get(String status) {
        return lookup.get(status);
    }

    public String getStatus() {
        return status;
    }

    public static Map<String, ClientStatus> getLookup() {
        return lookup;
    }
}
