package us.redshift.timesheet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDto implements Serializable {

    private Long id;

    @JsonIgnoreProperties(value = "createdTimeStamp", allowGetters = true)
    private Instant createdTimeStamp;
    @JsonIgnoreProperties(value = "updatedTimeStamp", allowGetters = true)
    private Instant updatedTimeStamp;
    @JsonIgnoreProperties(value = "createdBy", allowGetters = true)
    private Long createdBy;
    @JsonIgnoreProperties(value = "updatedBy", allowGetters = true)
    private Long updatedBy;


    public BaseDto(Long id) {
        this.id = id;
    }
}
