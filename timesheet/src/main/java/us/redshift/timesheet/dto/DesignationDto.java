package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesignationDto extends BaseDto {

    private String designation;

    public DesignationDto(Long id, String designation) {
        super(id);
        this.designation = designation;
    }
}
