package us.redshift.timesheet.dto.client;

import lombok.*;
import us.redshift.timesheet.domain.client.ClientStatus;
import us.redshift.timesheet.dto.common.AddressDto;
import us.redshift.timesheet.dto.common.BaseDto;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDto extends BaseDto {

    private Long id;
    @NonNull
    private String clientCode;
    @NonNull
    private String name;
    private Set<PocDto> pocs;
    private String url;
    private ClientStatus status;
    private IndustryDto industry;
    private Set<FocusAreaDto> focusAreas;
    private String futureFocus;
    private String about;
    private Boolean addressFlag;
    private AddressDto address;
    private AddressDto billingAddress;


}
