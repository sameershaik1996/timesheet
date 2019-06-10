package us.redshift.timesheet.dto.client;

import lombok.*;
import org.springframework.lang.NonNull;
import us.redshift.timesheet.domain.client.ClientStatus;
import us.redshift.timesheet.dto.common.AddressDto;
import us.redshift.timesheet.dto.common.BaseDto;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDto extends BaseDto {

    private Long id;
    @NotNull(message = "clientCode can't be NULL")
    private String clientCode;
    @NonNull
    private String name;
    private Set<PocDto> pocs = new HashSet<>();
    private String url;
    private ClientStatus status;
    private IndustryDto industry;
    private Set<FocusAreaDto> focusAreas = new HashSet<>();
    private String futureFocus;
    private String about;
    private AddressDto address;
    private AddressDto billingAddress;
    private String notes;


}
