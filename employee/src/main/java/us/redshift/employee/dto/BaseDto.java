package us.redshift.employee.dto;

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
public class BaseDto implements Serializable {

    private Long id;

    private Instant createdTimeStamp;

    private Instant updatedTimeStamp;

    private String createdBy;


    private String updatedBy;

}
