package us.redshift.timesheet.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import us.redshift.timesheet.domain.client.ClientStatus;
import us.redshift.timesheet.domain.client.Industry;
import us.redshift.timesheet.dto.common.BaseDto;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientListDto extends BaseDto {

    @NonNull
    private Long id;
    private String clientCode;
    private String name;
    private Set<PocDto> pocs;
    private ClientStatus status;
    private Industry industry;
    private Set<FocusAreaDto> focusAreas;
}
