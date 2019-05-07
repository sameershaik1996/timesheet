package us.redshift.timesheet.dto;

import lombok.*;
import us.redshift.timesheet.domain.ProjectStatus;
import us.redshift.timesheet.domain.ProjectType;
import us.redshift.timesheet.domain.RateCard;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto extends BaseDto {


    private String description;
    private Double estimatedHour;
    private Double estimatedCost;
    private ProjectStatus status;
    private ProjectType type;
    private Date startDate;
    private Date endDate;
    private Date startedOn;
    private Date endedOn;
    private Set<BaseDto> employees;
    private BaseDto manager;
    @NonNull
    private ClientListDto client;
    private RateCard rateCard;
}