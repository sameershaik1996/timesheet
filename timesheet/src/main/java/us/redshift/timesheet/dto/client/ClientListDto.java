package us.redshift.timesheet.dto.client;

import lombok.*;
import us.redshift.timesheet.domain.client.ClientStatus;
import us.redshift.timesheet.domain.client.Industry;
import us.redshift.timesheet.dto.common.BaseDto;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientListDto extends BaseDto {

    private Long id;
    @NonNull
    private String clientCode;
    private String name;
    private Set<PocDto> pocs;
    private ClientStatus status;
    private Industry industry;
    private Set<FocusAreaDto> focusAreas;
}
