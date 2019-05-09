package us.redshift.timesheet.dto;

import lombok.*;
import us.redshift.timesheet.domain.ClientStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientListDto extends BaseDto {

    @NonNull
    private String clientCode;
    private String name;
    private List<PocDto> pocs;
    private ClientStatus status;
    private String domain;
    private String specialization;
}
