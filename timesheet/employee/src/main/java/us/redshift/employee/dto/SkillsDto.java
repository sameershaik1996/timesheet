package us.redshift.employee.dto;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "employees")
@AllArgsConstructor
public class SkillsDto extends BaseDto implements Serializable {

    private String skill;

}
