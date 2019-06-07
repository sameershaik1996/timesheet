package us.redshift.timesheet.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDto implements Serializable {


    @JsonIgnoreProperties(value = "createdTimeStamp", allowGetters = true)
    private String createdTimeStamp;
    @JsonIgnoreProperties(value = "updatedTimeStamp", allowGetters = true)
    private String updatedTimeStamp;
    @JsonIgnoreProperties(value = "createdBy", allowGetters = true)
    private String createdBy;
    @JsonIgnoreProperties(value = "updatedBy", allowGetters = true)
    private String updatedBy;

}
