package us.redshift.timesheet.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.project.ProjectStatus;
import us.redshift.timesheet.domain.project.ProjectType;
import us.redshift.timesheet.dto.client.ClientListDto;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;
import us.redshift.timesheet.dto.ratecard.RateCardDto;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto extends BaseDto {

    private Long id;
    @NotNull(message = "projectCode cannot be empty")
    private String projectCode;
    @NotNull(message = "Name cannot be empty")
    private String name;
    private String description;
    @NotNull(message = "estimatedDays cannot be empty")
    private Long estimatedDays;
    private Double estimatedCost;
    private ProjectStatus status = ProjectStatus.ACTIVE;
    @NotNull(message = "projectType cannot be empty")
    private ProjectType type;
    private Date startDate;
    private Date endDate;
    private Date startedOn;
    private Date endedOn;
    @NotNull(message = " employees cannot be empty")
    private List<EmployeeListDto> employees;
    private EmployeeListDto manager;
    @NotNull(message = "client cannot be empty")
    private ClientListDto client;
    private RateCardDto rateCard;

}