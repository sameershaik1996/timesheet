package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillDto extends BaseDto {
    private String skill;

    public SkillDto(Long id, String skill) {
        super(id);
        this.skill = skill;
    }
}
