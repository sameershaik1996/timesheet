package us.redshift.timesheet.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDto implements Serializable {


    @JsonIgnoreProperties(value = "createdTimeStamp", allowGetters = true)
    private Instant createdTimeStamp;
    @JsonIgnoreProperties(value = "updatedTimeStamp", allowGetters = true)
    private Instant updatedTimeStamp;
    @JsonIgnoreProperties(value = "createdBy", allowGetters = true)
    private String createdBy;
    @JsonIgnoreProperties(value = "updatedBy", allowGetters = true)
    private String updatedBy;

}
