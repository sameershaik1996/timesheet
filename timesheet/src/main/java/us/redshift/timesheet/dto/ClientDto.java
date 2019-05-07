package us.redshift.timesheet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import us.redshift.timesheet.domain.ClientStatus;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String name;
    private String email;
    private ClientStatus status = ClientStatus.ACTIVE;
    private Integer phoneNumber;
    private String domain;
    private String specialization;
    private String offering;
    private String about;
    private String address;
    private Long countryCode;
    private Long stateCode;
    private Set<ProjectDto> projects;


}
