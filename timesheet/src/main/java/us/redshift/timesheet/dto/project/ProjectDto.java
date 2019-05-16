package us.redshift.timesheet.dto.project;

import lombok.*;
import us.redshift.timesheet.domain.project.ProjectStatus;
import us.redshift.timesheet.domain.project.ProjectType;
import us.redshift.timesheet.domain.ratecard.RateCard;
import us.redshift.timesheet.dto.client.ClientListDto;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;

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