package us.redshift.timesheet.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.project.ProjectStatus;
import us.redshift.timesheet.domain.project.ProjectType;
import us.redshift.timesheet.dto.common.BaseDto;
import us.redshift.timesheet.dto.common.CommonDto;
import us.redshift.timesheet.dto.common.EmployeeListDto;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectListDto extends BaseDto {

    private Long id;
    private String projectCode;
    private String name;
    private ProjectStatus status;
    private ProjectType type;
    private Date startDate;
    private Date endDate;
    private Set<EmployeeListDto> employees;
    private EmployeeListDto manager;
    private CommonDto client;

}