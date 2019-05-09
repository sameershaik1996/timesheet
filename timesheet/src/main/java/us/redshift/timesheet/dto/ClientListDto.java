package us.redshift.timesheet.dto;

import lombok.*;
import us.redshift.timesheet.domain.ClientStatus;
import us.redshift.timesheet.domain.Industry;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientListDto extends BaseDto {

    private Long id;
    @NonNull
    private String clientCode;
    private String name;
    private List<PocDto> pocs;
    private ClientStatus status;
    private Industry industry;
    private List<FocusAreaDto> focusAreas;
}
