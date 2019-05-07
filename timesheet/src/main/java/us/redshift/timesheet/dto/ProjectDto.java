package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.ProjectStatus;
import us.redshift.timesheet.domain.ProjectType;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String name;
    private String description;
    private Double estimatedHour;
    private Double estimatedCost;
    private ProjectStatus status = ProjectStatus.PENDING;
    private ProjectType type = ProjectType.FIXED;
    private Date startDate;
    private Date endDate;
    private Date startedOn;
    private Date endedOn;
    private Set<Long> employeeId;
    private ClientDto clientId;
}