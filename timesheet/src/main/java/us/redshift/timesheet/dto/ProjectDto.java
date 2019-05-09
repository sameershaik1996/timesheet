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

    private Long id;
    @NonNull
    private String projectCode;
    @NonNull
    private String name;
    private String description;
    @NonNull
    private Long estimatedDays;
    @NonNull
    private Double estimatedCost;
    @NonNull
    private ProjectStatus status;
    @NonNull
    private ProjectType type;
    private Date startDate;
    private Date endDate;
    private Date startedOn;
    private Date endedOn;
    @NonNull
    private Set<EmployeeListDto> employees;
    private EmployeeListDto manager;
    @NonNull
    private ClientListDto client;
    private RateCard rateCard;

}