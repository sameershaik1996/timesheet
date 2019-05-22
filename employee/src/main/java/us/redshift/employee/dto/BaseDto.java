package us.redshift.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto implements Serializable {

    private Long id;

    private String createdTimeStamp;

    private String updatedTimeStamp;

    private Long createdBy;



    private Long updatedBy;

}
